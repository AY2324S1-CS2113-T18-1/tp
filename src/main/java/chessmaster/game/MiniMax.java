//@@author onx001
package chessmaster.game;
import chessmaster.pieces.ChessPiece;

import chessmaster.exceptions.ChessMasterException;

public class MiniMax {
    protected int depth;
    protected int maxDepth;
    protected int score;
    protected Move bestMove;
    protected ChessBoard board;
    protected Color color;
    protected Color opponentColor;
    protected BoardScoreTuple tuple;

    //declares all variables needed for the minimax algorithm
    public MiniMax(ChessBoard board, Color color, int maxDepth, int score) {
        this.board = board;
        this.color = color;
        this.opponentColor = color.getOppositeColour();
        this.maxDepth = maxDepth;
        this.score = score;
        this.tuple = new BoardScoreTuple(board, score, null);
    }
    
    /**
     * returns the best move tuple for the current player
     * @author onx001
     * @param tuple the BoardScoreTuple to be weighed
     * @param color the color of the current player
     * @param depth the current depth of the minimax algorithm
     * @param score the score of the current board
     * @param isMax whether the current player is the CPU or the player
     * @param maxDepth the maximum depth of the minimax algorithm
     * @return the best move tuple for the current player
     */
    public static BoardScoreTuple mostPoints(BoardScoreTuple tuple, Color color, int depth, 
        int score, boolean isMax, int maxDepth) {

        //gets all the moves for the current player
        ChessBoard board = tuple.getBoard();
        Color playerColor = isMax ? color : color.getOppositeColour();
        Move[] moves = board.getLegalMoves(playerColor);
        if (moves.length == 0) {
            return tuple;
        }
        BoardScoreTuple[] boards = new BoardScoreTuple[moves.length];
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        BoardScoreTuple bestTuple = null;

        //if the depth is the max depth, return the score of the board as base case
        if (depth == maxDepth) {
            int newscore = board.getPoints(color);
            return new BoardScoreTuple(board,newscore, null);
        }

        //for each move, clone the board and execute the move as a possibility
        for (int i = 0; i < moves.length; i++) {
            assert moves[i] != null : "moves[i] is null";
            ChessBoard newBoard = board.clone();
            Move move = moves[i];
            Coordinate from = move.getFrom();
            ChessPiece piece = newBoard.getPieceAtCoor(from);
            move.setPieceMoved(piece);
            try {
                newBoard.executeMove(move);
                //get the score of the board after the move
                int newScore = newBoard.getPoints(color);
                boards[i] = new BoardScoreTuple(newBoard, newScore, move);
            } catch (ChessMasterException e) {
                continue;
            }
        }
        
        //go through boards and find one with best points
        for (int i = 0; i < boards.length; i++) {
            BoardScoreTuple iterTuple = boards[i];
            assert iterTuple != null : "iterTuple is null";
            assert iterTuple.getMove() != null : "iterTuple move is null";

            //recursively call mostPoints to find the best move for current board
            BoardScoreTuple tuple1 = mostPoints(iterTuple, color, depth + 1, score, !isMax, maxDepth);

            //prints score as branch
            //System.out.println("|" + "---".repeat(depth) + " " + tuple1.getScore() + tuple.getMove());
          
            //Sets new score to current child score
            int newScore = tuple1.getScore();

            if (isMax) {
                //maximises child score if CPU turn
                if (newScore > bestScore) {
                    bestScore = newScore;
                }
            } else {
                //minimises child score if player turn
                if (newScore < bestScore) {
                    bestScore = newScore;
                }
            }

            //set current tuple based on best child score
            bestTuple = bestScore == newScore ? iterTuple : bestTuple;
        }

        return bestTuple;
    }

    /** 
     * Kicks off minimax algorithm and returns the best move for the current player
     */
    public Move getBestMove() {
        BoardScoreTuple bestTuple = mostPoints(tuple, color, 0, score, true, maxDepth);
        Move bestMove = bestTuple.getMove();
        return bestMove;
    }


}
