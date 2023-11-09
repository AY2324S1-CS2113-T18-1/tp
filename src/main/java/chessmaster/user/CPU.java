package chessmaster.user;

import chessmaster.game.ChessBoard;
import chessmaster.game.Color;
import chessmaster.game.Coordinate;
import chessmaster.game.MiniMax;
import chessmaster.game.Move;
import chessmaster.game.MoveFactory;
import chessmaster.pieces.ChessPiece;

import java.util.Random;

public class CPU extends Player {

    private static final int MAX_LOOP_ITERATIONS = 16;

    private static final int RANDOM_SEED = 100;
    private final Random rand = new Random(RANDOM_SEED);

    public CPU(Color colour, ChessBoard board) {
        super(colour, board);
    }

    /**
     * The main function behind the CPU's logic, this function randomly selects an active (non-captured) piece
     * from the CPU's pieces which has possible valid moves.
     * @param board The board to extract the random move from.
     * @return A random move
     */
    public Move getRandomMove(ChessBoard board) {
        // 1. Get a random piece that
        //    - isn't captured
        //    - has possible legal moves
        ChessPiece randomPiece = getRandomPiece();

        // Need a cap on the number of pieces it checks to prevent an infinite loop when no moves are possible
        // on the CPUs side.
        int iter = 0;
        while (iter < MAX_LOOP_ITERATIONS
                && (randomPiece.getIsCaptured()
                || randomPiece.getLegalCoordinates(board).length == 0)) {
            randomPiece = getRandomPiece();
            iter++;
        }

        return getRandomMoveFromPiece(randomPiece, board);
    }

    //@@author onx001

    public Move getBestMove(ChessBoard board, int maxDepth) {
        MiniMax miniMax = new MiniMax(board, this.colour, maxDepth, 0);
        Move bestMove = miniMax.getBestMove();
        Coordinate from = bestMove.getFrom();
        ChessPiece piece = board.getPieceAtCoor(from);
        bestMove.setPieceMoved(piece);
        return bestMove;
    }

    //@@author

    private ChessPiece getRandomPiece() {
        return this.pieces.get(rand.nextInt(pieces.size()));
    }

    /**
     * This function returns any random valid move that a given piece can make on a given board.
     * @param piece The piece to extract a random move from.
     * @param board The board the piece is currently on.
     * @return A random move the given piece can make on the given board.
     */
    private Move getRandomMoveFromPiece(ChessPiece piece, ChessBoard board) {
        Coordinate[] allPossibleMoves = piece.getLegalCoordinates(board);
        int randIndex = rand.nextInt(allPossibleMoves.length);
        Coordinate randomDestination = allPossibleMoves[randIndex];

        return MoveFactory.createMove(board, piece.getPosition(), randomDestination);
    }

}
