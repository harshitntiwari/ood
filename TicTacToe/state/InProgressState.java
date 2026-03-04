package TicTacToe.state;

import TicTacToe.model.Game;
import TicTacToe.model.Player;

public class InProgressState implements GameState {

    @Override
    public void makeMove(Game game, Player player, int row, int col) {
        if (game.getCurrentPlayer() != player) {
            // throw error because wrong player is making the move
        }
        // make the move:
        game.getBoard().placeSymbol(row, col, player.getSymbol());

        // check for win or draw:
        if (game.checkWinner(player)) {
            game.setState(new WinState());
            game.setWinner(player);
            System.out.printf("%s has won the game!%n", player.getName());
        } else if (game.getBoard().isFull()) {
            game.setState(new DrawState());
            System.out.println("The game has ended in a draw!");
        } else {
            // game is still in progress, so switch players:
            game.switchPlayer();
        }
    }
}
