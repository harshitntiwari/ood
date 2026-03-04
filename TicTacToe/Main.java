package TicTacToe;

import TicTacToe.enums.Symbol;
import TicTacToe.model.Player;

class Main {

    public static void main(String[] args) {
        Player player1 = new Player("Tesla", Symbol.X);
        Player player2 = new Player("Edison", Symbol.O);

        TicTacToeManager manager = TicTacToeManager.getInstance();
        manager.createGame(3, player1, player2);
        manager.printBoard();

        manager.makeMove(player1, 0, 0);
        manager.makeMove(player2, 1, 0);
        manager.makeMove(player1, 0, 1);
        manager.makeMove(player2, 1, 1);
        manager.makeMove(player1, 0, 2);
    }
}
