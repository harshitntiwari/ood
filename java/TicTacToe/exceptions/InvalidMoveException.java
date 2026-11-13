package TicTacToe.exceptions;

public class InvalidMoveException extends RuntimeException {

    public InvalidMoveException(String msg) {
        super(msg);
    }
}
