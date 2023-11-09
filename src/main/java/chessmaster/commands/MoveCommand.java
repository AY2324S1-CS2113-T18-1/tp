//@@author TongZhengHong
package chessmaster.commands;

import chessmaster.exceptions.ChessMasterException;
import chessmaster.exceptions.InvalidMoveException;
import chessmaster.game.ChessBoard;
import chessmaster.game.Game;
import chessmaster.game.Move;
import chessmaster.parser.Parser;

public class MoveCommand extends Command {

    public static final String MOVE_COMMAND_STRING = "move";
    
    public static final String NO_MOVE_FOUND_STRING = 
        "Oops! It seems you forgot to provide the 'from' and 'to' squares!";
    public static final String MOVE_FORMAT_STRING = 
        "Format: moves <from> <to>";
    public static final String MOVE_EXAMPLE_STRING = "Example: move e2 e4";

    private static final String EMPTY_PAYLOAD_ERROR_STRING = NO_MOVE_FOUND_STRING + System.lineSeparator() + 
        MOVE_FORMAT_STRING + System.lineSeparator() + MOVE_EXAMPLE_STRING;
    private static final String MOVE_PIECE_MESSAGE = "You moved %s from %s to %s";
    private static final String MOVE_AND_CAPTURE_MESSAGE = "You moved %s from %s to %s and captured the opponent's %s!";

    private String userInput;
    private Move move;

    public MoveCommand(String inputString) {
        this.userInput = inputString;
    }

    /**
     * Executes the command based on user input, which is expected to consist of two
     * algebraic coordinate strings separated by whitespace.
     *
     * @return A CommandResult object containing the result of the command.
     * @throws ChessMasterException If the user input cannot be parsed into two
     *                                  coordinate objects.
     */
    @Override
    public CommandResult execute(Game game) throws ChessMasterException {
        ChessBoard board = game.getBoard();

        if (userInput.isBlank()) {
            throw new InvalidMoveException(EMPTY_PAYLOAD_ERROR_STRING);
        }
        
        move = Parser.parseMove(userInput, board, true);
        if (!move.isValid(board)) {
            throw new InvalidMoveException();
        }

        String pieceString = move.getPieceMoved().getClass().getSimpleName();

        String returnString;
        if (move.hasCapturedAPiece()) {
            returnString = String.format(
                    MOVE_AND_CAPTURE_MESSAGE,
                    pieceString, move.getFrom(), move.getTo(), move.getPieceCaptured().getPieceName()
            );
        } else {
            returnString = String.format(MOVE_PIECE_MESSAGE, pieceString, move.getFrom(), move.getTo());
        }
        return new CommandResult(returnString);
    }

    public Move getMove() throws InvalidMoveException {
        if (move == null) {
            throw new InvalidMoveException();
        }
        return move;
    }

}
