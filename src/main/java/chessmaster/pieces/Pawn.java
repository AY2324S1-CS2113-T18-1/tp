package chessmaster.pieces;

import chessmaster.game.Coordinate;

public class Pawn extends ChessPiece {
    public static final String PAWN_WHITE = "p"; // ♙
    public static final String PAWN_BLACK = "P"; // ♟

    public static final int[][] DIRECTIONS = {
        UP_LEFT, UP_RIGHT, UP, UP_UP,
    };

    public Pawn(int row, int col, int color) {
        super(row, col, color);
    }

    @Override
    public Coordinate[][] getAvailablCoordinates() {
        Coordinate[][] result = new Coordinate[DIRECTIONS.length][0];

        for (int dir = 0; dir < DIRECTIONS.length; dir++) {
            int offsetX = DIRECTIONS[dir][0];
            int offsetY = DIRECTIONS[dir][1];

            if (position.isOffsetWithinBoard(offsetX, offsetY)) {
                Coordinate dest = position.addOffsetToCoordinate(offsetX, offsetY);
                result[dir] = new Coordinate[]{ dest };
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return color == ChessPiece.BLACK ? PAWN_BLACK : PAWN_WHITE;
    }
}
