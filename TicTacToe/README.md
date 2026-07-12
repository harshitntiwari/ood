Core requirements:

1. The game will be played on a 3 \* 3 board.
2. Two players will take turns to put in a symbol (X or O) in a cell on the board.
3. The game can be in one of the following three states at any point in time:
   - Win: The first of the two players to have 3 of their symbol in a row (horizontally, vertically, diagonally) wins.
   - Draw: If the board is entirely filled with no player as a winner, then the game is a draw.
   - In progress: Neither a player has won, nor the game has been drawn.
4. The game can end in one of the following two states: Win or Draw.
5. The game should handle player turns. It should also validate if the moves are legal.

Core entities:

- Board
- Cell
- Player

Enums:

- Symbol

Orchestrator:

- Game - will coordinate players and the board

Key design patterns used:

- We can easily figure that like any game, this game will be in different states while being played: win, draw or in progress. Whenever a player will make a move, what happens next depends on which state the game is currently in. If the game is in win or draw state, any move will be an invalid move. If the game is in progress, the move is valid and we'll mark the position with that player's symbol and then check if the game has been won or drawn (handling state transition). This is a clear use case of _state design pattern_. We'll have three states: DrawState, WinState and InProgressState and the behaviour of the game when a move is made will be dependent on these states.

- There can be 3 different ways a player can win, depending on whether they have 3 of their symbol in a row horizontally, vertically or diagonally. Whenever a move is made, we need to check if the game has been won and the logic to check that will vary based on whether we're checking for horizontal win, vertical win or a diagonal win. These will act as three different winning _strategies_: RowWinningStrategy, ColumnWinningStrategy, DiagonalWinningStrategy.

Exceptions:

- InvalidMoveException
