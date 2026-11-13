import { Board } from "./model/Board.js";
import { Player } from "./model/Player.js";
import { GameState } from "./states/GameState.js";
import { InProgressState } from "./states/InProgressState.js";
import { ColumnWinningStrategy } from "./strategies/ColumnWinningStrategy.js";
import { DiagonalWinningStrategy } from "./strategies/DiagonalWinningStrategy.js";
import { RowWinningStrategy } from "./strategies/RowWinningStrategy.js";
import { WinningStrategy } from "./strategies/WinningStrategy.js";

export class Game {
    private readonly _board: Board;
    private readonly _player1: Player;
    private readonly _player2: Player;
    private _currentPlayer: Player;
    private _winner?: Player;
    private _state: GameState;
    private readonly _winningStrategies: readonly WinningStrategy[];

    constructor(boardSize: number, player1: Player, player2: Player) {
        this._board = new Board(boardSize);
        this._player1 = player1;
        this._player2 = player2;
        this._currentPlayer = player1;
        // starting state of the game will be in progress:
        this._state = new InProgressState();
        // create an immutable list of strategies
        this._winningStrategies = [
            new RowWinningStrategy(),
            new ColumnWinningStrategy(),
            new DiagonalWinningStrategy(),
        ];
    }

    // business logic:

    makeMove(player: Player, row: number, col: number): void {
        // STATE DESIGN PATTERN:
        // the logic to make a move will depend on the current state of the game, i.e.
        // whether the game is in progress, draw or win state.
        // we can write all that logic here itself using conditionals, but a cleaner way is to split that logic
        // as written inside the state package
        this._state.makeMove(this, player, row, col);
    }

    checkWinner(player: Player): boolean {
        // STRATEGY DESIGN PATTERN:
        // we could've written all the logic here itself to check
        // if there's a combo of 3 of this player's symbol occuring in a row, col or diagonally.
        // but a cleaner approach is to do this checking in 3 different strategies:
        // RowWinningStrategy, ColWinningStrategy, DiagonalWinningStrategy - written in the strategies package
        for (const winningStrategy of this._winningStrategies) {
            if (winningStrategy.checkWinner(this._board, player)) return true;
        }
        return false;
    }

    switchPlayer(): void {
        this._currentPlayer = this._currentPlayer === this._player1
            ? this._player2
            : this._player1;
    }

    // getters and setters for the private attributes:

    get currentPlayer(): Player {
        return this._currentPlayer;
    }

    get board(): Board {
        return this._board;
    }

    get state(): GameState {
        return this._state;
    }

    setState(state: GameState): void {
        this._state = state;
    }

    get winner(): Player | undefined {
        return this._winner;
    }

    setWinner(player: Player): void {
        this._winner = player;
    }
}
