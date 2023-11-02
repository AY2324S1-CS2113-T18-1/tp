//@@author TriciaBK
package chessmaster.commands;

import chessmaster.game.ChessBoard;
import chessmaster.pieces.Bishop;
import chessmaster.pieces.King;
import chessmaster.pieces.Knight;
import chessmaster.pieces.Pawn;
import chessmaster.pieces.Queen;
import chessmaster.pieces.Rook;
import chessmaster.ui.TextUI;

public class RulesCommand extends Command {

    public static final String RULES_COMAMND_STRING = "rules";

    private static final String[] RULES_STRINGS = {
        "Here are simple chess rules to get you started:",
        "",
        "Piece movement:",
        String.format("   Pawn (\"%s\") move forward one square but capture diagonally.", Pawn.PAWN_WHITE),
        String.format("   Rooks (\"%s\") move horizontally and vertically any number of squares.", Rook.ROOK_WHITE),
        String.format("   Knights (\"%s\") move in an L-shape: two squares in one direction and one square perpendicular.", Knight.KNIGHT_WHITE),
        String.format("   Bishops (\"%s\") move diagonally any number of squares.", Bishop.BISHOP_WHITE),
        String.format("   Queens (\"%s\") move horizontally, vertically, and diagonally any number of squares.", Queen.QUEEN_WHITE),
        String.format("   Kings (\"%s\") move one square in any direction.", King.KING_WHITE),
        "",
        "Special Rules:",
        "   Castling - King and rook move simultaneously to safeguard the king.",
        "   En Passant - Pawn capturing when moving two squares from starting position.",
        "   Pawn Promotion - Promote a pawn to another piece (except king) upon reaching the back rank.",
        "",
        "Objective:",
        "   Game ends when one player's king is in checkmate, under attack and can't escape capture.",
        "   The delivering player wins the game."
    };


    @Override
    public CommandResult execute(ChessBoard board, TextUI ui) {
        return new CommandResult(RULES_STRINGS);
    }

}
