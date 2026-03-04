Core requirements:
1. The game will be played on a 3 * 3 board.
2. Two players will take turns to put in a symbol (X or O) in a cell on the board.
3. The game can be in one of the following three states at any point in time:
    - Win: The first of the two players to have 3 of their symbol in a row (horizontally, vertically, diagonally) wins.
    - Draw: If the board is entirely filled with no player as a winner, then the game is a draw.
    - In progress: Neither a player has won, nor the game has been drawn.
4. The game can end in one of the following two states: Win or Draw.
5. The game should handle player turns. It should also validate if the moves are legal.

Core entities:
- Game
- Board
- Cell
- Player

Enums:
- Symbol

Strategies:
- WinningStrategy
  - RowWinningStrategy
  - ColumnWinningStrategy
  - DiagonalWinningStrategy

Exceptions:
- InvalidMoveException
