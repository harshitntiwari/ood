package tictactoe.strategies;

import tictactoe.model.Board;
import tictactoe.model.Player;

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
        for (int idx = 0; idx < board.getSize(); idx++) {
            if (board.getCell(idx, board.getSize() - 1 - idx).getSymbol() != player.getSymbol()) {
                diagWin = false;
                break;
            }
        }
        return diagWin;
    }
}
