> NOTE: This is meant as a practical guide to approaching object orient design from an interview perspective and not an exhaustive material for study of this matter. For more comprehensive resources, you can follow something like [Awesome Low Level Design](https://github.com/ashishps1/awesome-low-level-design/).

## Delivery framework

Steps for object-oriented design problem solving:

- Requirements: Think through and fix the core requirements.
- Entities: What will be the core entities in your design? You can generally figure these out by looking at the nouns in your requirements.
- Class design: Start designing the classes based on the entities. Write down the state (attributes) and behaviour (methods) for these classes (don't implement them yet). Figure out the enums required.
- Implementation: Objectives of the implementation should be to code: primary capabilities + error handling.
  - Implement helper functionalities (whichever applicable) like: strategies, states, factories, observers etc.
  - Implement core logic of the core entities.
  - Finally handle edge cases (error handling).

## Important signals

Most LLD resources online feel exhausting, primarily because they try to teach you everything there is to teach about LLD, every design pattern and the most exhaustive way to write a design for a problem by defining tons of classes.

There are in fact just a few primary goals that act as the sufficient objectives to achieve while designing a solution to any problem. Anything that does more than satisfying these requirements is an overengineered bs from an interview perspectve in my opinion.

- Single responsibility - Each core entity should have a single reason to change i.e. it should have a single responsibility. Neither too few classes nor too many classes, just enough that no single class is doing two unrelated things.

- Extensibility (Open/close principle) - You should be able to add new features to your design without changing too much of the existing code. Can you add a new payment method in your payment system without touching the existing classes? Can you add support for a new vehicle type in your parking lot system without modifying other parts of the code?

- State modeling - Can you identify what has a state, what states those will be, what tranisitions between the states are legal and what part of the code owns that tranisition logic ?

- Concurrency awareness - What happens (or breaks) if two users hit the system simultaneously. Identifying race conditions and proposing general approaches (locks, atomic ops etc).

## Code organisation

Most resources have different ways to organise their code and name things. Here is a generic mental model you can use consistently in all the problems to structure your solution, so that you're not stuck wondering during an interview what you should name a directory or a file. Remember these are not rules and more of a mental model.

There will be some common directories that you can use in any problem. And then there will be some based on which desgin pattern you're using.

General directories that are used in almost all problems:

- models - core entities
- enums - types and statuses constants
- exceptions - custom exception classes

Directories based on what design patterns are used in the solution:

- strategies
- states
- observers
- factories

An entity (the ones you write inside model) generally is supposed to do a single thing. But business operations usually require coordination between multiple entities. You cannot write that logic inside the entities. Orchestration layer exists for that purpose. Most resources name these orchestrator classes as a "Service", "Manager" or "Controller".

Simple problems where there is one core operation and the entities are all in service of that one thing, would require just one orchestrator file. Like a ParkingLotManager, VendingMachine, TicTacToeGame etc.

Complex problems where there are clearly distinct domains that don't need to know about each other, we create multiple orchestrators for each domain and a top-level orchestrator that calls them.
For example, in a movie booking app, "Payment processing" has nothing to do with "seat inventory management" or "notifying the users". So we'll have different services like PaymentService, NotificationService etc.

For simple problems (parking lot, elevator, vending machine, games):

```
src/
├── models/
│ ├── Entity1.java
│ └── Entity2.java
├── enums/
│ ├── EntityStatus.java
│ └── EntityType.java
├── [conditional pattern folders only if used]
└── ProblemNameManager.java ← one orchestrator, main() calls this
```

For complex problems (movie booking, ride sharing, food delivery):

```
src/
├── models/
│ ├── Entity1.java
│ └── Entity2.java
├── enums/
│ ├── EntityStatus.java
│ └── EntityType.java
├── [conditional pattern folders only if used]
├── [DomainSpecific]Service.java
└── ProblemNameService.java ← top-level orchestrator that coordinates the rest, main() calls this
```

Note, the term "simple" and "complex" used above to refer to the problems is by the virtue of the number of distinct domains in the problem and not by how difficult the problem in itself is.
