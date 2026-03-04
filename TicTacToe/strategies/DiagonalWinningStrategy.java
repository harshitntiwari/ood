package TicTacToe.strategies;

import TicTacToe.model.Board;
import TicTacToe.model.Player;

public class DiagonalWinningStrategy implements WinningStrategy {

    @Override
    public boolean checkWinner(Board board, Player player) {
        boolean diagWin = true;
        for (int idx = 0; idx < board.getSize(); idx++) {
            if (board.getCell(idx, idx).getSymbol() != player.getSymbol()) {
                diagWin = false;
                break;
            }
        }
        if (diagWin) return true;

        diagWin = true;
        for (int idx = board.getSize() - 1; idx >= 0; idx--) {
            if (board.getCell(idx, idx).getSymbol() != player.getSymbol()) {
                diagWin = false;
                break;
            }
        }
        if (diagWin) return true;
        return false;
    }
}
