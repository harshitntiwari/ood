package tictactoe.states;

import tictactoe.Game;
import tictactoe.exceptions.InvalidMoveException;
import tictactoe.model.Player;

public class WinState implements GameState {

    @Override
    public void makeMove(Game game, Player player, int row, int col) {
        // throw error because you cannot make a move if the game is already in win state
        throw new InvalidMoveException(
            "Game is in win state; cannot make a move!"
        );
    }
}
