import { Symbol } from "../enums/Symbol.js";

export class Cell {
    private _symbol?: Symbol;

    // getters and setters for private attributes:

    setSymbol(symbol: Symbol): void {
        this._symbol = symbol;
    }

    get symbol(): Symbol | undefined {
        return this._symbol;
    }

    isEmpty(): boolean {
        return this._symbol === undefined;
    }
}
