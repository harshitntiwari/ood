## Core Requirements:

- There are $n$ number of elevators serving $m$ number of floors in a building
- There are 2 types of request a user can make:
  - Hall call: User can call an elevator from any floor. Hall call will also be of 2 types: UP and DOWN. System decides which elevator to dispatch
  - Destination call: Once a user boards an elevator, they can select one or more destination floors.
- System should handle requests efficiently:
  - Optimize the movement of elevators to minimize wait time
  - Prioritize requests based on the direction of requests and proximity of elevator to the requested floor
- System should handle multiple concurrent user requests
- Should handle errors/edge cases:
  - Making a destination call for an invalid (out-of-bounds) floor should throw proper error
  - Destinaton call for the current floor is treated as no-op

## Core Entities:

- Elevator
- Request

## Orchestrator:

- ElevatorController - this acts as the orchestrator - receives hall call from people and dispatches the elevators

## Class Design:

We'll start class design in a top down manner. The user will interact, not with individual elevators, but with the system, i.e. the ElevatorController.

So we'll begin by designing the `ElevatorController` first.

### `ElevatorController`

The ElevatorController is the orchestrator. It is the one responsible for dispatching the elevators based on user requests and controlling the movement of the elevators. Therefore, it needs to know about all the `elevators` available in the building.

It will provide user-facing methods that the user can call to make requests:

- `requestElevator()` for hall calls - the controller will decide which elevator to assign for a particular request and then hand off that request to that elevator. The implementation of how it will do this exactly, we'll look into it in the implementation section.
- `requestDestination` for destination calls - in case of destination call, the elevator is already decided, we just have to hand off the request to that elevator so that it can service it.

The ElevatorController will also make the elevators move through a method `move`.

```
class ElevatorController:
    - elevators: List<Elevator>

    + ElevatorController(int numElevators, int maxFloors)
    + requestElevator(int floor, RequestType type): void
    + requestDestination(int elevatorId, int floor): void
    + move(): void
```

### `Elevator`

Next we design `Elevator`. An elevator will maintain all the state associated to itself, like the `floor` it is currently on, the `direction` it is currently moving in and the list of `requests` it has to service.

Whenever the ElevatorController decides which elevator to dispatch for a particular request, that request will get added to that particular elevator's `requests` set through `addRequest()`. Once that request is serviced, it can be removed through `removeRequest`.

The next question is how will the elevator service these requests? By moving along the building obviously. For that there is `move()` method which will simulate the movement of an elevator and its servicing of the requests.

The remaining methods mentioned below are just normal getters and setters.

```
class Elevator:
    - floor: int
    - direction: Direction
    - requests: Set<Request>

    + Elevator()
    + addRequest(Request request): void
    + removeRequest(Request request): void
    + move(): void
    + getFloor(): int
    + getDirection(): Direction
    + setFloor(): int
```

```
enum Direction:
    UP,
    DOWN,
    IDLE // an elevator is not always going up or down, it can be idle too
```

So the user will make the request to the `ElevatorController` and it will pass down the request to the relevant elevator.

Now let's design this `Request` class.

### `Request`

We know that a request can be of two types: Hall call or Destination call. In case of a hall call, the two variables to consider are: the floor at which the elevator is requested and the direction in which the user wants to go. In case of a destination call, the only variable to consider is: the floor to which the user wants to go.

So, we can have these two attributes in our `Request` class: `floor` and `direction` - and this will be perfectly fine.

There's one thing to note here: `direction` will be null for destination calls. And the meaning of `floor` changes based on whether `direction` is null or not. If `direction` is not null (i.e. hall call), then `floor` means the floor from which the user has requested the elevator. And if `direction` is null (i.e. destination call), then `floor` means the floor to which the user wants to go.

Since we're designing this now, we know what a null `destination` in a Request mean. But for someone working on this code in future, this might create an ambiguity as to what does a null destination mean.

So a better way is to keep a request type instead of the direction. The `RequestType` will be of 3 types: Hall call for UP, Hall call for DOWN and Destination call. This clearly defines what this request exactly is.

```
class Request:
    - floor: int
    - type: RequestType

    + Request(floor, type)
    + getFloor(): int
    + getRequestType(): RequestType

```

```
enum RequestType:
    CALL_UP, // Hall call to go UP
    CALL_DOWN, // Hall call to go down
    DESTINATION // destination call to go to a particular floor
```

## Implementation

For implementation we'll go bottom up, where we'll first implement `RequestType` and `Direction` enums and the `Request` class which are trivial.

The main two functionalities to put actual thought into are: `Elevator.move` and `ElevatorController.requestElevator`. For each of these we'll first see what design patterns we can use and then go into the actual implementation.

### `Elevator.move`

We know that an elevator can be in any of these 3 states at any given time: Moving up, Moving down or Idle. The implementation of the `move()` method in the `Elevator` class will be dependent on which state the elevator currently is. This is the classic use case of _**State design pattern**_, where the behaviour of an object varies depending on which state it currently is.

Note that the `direction` field (which is an enum) in the `Elevator` class sort of tracks this state only. But we want to describe and associate some behaviour (i.e. the `move()` method) using the state and we can't do that with an enum. Therefore, we'll create another field `state` of type `ElevatorState` and we'll create three state classes that will implement this `ElevatorState` interface: `MovingUpState`, `MovingDownState` and `IdleState`.

Now, there is no need of `direction` field in the `Elevator` class, so we'll move that info inside the State classes.

```
interface ElevatorState:
    + move(Elevator elevator): void
    + getDirection(): Direction
```

```
class Elevator:
    // ...remaining fields
    - state: ElevatorState

    // ...remaning methods
    + setState(ElevatorState state): void
```

And the `Elevator.move` logic will now be simply:

```
Elevator.move():
    state.move(this)
```

All the actual logic to move the elevator will be inside the respective states.

Now, for that actual `move` implementation, we'll be using the popular [SCAN algorithm](https://en.wikipedia.org/wiki/Elevator_algorithm).

### `ElevatorController.requestElevator`

There can be different ways an Elevator system decides which elevator to pick for which request. You can simply pick the nearest elevator, or you can pick a more optimal one by doing a more direction-aware decision making.
This clearly indicates: _**Strategy design pattern**_. We'll add a new attribute `strategy` of type `SelectionStrategy` in the `ElevatorController`. Each elevator selection strategy will implement this common interface:

```
interface SelectionStrategy:
    + selectBestElevator(List<Elevator> elevators, Request request): Elevator
```

And `ElevatorController.requestElevator` simply becomes:

```
ElevatorController.requestElevator(floor, request):
    bestElevator = strategy.selectBestElevator(request)
    bestElevator.addRequest(request)
```

The actual implementation to select the best elevator now lies inside the respective strategy's `selectBestElevator` method.
