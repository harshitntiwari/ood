package TicTacToe.model;

import TicTacToe.enums.Symbol;

public class Cell {

    private Symbol symbol;
    private boolean isEmpty;

    public Cell() {
        this.isEmpty = true;
    }

    // getters and setters for private attributes:

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
        this.isEmpty = false;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }
}
