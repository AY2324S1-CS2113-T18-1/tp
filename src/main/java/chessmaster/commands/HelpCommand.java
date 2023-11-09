//@@author TriciaBK
package chessmaster.commands;

import chessmaster.game.Game;

public class HelpCommand extends Command {

    public static final String HELP_COMMAND_STRING = "help";

    public static final String[] HELP_STRINGS = {
        "Here are the commands you can use to play:",
        "move\t\tMove piece",
        "\t\tFormat: move [column][row] [column][row]",
        "\t\te.g. move a2 a3",
        "moves\t\tShow available moves for a piece",
        "\t\tFormat: moves [column][row]",
        "\t\te.g. moves a2",
        "show\t\tShow the chessboard",
        "rules\t\tObtain a quick refresher on the rules of chess",
        "legend\t\tView pieces representation",
        "history\t\tView history of all game moves",
        "stepback\tView the board as it was a certain number of moves ago",
        "\t\tFormat: stepback [number of moves to step back]",
        "\t\te.g. stepback 4",
        "abort\t\tAbort game",
    };

    @Override
    public CommandResult execute(Game game) {
        return new CommandResult(HELP_STRINGS);
    }

}
