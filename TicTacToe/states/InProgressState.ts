import { Game } from "../Game.js";
import { InvalidMoveException } from "../exceptions/InvalidMoveException.js";
import { Player } from "../model/Player.js";
import { GameState } from "./GameState.js";
import { DrawState } from "./DrawState.js";
import { WinState } from "./WinState.js";

export class InProgressState implements GameState {
  makeMove(game: Game, player: Player, row: number, col: number): void {
    if (game.currentPlayer !== player) {
      // throw error because wrong player is making the move
      throw new InvalidMoveException(`It is not your turn ${player.name}`);
    }
    // make the move:
    game.board.placeSymbol(row, col, player.symbol);

    // check for win or draw:
    if (game.checkWinner(player)) {
      game.setState(new WinState());
      game.setWinner(player);
      console.log(`${player.name} has won the game!`);
    } else if (game.board.isFull()) {
      game.setState(new DrawState());
      console.log("The game has ended in a draw!");
    } else {
      // game is still in progress, so switch players:
      game.switchPlayer();
    }
  }
}
