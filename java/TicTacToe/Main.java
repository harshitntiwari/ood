package TicTacToe;

import TicTacToe.enums.Symbol;
import TicTacToe.model.Player;

class Main {

    public static void main(String[] args) {
        Player player1 = new Player("Tesla", Symbol.X);
        Player player2 = new Player("Edison", Symbol.O);

        Game game = new Game(3, player1, player2);
        System.out.println("Game started: " + player1.getName() + " vs " + player2.getName());
        game.getBoard().printBoard();

        System.out.printf("Player %s makes move at (0, 0)%n", player1.getName());
        game.makeMove(player1, 0, 0);
        game.getBoard().printBoard();
    }
}
