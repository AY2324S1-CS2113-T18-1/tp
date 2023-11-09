package chessmaster.game;

import chessmaster.pieces.ChessPiece;

public class PromoteMove extends Move {
    public static final String PROMOTE_MOVE_STRING = "p %s %s";

    public PromoteMove (Coordinate coord, ChessPiece piece) {
        super(coord, coord, piece);
    }

    /**
     * Converts the move to a format to be saved in the .txt file.
     * Only called after the piece has been promoted to
     *
     * @return
     */
    @Override
    public String toFileString() {
        String out = String.format(PROMOTE_MOVE_STRING, this.getFrom(), this.getPieceMoved().toString());
        return out;
    }
}
