package chessmaster.ui;

public class UiMessages {

    public static final String[] WELCOME_MESSAGE = { 
        "Hey there, chess geek! You have stumbled upon the one and only: ",
        "    ________                      __  ___           __           ",
        "   / ____/ /_  ___  __________   /  |/  /___ ______/ /____  _____",
        "  / /   / __ \\/ _ \\/ ___/ ___/  / /|_/ / __ `/ ___/ __/ _ \\/ ___/",
        " / /___/ / / /  __(__  |__  )  / /  / / /_/ (__  ) /_/  __/ /    ",
        " \\____/_/ /_/\\___/____/____/  /_/  /_/\\__,_/____/\\__/\\___/_/     ", "",
        "where CHESS becomes an exciting journey of strategy and skill!" };

    public static final String EXIST_PREV_GAME_MESSAGE = 
        "You have an ongoing previous chess game. Continue game? [y/n] ";
    public static final String CONTINUE_PREV_GAME_ERROR_MESSAGE = 
        "Invalid input! Please enter either 'y' for yes or 'n' for no: ";
    public static final String CONTINUE_PREV_GAME_MESSAGE = "Great! Continuing previous game as %s at difficulty %d";

    public static final String CHOOSE_PLAYER_COLOR_MESSAGE = "Choose your starting color to start new game! [b/w] ";
    public static final String CHOOSE_PLAYER_COLOR_ERROR_MESSAGE = 
        "Invalid input! Please enter either 'b' for Black or 'w' for White: ";
    public static final String START_NEW_GAME_MESSAGE = "Great! Starting new game as %s";

    public static final String LOAD_BOARD_ERROR_MESSAGE = "No valid previous game found. Starting new chess game...";
    public static final String CPU_MOVE_MESSAGE = "ChessMaster moved %s from %s to %s";
    public static final String CPU_MOVE_AND_CAPTURE_MESSAGE = "ChessMaster moved %s from %s to %s " +
            "and captured your %s!";

    public static final String PROMPT_PROMOTE_MESSAGE = 
        "Promote the pawn at %s! Choose piece to promote to [b/q/r/n]: ";
    public static final String PROMPT_PROMOTE_INVALID_MESSAGE = 
        "Invalid piece! Enter b(Bishop), q(Queen), r(Rook) or n(Knight): ";
    
    public static final String CHOOSE_DIFFICULTY_MESSAGE = 
        "Choose difficulty level [1/2/3]: ";
    public static final String CHOOSE_DIFFICULTY_ERROR_MESSAGE =
        "Invalid input! Please enter either '1', '2' or '3': ";

    public static final String CHESSMASTER_THINKING_MESSAGE = "ChessMaster is thinking of a move...";

    public static final String HUMAN_WIN_MESSAGE = "Congratulations! You have won as %s! :)";
    public static final String CPU_WIN_MESSAGE = "Oh no! You have lost as %s. Please try harder next time :(";
}
