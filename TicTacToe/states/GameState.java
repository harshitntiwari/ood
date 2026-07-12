package TicTacToe.state;

import TicTacToe.Game;
import TicTacToe.model.Player;

public interface GameState {
    public void makeMove(Game game, Player player, int row, int col);
}
