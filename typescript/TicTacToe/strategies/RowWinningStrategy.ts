import { Board } from "../model/Board.js";
import { Player } from "../model/Player.js";
import { WinningStrategy } from "./WinningStrategy.js";

export class RowWinningStrategy implements WinningStrategy {
    checkWinner(board: Board, player: Player): boolean {
        for (let row = 0; row < board.size; row++) {
            let rowWin = true;
            for (let col = 0; col < board.size; col++) {
                if (board.getCell(row, col).symbol !== player.symbol) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) return true;
        }
        return false;
    }
}
