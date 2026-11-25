package tictactoe.model;

import tictactoe.enums.Symbol;

public class Cell {

    private Symbol symbol;

    public Cell() {}

    // getters and setters for private attributes:

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public boolean isEmpty() {
        return this.symbol == null;
    }
}
