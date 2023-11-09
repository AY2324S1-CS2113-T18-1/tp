package chessmaster.game;

import java.util.ArrayList;

import chessmaster.exceptions.ChessMasterException;
import chessmaster.exceptions.InvalidMoveException;
import chessmaster.parser.Parser;
import chessmaster.pieces.ChessPiece;
import chessmaster.pieces.EmptyPiece;
import chessmaster.pieces.King;
import chessmaster.pieces.Pawn;
import chessmaster.user.CPU;
import chessmaster.user.Human;


public class ChessBoard {

    public static final int SIZE = 8;
    public static final int TOP_ROW_INDEX = 0;
    public static final int BOTTOM_ROW_INDEX = 7;
    public static final int MAX_PIECES = 16;
    public static final String PROMOTE_MOVE_STRING = "p";

    private static final String[][] STARTING_CHESSBOARD_BLACK = { 
        { "r", "n", "b", "q", "k", "b", "n", "r" }, 
        { "p", "p", "p", "p", "p", "p", "p", "p" }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { "P", "P", "P", "P", "P", "P", "P", "P" }, 
        { "R", "N", "B", "Q", "K", "B", "N", "R" }, 
    };

    private static final String[][] STARTING_CHESSBOARD_WHITE = { 
        { "R", "N", "B", "Q", "K", "B", "N", "R" }, 
        { "P", "P", "P", "P", "P", "P", "P", "P" },
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { ".", ".", ".", ".", ".", ".", ".", "." }, 
        { "p", "p", "p", "p", "p", "p", "p", "p" },
        { "r", "n", "b", "q", "k", "b", "n", "r" }, 
    };

    private Color playerColor;

    private int difficulty = 4;

    private final ChessTile[][] board = new ChessTile[SIZE][SIZE];

    public ChessBoard(Color playerColor) {
        this.playerColor = playerColor;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String chessPieceString = playerColor.isBlack()
                    ? STARTING_CHESSBOARD_BLACK[row][col]
                    : STARTING_CHESSBOARD_WHITE[row][col];
                ChessPiece initialPiece = Parser.parseChessPiece(chessPieceString, row, col);
                board[row][col] = new ChessTile(initialPiece);
                assert (board[row][col] != null);
            }
        }
        this.playerColor = playerColor;
    }

    public ChessBoard(Color playerColor, ChessTile[][] boardTiles) {
        this.playerColor = playerColor;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = boardTiles[row][col];
            }
        }
    }

    /**
     * Gets a copy of the current chessboard as a 2D array of ChessTile objects.
     *
     * This method creates a deep copy of the chessboard, allowing for the independent
     * examination of the board's state without modifying the original chessboard.
     *
     * @return A 2D array copy of ChessTile objects representing the current state of the chessboard.
     */
    public ChessTile[][] getBoard() {
        ChessTile[][] copy = new ChessTile[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }

    public Color getPlayerColor() {
        return this.playerColor;
    }

    //@@author onx001
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public boolean isChecked(Color color) {
        Move[] moves = getAllMoves(color.getOppositeColour());
        for (Move move : moves) {
            Coordinate to = move.getTo();
            if (this.getPieceAtCoor(to) instanceof King) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnPassant() {
        //Checks all chess pieces for en passant
        for (int row = 0; row < ChessBoard.SIZE; row++) {
            for (int col = 0; col < ChessBoard.SIZE; col++) {
                Coordinate coor = new Coordinate(col, row);
                ChessPiece piece = getPieceAtCoor(coor);
                if (piece.isEnPassant()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Coordinate getEnPassantCoor() {
        //Checks all chess pieces for en passant
        for (int row = 0; row < ChessBoard.SIZE; row++) {
            for (int col = 0; col < ChessBoard.SIZE; col++) {
                Coordinate coor = new Coordinate(col, row);
                ChessPiece piece = getPieceAtCoor(coor);
                if (piece.isEnPassant()) {
                    return coor;
                }
            }
        }
        return null;
    }

    public boolean isCheckmated(Color color) {
        Move[] moves = getLegalMoves(color);
        return moves.length == 0;
    }


    public Move[] getAllMoves(Color color) {
        //Declare arraylist of moves as allMoves
        ArrayList<Move> allMoves = new ArrayList<>();

        for (int row = 0; row < ChessBoard.SIZE; row++) {
            for (int col = 0; col < ChessBoard.SIZE; col++) {
                Coordinate currentCoor = new Coordinate(col, row);
                ChessPiece piece = getPieceAtCoor(currentCoor);

                if (piece.isSameColorAs(color)) {
                    Coordinate[] possibleCoordinates = piece.getLegalCoordinates(this);
                    for (Coordinate possible: possibleCoordinates) {
                        allMoves.add(MoveFactory.createMove(this, currentCoor, possible));
                    }
                }
            }
        }
        return allMoves.toArray(new Move[0]);
    }

    public Move[] getLegalMoves(Color color) {
        Move[] moves = getAllMoves(color);
        ArrayList<Move> uncheckedMoves = new ArrayList<>();
        for (Move move : moves) {
            ChessBoard newBoard = this.clone();
            Coordinate from = move.getFrom();
            ChessPiece piece = newBoard.getPieceAtCoor(from);
            Move moveCopy = new Move(from, move.getTo(), piece);
            try {
                newBoard.executeMove(moveCopy);
            } catch (InvalidMoveException e) {
                continue;
            }
            if (!newBoard.isChecked(color)) {
                uncheckedMoves.add(move);
            }
        }
        return uncheckedMoves.toArray(new Move[0]);
    }

    //@@author TongZhengHong
    public void setPromotionPiece(Coordinate coord, ChessPiece promotedPiece) {
        getTileAtCoor(coord).updateTileChessPiece(promotedPiece);
    }

    /**
     * Gets the ChessTile object located at the specified coordinate on the
     * chessboard.
     *
     * @param coor The coordinate of the position to retrieve the tile for.
     * @return The ChessTile object at the specified coordinate.
     */
    public ChessTile getTileAtCoor(Coordinate coor) {
        return board[coor.getY()][coor.getX()];
    }

    /**
     * Gets the chess piece located at the specified coordinate on the chessboard.
     *
     * @param coor The coordinate of the position to check.
     * @return The ChessPiece object at the specified coordinate. If empty piece at
     *         coordinate, returns EmptyPiece object
     */
    public ChessPiece getPieceAtCoor(Coordinate coor) {
        ChessTile tile = getTileAtCoor(coor);
        return tile.getChessPiece();
    }

    /**
     * Executes a chess move on the chessboard.
     *
     * @param move The Move object representing the move to be executed.
     * @throws InvalidMoveException If the move is not valid according to the game
     *                              rules.
     */

    public void executeMove(Move move) throws InvalidMoveException {
        Coordinate startCoor = move.getFrom();
        Coordinate destCoor = move.getTo();
        ChessPiece chessPiece = move.getPieceMoved();


        chessPiece.setHasMoved();
        chessPiece.updatePosition(destCoor);
        getTileAtCoor(startCoor).setTileEmpty(startCoor);
        getTileAtCoor(destCoor).updateTileChessPiece(chessPiece);

        //@@author onx001
        if (move.isLeftCastling() && startCoor.isOffsetWithinBoard(-4, 0)) {
            Coordinate rookStartCoor = startCoor.addOffsetToCoordinate(-4, 0);
            Coordinate rookDestCoor = startCoor.addOffsetToCoordinate(-1, 0);
            ChessPiece rook = getTileAtCoor(rookStartCoor).getChessPiece();

            rook.setHasMoved();
            rook.updatePosition(rookDestCoor);

            getTileAtCoor(rookStartCoor).setTileEmpty(rookStartCoor);
            getTileAtCoor(rookDestCoor).updateTileChessPiece(rook);

        } else if (move.isRightCastling() && startCoor.isOffsetWithinBoard(3, 0)) {
            Coordinate rookStartCoor = startCoor.addOffsetToCoordinate(3, 0);
            Coordinate rookDestCoor = startCoor.addOffsetToCoordinate(1, 0);
            ChessPiece rook = getTileAtCoor(rookStartCoor).getChessPiece();

            rook.setHasMoved();
            rook.updatePosition(rookDestCoor);

            getTileAtCoor(rookStartCoor).setTileEmpty(rookStartCoor);
            getTileAtCoor(rookDestCoor).updateTileChessPiece(rook);
        } else if (move.getPieceMoved() instanceof Pawn && hasEnPassant()) {
            Coordinate to = move.getTo();
            Coordinate enPassantCoor = getEnPassantCoor();
            if (move.getPieceMoved().getColor() == playerColor) {
                enPassantCoor = enPassantCoor.addOffsetToCoordinate(0, -1);
            } else {
                enPassantCoor = enPassantCoor.addOffsetToCoordinate(0, 1);
            }
            if (to.equals(enPassantCoor)) {
                ChessPiece enPassantPiece = getPieceAtCoor(enPassantCoor);
                getTileAtCoor(enPassantCoor).setTileEmpty(enPassantCoor);
                enPassantPiece.setIsCaptured();
            }
        }

        //clear all en passants
        for (int row = 0; row < ChessBoard.SIZE; row++) {
            for (int col = 0; col < ChessBoard.SIZE; col++) {
                Coordinate coor = new Coordinate(col, row);
                ChessPiece piece = getPieceAtCoor(coor);
                if (piece.isEnPassant()) {
                    piece.clearEnPassant();
                }
            }
        }
    }

    public void executeMoveWithCheck(Move move) throws InvalidMoveException {
        
        if (move.isValidWithCheck(this)) {
            executeMove(move);
        } else {
            throw new InvalidMoveException("Move Causes a check");
        }
    }

    //@@author ken-ruster
    public boolean canPromote(Move move) {
        ChessPiece piece = move.getPieceMoved();
        Coordinate endCoord = move.getTo();

        if (!piece.isPawn()) {
            return false;
        }

        if (isPieceFriendly(piece)) {
            return endCoord.getY() == TOP_ROW_INDEX;
        }

        if (isPieceOpponent(piece)) {
            return endCoord.getY() == BOTTOM_ROW_INDEX;
        }

        return false;
    }

    //@@author onx001
    public boolean isEndGame() {
        return isCheckmated(playerColor) || isCheckmated(playerColor.getOppositeColour());
    }

    public Color getWinningColor() {
        
        if (isCheckmated(playerColor)) {
            return playerColor.getOppositeColour();
        } else if (isCheckmated(playerColor.getOppositeColour())) {
            return playerColor;
        } else {
            return Color.EMPTY;
        }
    }
    

    public int getPoints(Color color) {
        int points = 0;
        int enemyPoints = 0;
        boolean isUpright;

        if (this.playerColor == color) {
            isUpright = true;
        } else {
            isUpright = false;
        }

        for (int row = 0; row < ChessBoard.SIZE; row++) {
            for (int col = 0; col < ChessBoard.SIZE; col++) {
                Coordinate coor = new Coordinate(col, row);
                ChessPiece piece = this.getPieceAtCoor(coor);

                if (piece.isSameColorAs(color)) {
                    points += piece.getPoints(isUpright);
                } else {
                    enemyPoints += piece.getPoints(isUpright);
                }
            }
        }

        return points - enemyPoints;
    }


    public ChessBoard clone() {
        String boardString = this.toString();
        return toBoard(boardString);
    }



    public ChessBoard toBoard(String board) {
        ChessTile[][] boardTiles = new ChessTile[SIZE][SIZE];
        int row = 0;
        int col = 0;
        for (int i = 0; i < board.length(); i++) {
            String pieceString = board.substring(i, i + 1);
            ChessPiece piece = Parser.parseChessPiece(pieceString, row, col);
            assert (row < SIZE);
            assert (col < SIZE);
            boardTiles[row][col] = new ChessTile(piece);
            col++;
            if (col == SIZE) {
                col = 0;
                row++;
            }
            if (row == SIZE) {
                break;
            }
        }
        return new ChessBoard(this.playerColor, boardTiles);
    }

    //@@author ken-ruster
    public void executeMoveArray(ArrayList<String> moves, Human human, CPU cpu) throws ChessMasterException {
        boolean isPlayersTurn = playerColor.isWhite();

        for (String move : moves) {
            String[] moveCommandArray = move.split("\\s+");
            boolean isPromote = moveCommandArray[0].equals(PROMOTE_MOVE_STRING);

            if (!isPromote) {
                Move toExecute = Parser.parseMove(move, this, false);
                assert toExecute.isValid(this) : "Move in file is not valid!";
                this.executeMove(toExecute);

                if (isPlayersTurn) {
                    human.addMove(toExecute);
                } else {
                    cpu.addMove(toExecute);
                }
            } else {
                Coordinate coord = Coordinate.parseAlgebraicCoor(moveCommandArray[1]);
                ChessPiece oldPiece = this.getPieceAtCoor(coord);
                assert this.canPromote(new Move(coord, coord, oldPiece))
                        : "Move in file tries to make an invalid promotion!";
                ChessPiece newPiece = Parser.parsePromote(oldPiece, moveCommandArray[2]);
                this.setPromotionPiece(coord, newPiece);

                PromoteMove promoteMove = new PromoteMove(coord, newPiece);
                if (isPlayersTurn) {
                    human.addMove(promoteMove);
                } else {
                    cpu.addMove(promoteMove);
                }
            }

            isPlayersTurn = !isPlayersTurn;
        }
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (ChessTile[] row : board) {
            for (ChessTile tile : row) {
                boardString.append(tile.toFileString());
            }
        }
        return boardString.toString();
    }

    //@@author TongZhengHong
    public boolean isPieceFriendly(ChessPiece otherPiece) {
        return this.playerColor == otherPiece.getColor();
    }

    public boolean isPieceOpponent(ChessPiece otherPiece) {
        return this.playerColor != otherPiece.getColor();
    }

    public boolean isTileOccupied(Coordinate coord) {
        ChessPiece piece = this.getPieceAtCoor(coord);
        return piece == null || !(piece instanceof EmptyPiece);
    }

    //@@author ken_ruster
    public boolean equals(ChessBoard otherBoard) {
        String otherBoardString = otherBoard.toString();

        return otherBoardString.equals(this.toString());
    }
}
