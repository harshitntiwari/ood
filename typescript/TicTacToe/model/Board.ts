import { Symbol } from "../enums/Symbol.js";
import { InvalidMoveException } from "../exceptions/InvalidMoveException.js";
import { Cell } from "./Cell.js";

export class Board {
  // the following two attributes are made as readonly,
  // b'cz we cannot change them once the Board class is instantiated
  private readonly _size: number;
  private readonly _board: Cell[][];

  constructor(size: number) {
    this._size = size;
    this._board = [];
    this.initializeBoard();
  }

  private initializeBoard(): void {
    for (let i = 0; i < this._size; i++) {
      this._board[i] = [];
      for (let j = 0; j < this._size; j++) {
        this._board[i][j] = new Cell();
      }
    }
  }

  // business logic:

  placeSymbol(row: number, col: number, symbol: Symbol): void {
    const cell = this.getCell(row, col);
    if (!cell.isEmpty()) {
      // throw error if the cell is already occupied
      throw new InvalidMoveException(
        "Invalid move; a symbol has already been placed at this position!",
      );
    }
    cell.setSymbol(symbol);
  }

  getCell(row: number, col: number): Cell {
    if (row < 0 || row >= this._size || col < 0 || col >= this._size) {
      // throw error if row/col are out of bounds
      throw new InvalidMoveException(
        "Invalid move; this position is out of bounds of the board!",
      );
    }
    return this._board[row][col];
  }

  isFull(): boolean {
    for (const cellRow of this._board) {
      for (const cell of cellRow) {
        if (cell.isEmpty()) return false;
      }
    }
    return true;
  }

  printBoard(): void {
    console.log("-----------------");
    for (let i = 0; i < this._size; i++) {
      process.stdout.write("| ");
      for (let j = 0; j < this._size; j++) {
        const symbol = this._board[i][j].symbol;
        process.stdout.write(
          `${symbol === undefined ? "-" : Symbol[symbol]} | `,
        );
      }
      console.log("\n-----------------");
    }
  }

  // getters for private attributes:

  get size(): number {
    return this._size;
  }
}
