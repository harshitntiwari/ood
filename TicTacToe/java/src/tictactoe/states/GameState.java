package tictactoe.states;

import tictactoe.Game;
import tictactoe.model.Player;

public interface GameState {
    public void makeMove(Game game, Player player, int row, int col);
}
