package chessmaster.commands;

import chessmaster.exceptions.ChessMasterException;
import chessmaster.game.ChessBoard;
import chessmaster.game.Coordinate;
import chessmaster.game.Game;
import chessmaster.game.Move;
import chessmaster.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Collections;

public class StepbackCommand extends Command {

    public static final String STEPBACK_COMMAND_STRING = "stepback";
    private String userInput;

    public StepbackCommand(String inputString) {
        this.userInput = inputString;
    }

    @Override
    public CommandResult execute(Game game) throws ChessMasterException {
        ChessBoard currentBoard = game.getBoard();
        ChessBoard historyBoard = currentBoard.clone();
        int totalNumMoves = game.getNumMoves();

        int numMovesToStepBack = 0;
        try {
            numMovesToStepBack = Integer.parseInt(this.userInput);
            if (numMovesToStepBack > totalNumMoves) {
                throw new ChessMasterException("You cannot step back more moves than have been played!");
            } else if (numMovesToStepBack <= 0) {
                throw new ChessMasterException("Number of steps has to be greater than 0.");
            }
        } catch (NumberFormatException e) {
            throw new ChessMasterException("Please input an integer number of steps to step back.");
        }

        ArrayList<PlayerMoveTuple> allMoves = HistoryCommand.getAllMovesInChronologicalOrder(game);
        Collections.reverse(allMoves);

        for (int i = 0; i < numMovesToStepBack; i++) {
            Move previousMove = allMoves.get(i).getMove();

            // need to reverse the previousMove by moving the piece back from `to` to `from`.
            Coordinate moveTo = previousMove.getTo();
            Coordinate moveFrom = previousMove.getFrom();

            ChessPiece clonedPieceToMove = historyBoard.getPieceAtCoor(moveTo);
            clonedPieceToMove.updatePosition(moveFrom);

            // Need to check if the move captured a piece. If it did, need to replace `to` tile with pieceCaptured
            if (previousMove.hasCapturedAPiece()) {
                historyBoard.getTileAtCoor(moveTo).updateTileChessPiece(previousMove.getPieceCaptured());
            } else {
                historyBoard.getTileAtCoor(moveTo).setTileEmpty(moveTo);
            }

            // The `from` tile always gets the pieceMoved
            historyBoard.getTileAtCoor(moveFrom).updateTileChessPiece(clonedPieceToMove);
        }

        game.getUI().printChessBoard(historyBoard.getBoard());

        return new CommandResult(String.format(
                "Stepped back %d steps!\nUse `show` to see the current board.",
                numMovesToStepBack)
        );
    }

}
