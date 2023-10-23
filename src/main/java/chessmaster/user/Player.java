package chessmaster.user;

import chessmaster.game.ChessBoard;
import chessmaster.game.Color;
import chessmaster.game.Coordinate;
import chessmaster.game.Move;
import chessmaster.pieces.ChessPiece;

import java.util.ArrayList;

public abstract class Player {

    protected ArrayList<Move> moves;
    protected ArrayList<ChessPiece> pieces;
    protected Color colour;

    /**
     * A player is a dependency of the Game class. This class stores all move
     * history, all current pieces, and colour of each player. 
     * It also contains functions to request input from the user for the next 
     * move and to execute that move.
     * 
     * @param colour The ChessPiece.Colour desired for this player.
     */
    public Player(Color colour, ChessBoard board) {
        this.moves = new ArrayList<Move>();
        this.pieces = new ArrayList<ChessPiece>();
        this.colour = colour;
        initialisePieces(board);
    }

    /**
     * Adds a given move into the Player's move history.
     * 
     * @param move The given move to be added to history.
     */
    public void addMove(Move move) {
        this.moves.add(move);
    }

    public Color getColour() {
        return this.colour;
    }

    /**
     * Adds all the player's pieces to their ChessPiece array 
     * when Player is initialised.
     * 
     * @param board The new ChessBoard containing all 32 chess pieces.
     */
    private void initialisePieces(ChessBoard board) {
        for (int row = 0; row < ChessBoard.SIZE; row++) {
            for (int col = 0; col < ChessBoard.SIZE; col++) {
                ChessPiece piece = board.getPieceAtCoor(new Coordinate(col, row));
                if (piece.isSameColorAs(this.colour)) {
                    this.pieces.add(piece);
                }
            }
        }
    }

    /**
     * Prints out all the player's pieces including whether it has been captured or
     * not.
     * Used for debugging purposes only.
     */
    public void printAllPieces() {
        for (ChessPiece p : pieces) {
            System.out.println("Piece: " + p);
            System.out.println("Colour: " + p.getColor().toString());
            System.out.println("Is captured: " + p.getIsCaptured());
        }
    }

    public boolean isHuman() {
        return this instanceof Human;
    }

    public boolean isCPU() {
        return this instanceof CPU;
    }
}
