import { Symbol } from "./enums/Symbol.js";
import { Game } from "./Game.js";
import { Player } from "./model/Player.js";

function main(): void {
    const player1 = new Player("Tesla", Symbol.X);
    const player2 = new Player("Edison", Symbol.O);

    const game = new Game(3, player1, player2);
    console.log(`Game started: ${player1.name} vs ${player2.name}`);
    game.board.printBoard();

    console.log(`Player ${player1.name} makes move at (0, 0)`);
    game.makeMove(player1, 0, 0);
    game.board.printBoard();
}

main();
