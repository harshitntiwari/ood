import { Board } from "../model/Board.js";
import { Player } from "../model/Player.js";

export interface WinningStrategy {
    checkWinner(board: Board, player: Player): boolean;
}
