package chessmaster.game;

import chessmaster.pieces.ChessPiece;

public class MoveFactory {

    public static Move createMove(ChessBoard board, Coordinate from, Coordinate to) {
        ChessPiece pieceMoved = board.getPieceAtCoor(from);

        Move move;
        if (board.isTileOccupied(to)) {
            ChessPiece pieceCaptured = board.getPieceAtCoor(to);
            move = new Move(from, to, pieceMoved, pieceCaptured);
        } else {
            move = new Move(from, to, pieceMoved);
        }

        return move;
    }

}
