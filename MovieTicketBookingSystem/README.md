## Core Requirements

- The user should be able to see the list of different movies screening in different theaters
- For each movie, there will be a list of shows running in different screens at different theaters
- In each screen there will be a seating arrangement, with seats of different types like regular, premium etc.
- The user can select a movie, theater, show and seats to book a ticket
- The user should be able to make payments and confirm their booking
- The system should support different payment methods like UPI, Card etc.
- The system should handle concurrent bookings and ensure that seat availablity is updated in real-time:
  - If two users try to book the same seat at the exact same time, only one of them should be able to succeed. In other words the system should be thread safe
  - If a user selects a seat and is yet to make the payment, the seat should be blocked for a fixed period of time (like 10 mins). After that time period is elapsed:
    - If the booking is still incomplete, the seat should be released for other users to book.
    - If the user tries to make a payment, we should check if the seat is still booked by this user or someone else has booked it.

## Core entities

These are the core entities that we can identify by looking at the nouns in the core requirements above:

- Movie
- Theater
- Show
- Screen
- Seat
- Booking
- Payment

## Orchestrators

We can create one orchestrator for each of the core entities above to handle the operations on that class. However, not each of these services will have some meaningful business logic apart from doing basic CRUD-like operations.

The main entity which requires some business logic is the `Booking` class. For each booking to be created and confirmed you'll have to check the availability of the selected seats, have the user make payment, and then create/confirm the booking. We prefer to make an orchestrator class for such scenarios where coordination between multiple entities is required. So we'll create a `BookingManager` for the same.

For the remaining classes we'll simply create a top-level orchestrator, `MovieBookingService` which will have methods to do operations on all of these classes.

We'll expand on `BookingManager` and `MovieBookingService` more in class design.

## Class design

All the users will be interacting with the top-level orchestrator `MovieBookingService`. So we'll start class design from that.

There will be two types of users:

- Administrators who'll add movies, screens, seats, theaters and shows to the platform.
- Customers who'll book tickets on the platform. Booking tickets has two steps:
  - creating a booking by selecting show and seats
  - confirming the booking by making the payment

```
class MovieBookingService:
    - shows: Map<string, Show>
    - bookingManager: BookingManager

    + MovieBookingService()
    // ---- methods for admins ----
    + addShow(Show show): Show
    // ---- methods for customers ----
    + findShows(String movieName): List<Show>
    + findAvaliableSeats(Show show): List<Seats>
    + createBooking(String showID, List<String> seatIDs, String userId): Optional<Booking>
    + confirmBooking(String bookingID, PaymentType paymentType): void
```

```
class BookingManager:
    - bookings: Map<string, Booking>

    + BookingManager()
    + createBooking(Show show, List<Seat> seats, String userId): Optional<Booking>
    + confirmBooking(String bookingID, PaymentType paymentType): void
```

```
class Movie:
    - id: string
    - name: string
    - durationInMinutes: int

    + Movie(String id, String name, int duration)
```

```
class Theater:
    - id: string
    - name: string
    - screens: List<Screen>

    + Theater(String id, String name)
    + addScreen(Screen screen): void
```

```
class Screen:
    - id: string
    - seats: List<Seat>

    + Screen(String id)
    + addSeat(Seat seat): void
    + getSeats(): List<Seat>
```

```
class Seat:
    - id: String
    - type: SeatType

    + Seat(String id)
    + getId(): String
    + getType(): SeatType
```

```
enum SeatType:
    REGULAR,
    PREMIUM
```

We'll also have an enum to represent a seat's status at any given point of time:

```
enum SeatStatus:
    BOOKED,
    LOCKED,
    AVAILABLE
```

But the important question is: where do we store a seat's status? At first glance, we could be tempted to add a `status` attribute in the `Seat` entity itself to track a seat's status. But that would be wrong.

The reason is: The status of a seat always changes on a show basis - a seat could be booked in one show and available in another.
Therefore it would be wrong to track a seat's status globally in the `Seat` entity itself. Instead we need to maintain a per show Map of (seatId, SeatStatus) in the `Show` entity as we'll do below.

Another question: where do we store info related to Seat pricing? Again we might think of adding it to the `Seat` entity. But the price of a seat will vary depending on the show. So we'll maintain a map of (SeatType, price) in the `Show` entity.

Both seat status and seat pricing are dependent on the show and not the seat.

```
class Show:
    - id: String
    - movie: Movie
    - theater: Theater
    - screen: Screen
    - startTime: DateTime
    - seatStatusMap: Map<String, SeatStatus>
    - seatPricingMap: Map<SeatType, Double>

    + Show(String id, Movie movie, Theater theater, Screen screen)
    + getMovie(): Movie
    + getScreen(): Screen
    + getSeatStatus(Seat seat): SeatStatus
    + setSeatStatus(Seat seat, SeatStatus status): void
    + getSeatPriceByType(SeatType type): double
    + setSeatPrice(SeatType type, double price): void
```

```
class Booking:
    - id: String
    - show: Show
    - seats: List<Seat>
    - payment: Payment

    + Booking(String id, Show show, List<Seat> seats)
```

```
class Payment:
    - amount: Double
    - status: PaymentStatus
    - type: PaymentType

    + setStatus(PaymentStatus status): void
```

```
enum PaymentStatus:
    SUCCESS,
    PENDING,
    FAILURE
```

```
enum PaymentType:
    UPI,
    CARD
```

## Implementation

The two main methods to implement are: `BookingManager.createBooking()` and `BookingManager.confirmBooking()`.

### `BookingManager.createBooking()`:

What are the steps involved in creating a booking?
First we need to check if the seats that the user is trying to book are available or not. If yes, then we'll create the booking.
But there's an edge case: what if 2 users try to book the same seats simultaneously? This is, in fact, the single most important point to consider while designing the solution to this problem.

If 2 users try to book the same seats at the same time, both of them find that the seats are available and both of them are able to create the booking. Which is wrong. There has to be one extra step between checking the availability of seats and creating the actual booking, which is to acquire lock over the seats.
Only one of the user will be able to acquire the lock (we don't care which one) and the other will receive an error.
For this we'll create another service `SeatLockMangaer` which will hold methods to lock and unlock seats:

```
class SeatLockManager:
    - seatLock: Map<Show, Map<Seat, String>>

    + lockSeats(Show show, List<Seat> seats, String userId)
    + unlockSeats(Show show, List<Seat> seats, String userId)
```

The `BookingManager` class can then use this `SeatLockManager`:

```
class BookingManager:
    - seatLockManager: SeatLockManager
```

## `BookingManager.confirmBooking()`:

These will be the steps involved in `confirmBooking()`:

- check if the seat is still acquired by the same user who created the booking
- calculate the total price of all the seats
- make payment
- mark the seats as booked
- release the lock on the seats

The first very obvious place to use a design pattern is while "making payments". Our system should support several payment methods like UPI, Card etc, which is the classic use case of _*Strategy Design Pattern*_.
We'll create an interface `PaymentStrategy` which will have a `pay()` method and then we could create several concrete strategy classes like `UPIStrategy` and `CreditCardStrategy` which will all implement this interface.

```
interface PaymentStrategy:
    + pay(Double amount): Payment
```

Inside the `BookingManager.confirmBooking()` method, we can simply call `paymentStrategy.pay(amount)`.

Now another thing to note here is that `BookingManager.confirmBooking()` takes in the `PaymentType` as an input argument as that's what the user will send. But we've to call `paymentStrategy.pay(amount)`. Which means we have to convert the `PaymentType` to a concrete strategy. We can do this by simply writing an if-else statement like:

```
if paymentType == UPI:
    paymentStrategy = new UPIStrategy()
else if paymentType == CARD:
    paymentStrategy = new CreditCardStrategy()
```

The important question is, where do we keep this if-else logic in our code? Ideally `BookingManager.confirmBooking()` is the place which is handling the payment, so we could put this there.
But there are quite a few reasons to not put it there:

- It will unnecessarily bloat the `confirmBooking()` method as it will already have a bunch of logic related to seat locking and payment.
- Every time we've to add a new payment method, we'll have to modify the code in `confirmBooking()` which violates open-closed principle.
- The main responsibility of `confirmBooking()` is to orchestrate the confirmation of a booking. It has no business creating objects of other classes. If it does that it will violate single responsibility principle.

This is the classic use case of _*Factory design pattern*_. We'll create a `PaymentStrategyFactory` class which will hold this conditional logic:

```
class PaymentStrategyFactory:
    + getPaymentStrategy(PaymentType paymentType): PaymentStrategy
```

Every time a new payment method is to be added, we just need to modify this factory to handle it. Then in our `BookingManager.confirmBooking()`, we can simply use this factory:

```
BookingManager.confirmBooking():
    PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentType)
    paymentStrategy.pay(amount)
```
