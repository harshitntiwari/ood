import { Symbol } from "../enums/Symbol.js";

export class Player {
    private _name: string;
    private _symbol: Symbol;

    constructor(name: string, symbol: Symbol) {
        this._name = name;
        this._symbol = symbol;
    }

    // getters for private attributes:

    get name(): string {
        return this._name;
    }

    get symbol(): Symbol {
        return this._symbol;
    }
}
