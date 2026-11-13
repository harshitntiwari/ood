import { Board } from "../model/Board.js";
import { Player } from "../model/Player.js";
import { WinningStrategy } from "./WinningStrategy.js";

export class DiagonalWinningStrategy implements WinningStrategy {
    checkWinner(board: Board, player: Player): boolean {
        let diagWin = true;
        for (let idx = 0; idx < board.size; idx++) {
            if (board.getCell(idx, idx).symbol !== player.symbol) {
                diagWin = false;
                break;
            }
        }
        if (diagWin) return true;

        diagWin = true;
        for (let idx = 0; idx < board.size; idx++) {
            if (board.getCell(idx, board.size - 1 - idx).symbol !== player.symbol) {
                diagWin = false;
                break;
            }
        }
        return diagWin;
    }
}
