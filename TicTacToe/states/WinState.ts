import { Game } from "../Game.js";
import { InvalidMoveException } from "../exceptions/InvalidMoveException.js";
import { Player } from "../model/Player.js";
import { GameState } from "./GameState.js";

export class WinState implements GameState {
    makeMove(game: Game, player: Player, row: number, col: number): void {
        // throw error because you cannot make a move if the game is already in win state
        throw new InvalidMoveException(
            "Game is in win state; cannot make a move!"
        );
    }
}
