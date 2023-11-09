package chessmaster.commands;

import chessmaster.exceptions.ChessMasterException;
import chessmaster.game.Game;
import chessmaster.game.Move;
import chessmaster.user.Player;

import java.util.ArrayList;

class PlayerMoveTuple {
    private Player player;
    private Move move;

    public PlayerMoveTuple(Player player, Move move) {
        this.player = player;
        this.move = move;
    }

    public Player getPlayer() {
        return player;
    }

    public Move getMove() {
        return move;
    }
}

public class HistoryCommand extends Command {

    public static final String HISTORY_COMMAND_STRING = "history";

    public static ArrayList<PlayerMoveTuple> getAllMovesInChronologicalOrder(Game game) {
        int totalMoves = game.getNumMoves();
        ArrayList<PlayerMoveTuple> allMoves = new ArrayList<>();

        Player currentPlayer = game.getCurrentPlayer();
        ArrayList<Move> currentPlayerMoves = currentPlayer.getMoves();
        Player opponent = currentPlayer.isHuman() ? game.getCPU() : game.getHuman();
        ArrayList<Move> opponentMoves = opponent.getMoves();

        int j = (int) Math.floor(totalMoves / 2);
        for (int i = 0; i <= j; i++) {
            // If totalMoves is odd, that means the current player is NOT the player
            // who started playing. The first move of the game was from opponent.
            if (totalMoves % 2 != 0) {
                allMoves.add(new PlayerMoveTuple(opponent, opponentMoves.get(i)));
                if (i == j) {
                    break; // to account for the odd number of moves
                }
                allMoves.add(new PlayerMoveTuple(currentPlayer, currentPlayerMoves.get(i)));
            } else {
                if (i == j) {
                    break;
                }
                allMoves.add(new PlayerMoveTuple(currentPlayer, currentPlayerMoves.get(i)));
                allMoves.add(new PlayerMoveTuple(opponent, opponentMoves.get(i)));
            }
        }

        return allMoves;
    }

    @Override
    public CommandResult execute(Game game) throws ChessMasterException {
        int numMoves = game.getNumMoves();
        if (numMoves == 0) {
            return new CommandResult("No moves have been played yet!");
        }

        ArrayList<PlayerMoveTuple> allMoves = getAllMovesInChronologicalOrder(game);

        StringBuilder returnStringBuilder = new StringBuilder();
        int moveCounter = 1;
        for (PlayerMoveTuple tuple : allMoves) {
            Move move = tuple.getMove();
            Player player = tuple.getPlayer();

            String moveString;
            if (move.hasCapturedAPiece()) {
                moveString = String.format(
                        "Move %d: %s moves %s from %s to %s capturing the opponent's %s!\n",
                        moveCounter,
                        player.getColour(),
                        move.getPieceMoved().getPieceName(),
                        move.getFrom(),
                        move.getTo(),
                        move.getPieceCaptured().getPieceName()
                );
            } else {
                moveString = String.format(
                        "Move %d: %s moves %s from %s to %s\n",
                        moveCounter,
                        player.getColour(),
                        move.getPieceMoved().getPieceName(),
                        move.getFrom(),
                        move.getTo()
                );
            }
            returnStringBuilder.append(moveString);

            moveCounter++;
        }

        return new CommandResult(returnStringBuilder.toString());
    }


}
