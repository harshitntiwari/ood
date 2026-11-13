package TicTacToe.model;

import TicTacToe.enums.Symbol;
import TicTacToe.exceptions.InvalidMoveException;

public class Board {

    // the following two attributes are made as final,
    // b'cz we cannot change them once the Board class is instantiated
    private final int size;
    private final Cell[][] board;

    public Board(int size) {
        this.size = size;
        this.board = new Cell[size][size];
        this.initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    // business logic:

    public void placeSymbol(int row, int col, Symbol symbol) {
        Cell cell = this.getCell(row, col);
        if (!cell.isEmpty()) {
            // throw error if the cell is already occupied
            throw new InvalidMoveException(
                "Invalid move; a symbol has already been placed at this position!"
            );
        }
        cell.setSymbol(symbol);
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            // throw error if row/col are out of bounds
            throw new InvalidMoveException(
                "Invalid move; this position is out of bounds of the board!"
            );
        }
        return board[row][col];
    }

    public boolean isFull() {
        for (Cell[] cellRow : board) {
            for (Cell cell : cellRow) {
                if (cell.isEmpty()) return false;
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.println("-----------------");
        for (int i = 0; i < size; i++) {
            System.out.print("| ");
            for (int j = 0; j < size; j++) {
                Symbol symbol = board[i][j].getSymbol();
                System.out.print((symbol == null ? '-' : symbol) + " | ");
            }
            System.out.println("\n-----------------");
        }
    }

    // getters and setters for private attributes:

    public int getSize() {
        return size;
    }
}
