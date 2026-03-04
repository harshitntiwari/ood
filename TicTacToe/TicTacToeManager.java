package TicTacToe;

import TicTacToe.exceptions.InvalidMoveException;
import TicTacToe.model.Game;
import TicTacToe.model.Player;

public class TicTacToeManager {

    private Game game;
    private static TicTacToeManager instance;

    private TicTacToeManager() {}

    public static TicTacToeManager getInstance() {
        if (instance == null) {
            instance = new TicTacToeManager();
        }
        return instance;
    }

    public void createGame(int size, Player player1, Player player2) {
        this.game = new Game(size, player1, player2);
        System.out.printf(
            "A new game has started: %s Vs. %s.%n",
            player1.getName(),
            player2.getName()
        );
    }

    public void makeMove(Player player, int row, int col) {
        if (game == null) {
            // throw an error because no game is in progress yet.
            throw new InvalidMoveException("No game in progress!");
        }
        System.out.printf(
            "Player %s makes move at (%d, %d).%n",
            player.getName(),
            row,
            col
        );
        game.makeMove(player, row, col);
        this.printBoard();
    }

    public void printBoard() {
        game.getBoard().printBoard();
    }
}
