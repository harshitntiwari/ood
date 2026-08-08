package MovieTicketBookingSystem;

import MovieTicketBookingSystem.enums.PaymentStatus;
import MovieTicketBookingSystem.enums.PaymentType;
import MovieTicketBookingSystem.enums.SeatStatus;
import MovieTicketBookingSystem.factory.PaymentFactory;
import MovieTicketBookingSystem.model.Booking;
import MovieTicketBookingSystem.model.Payment;
import MovieTicketBookingSystem.model.Seat;
import MovieTicketBookingSystem.model.Show;
import MovieTicketBookingSystem.strategies.PaymentStrategy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BookingManager {
    private final ConcurrentHashMap<UUID, Booking> bookings;
    private final SeatLockManager seatLockManager;

    public BookingManager() {
        bookings = new ConcurrentHashMap<>();
        seatLockManager = new SeatLockManager();
    }
    
    public Optional<Booking> createBooking(Show show, List<Seat> seats, String userId) {

        // try to acquire lock over the seats
        if(!seatLockManager.lockSeats(show, seats, userId)) return Optional.empty();

        // create and return booking
        UUID bookingID = UUID.randomUUID();
        Booking booking = new Booking(bookingID, show, seats, userId);
        bookings.put(bookingID, booking);
        return Optional.of(booking);
    }

    public void confirmBooking(UUID bookingId, PaymentType paymentType) {
        Booking booking = bookings.get(bookingId);
        Show show = booking.getShow();

        // all the steps below should be atomic per show, so we wrap them inside a synchronized block
        synchronized (show) {
            // check if the seat is still acquired by the same user
            for(Seat seat: booking.getSeats()) {
                if(!seatLockManager.isLockedBy(show, seat, booking.getUserId())) {
                    System.out.println("Seat " + seat.getId() + " is not available to book now.");
                    return;
                }
            }

            // calculate the total price of all the seats
            double totalAmount = 0;
            for(Seat seat: booking.getSeats()) {
                totalAmount += show.getPriceForSeatType(seat.getType());
            }

            // make payment
            PaymentStrategy paymentStrategy = PaymentFactory.getPaymentStrategy(paymentType);
            Payment payment = paymentStrategy.pay(totalAmount);
            booking.setPayment(payment);

            if(payment.getStatus() != PaymentStatus.SUCCESS) {
                System.out.println("Failed to confirm the booking.");
                return; // seats remain locked; user can retry within the lock window
            }

            // mark the seats as booked
            for(Seat seat: booking.getSeats()) {
                show.setSeatStatus(seat, SeatStatus.BOOKED);
            }

            // release the lock on the seats
            seatLockManager.unlockSeats(show, booking.getSeats(), booking.getUserId());
        }
    }
}