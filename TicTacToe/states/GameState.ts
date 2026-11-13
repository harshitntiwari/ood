import { Game } from "../Game.js";
import { Player } from "../model/Player.js";

export interface GameState {
    makeMove(game: Game, player: Player, row: number, col: number): void;
}
