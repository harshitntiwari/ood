## Core requirements

- The system should allow users to create their accounts
- Users should be able to create groups and add other users to the groups
- Users should be able to create expenses in a group as well as add 1-on-1 expenses
- The system should automatically split the expenses among the participants
- Different methods for splitting should be available like: equal, percentage, exact amounts
- Users can simplify the group debts
- Users should be able to see their balance sheet - total amount paid, and individual balances with other users
- Users should be able to settle the balances

## Core entities

- User
- Group
- Expense
- Split
- BalanceSheet

## Orchestrator

There will be one orchestrator class `SplitwiseService`.

## Class design

The users will interact with the top-level orchestrator class `SplitwiseService`.

```
class SplitwiseService:
    - users: Map<String, User>
    - groups: Map<String, Group>

    - addUsers(List<User> users): void
    - addGroups(List<Group> groups): void

```

```
class User:
    - id: String
    - name: String

    + User(String id, String name)
    + getId(): String
    + getName: String
```

```
class Group:
    - id: String
    - name: String
    - members: List<Users>

    + Group(String id, String name)
    + addMember(User user): void
```
