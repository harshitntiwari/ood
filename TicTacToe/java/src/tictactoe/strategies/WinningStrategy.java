package tictactoe.strategies;

import tictactoe.model.Board;
import tictactoe.model.Player;

public interface WinningStrategy {
    public boolean checkWinner(Board board, Player player);
}
