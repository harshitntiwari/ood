package TicTacToe.strategies;

import TicTacToe.model.Board;
import TicTacToe.model.Player;

public interface WinningStrategy {
    public boolean checkWinner(Board board, Player player);
}
