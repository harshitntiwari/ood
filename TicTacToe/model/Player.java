package TicTacToe.model;

import TicTacToe.enums.Symbol;

public class Player {

    private String name;
    private Symbol symbol;

    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    // getters and setters for private attributes:

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
