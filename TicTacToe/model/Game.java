package TicTacToe.model;

import TicTacToe.state.GameState;
import TicTacToe.state.InProgressState;
import TicTacToe.strategies.ColumnWinningStrategy;
import TicTacToe.strategies.DiagonalWinningStrategy;
import TicTacToe.strategies.RowWinningStrategy;
import TicTacToe.strategies.WinningStrategy;
import java.util.List;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player winner;
    private GameState state;
    private final List<WinningStrategy> winningStrategies;

    public Game(int boardSize, Player player1, Player player2) {
        this.board = new Board(boardSize);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        // starting state of the game will be in progress:
        this.state = new InProgressState();
        // create an immutable list of strategies
        this.winningStrategies = List.of(
            new RowWinningStrategy(),
            new ColumnWinningStrategy(),
            new DiagonalWinningStrategy()
        );
    }

    // business logic:

    public void makeMove(Player player, int row, int col) {
        // the logic to make a move will depend on the current state of the game, i.e.
        // whether the game is in progress, draw or win state.
        // we can write all that logic here itself, but a cleaner way is to split that logic
        // as written inside the state package
        state.makeMove(this, player, row, col);
    }

    public boolean checkWinner(Player player) {
        // we could've written all the logic here itself to check
        // if there's a combo of 3 of this player's symbol occuring in a row, col or diagonally.
        // but a cleaner approach is to do this checking in 3 different strategies:
        // RowWinningStrategy, ColWinningStrategy, DiagonalWinningStrategy - written in the strategies package
        for (WinningStrategy winningStrategy : this.winningStrategies) {
            if (winningStrategy.checkWinner(board, player)) return true;
        }
        return false;
    }

    public void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == player1)
            ? player2
            : player1;
    }

    // getters and setters for the private attributes:

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player player) {
        this.winner = player;
    }
}
