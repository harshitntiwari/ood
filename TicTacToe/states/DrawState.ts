import { Game } from "../Game.js";
import { InvalidMoveException } from "../exceptions/InvalidMoveException.js";
import { Player } from "../model/Player.js";
import { GameState } from "./GameState.js";

export class DrawState implements GameState {
    makeMove(game: Game, player: Player, row: number, col: number): void {
        // throw error because you cannot make a move if the game is already in draw state
        throw new InvalidMoveException(
            "Game is in draw state; cannot make a move!"
        );
    }
}
