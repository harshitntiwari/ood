import { Board } from "../model/Board.js";
import { Player } from "../model/Player.js";
import { WinningStrategy } from "./WinningStrategy.js";

export class ColumnWinningStrategy implements WinningStrategy {
    checkWinner(board: Board, player: Player): boolean {
        for (let col = 0; col < board.size; col++) {
            let colWin = true;
            for (let row = 0; row < board.size; row++) {
                if (board.getCell(row, col).symbol !== player.symbol) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) return true;
        }
        return false;
    }
}
