# ChessMaster User Guide

## Introduction

ChessMasterCLI is a command-line interface (CLI) chess game designed to make learning and training accessible for beginners while offering an engaging experience for all skill levels. This sleek and user-friendly chess simulator provides a platform for novice players to build their skills and understanding of the game.

- [Quick Start](#quick-start)
- [Starting a game](#starting-a-game)
- [Gameplay]()
- [Features](#features)
    - Gameplay features
        - [Make a move](#make-a-move)
        - [Show available moves: `moves`](#show-available-moves-moves)
        - [Show current chess board: `show`](#show-current-chess-board-show)
    - Getting help
        - [Show chess rules: `rules`](#show-chess-rules-rules)
        - [Show commands: `help`](#show-commands-help)
        - [Legend: `legend`](#view-pieces-representation-legend)
        - [View history of game moves: `history`](#view-history-of-game-moves-history)
        - [Step back in history: `stepback`](#step-back-in-history-stepback)
    - [Aborting game: `abort`](#aborting-game-abort)
- [Command Summary](#command-summary)

## Quick Start

1. Ensure you have **Java 11 or above** installed in your system.

2. Download the latest version of `ChessMaster.jar` from [here](https://github.com/AY2324S1-CS2113-T18-1/tp/releases).

3. Open a terminal instance and navigate into the folder that contains the downloaded ChessMaster.jar file.

```bash
cd PATH_TO_JAR_FILE
```

4. Run the jar application with the following command:

```bash
java -jar ChessMaster.jar
```

You should be greeted by a welcome message from ChessMaster:
```
_________________________________________________________________

Hey there, chess geek! You have stumbled upon the one and only:
    ________                      __  ___           __
   / ____/ /_  ___  __________   /  |/  /___ ______/ /____  _____
  / /   / __ \/ _ \/ ___/ ___/  / /|_/ / __ `/ ___/ __/ _ \/ ___/
 / /___/ / / /  __(__  |__  )  / /  / / /_/ (__  ) /_/  __/ /
 \____/_/ /_/\___/____/____/  /_/  /_/\__,_/____/\__/\___/_/

where CHESS becomes an exciting journey of strategy and skill!
_________________________________________________________________
```

## Starting a game

Upon launching the application, ChessMaster checks if there is a previous game in progress. If a previous game exists, you'll be presented with the option to continue from where you left off. Please enter `y` for **yes** and `n` for **no**.

```
You have an ongoing previous chess game. Continue game? [y/n]
```

However, if you prefer to **start a fresh game** or if **no previous game is found**, ChessMaster will prompt you to select your preferred color—whether it's "White" or "Black." Please enter `b` for **black** and `w` for **white**.

```
Choose your starting color to start new game! [b/w]
```

Next, you can choose the difficulty of the AI you will be pitched against! The current supported difficulty levels range from 1 to 4. Please enter the number corresponding to your preferred difficulty level.
```
Choose your difficulty level! [1/2/3/4]
```

Now, you'll be off to a rewarding journey of enhancing your chess skills and enjoying the timeless game of strategy and tactics with ChessMaster!

## Gameplay

The gameplay system of ChessMaster provides you with the flexibility to enter either a **command** or a **move** during your turn. 

- If you opt for a **command**, the system will promptly solicit your next action, allowing you to access various [features](#features) seamlessly. However, when you decide to make a move, the system will evaluate your input for validity. 
- Only when you enter a **valid move** will your turn be passed to the CPU, enabling it to respond with its strategic move. 

Additionally, every time a move is executed, ChessMaster provides a comprehensive view of the chessboard, displaying the state of the game following the previous move.

<table>
<tr>
    <th>User Move</th>
    <th>CPU move</th>
</tr>
<tr>
<td>
  
<pre><code>
d2 d4
_________________________________________________________________

You moved Pawn from d2 to d4
_________________________________________________________________

                 (a) (b) (c) (d) (e) (f) (g) (h)
                _________________________________
            (8) | R | N | B | Q | K | B | N | R | (8)
                _________________________________
            (7) | P | P | P | P | P | P | P | P | (7)
                _________________________________
            (6) |   |   |   |   |   |   |   |   | (6)
                _________________________________
            (5) |   |   |   |   |   |   |   |   | (5)
                _________________________________
            (4) |   |   |   |(p)|   |   |   |   | (4)
                _________________________________
            (3) |   |   |   |   |   |   |   |   | (3)
                _________________________________
            (2) | p | p | p |( )| p | p | p | p | (2)
                _________________________________
            (1) | r | n | b | q | k | b | n | r | (1)
                _________________________________
                 (a) (b) (c) (d) (e) (f) (g) (h)
</code></pre>

</td>
<td>

<pre><code>

_________________________________________________________________

ChessMaster moved Pawn from e7 to e5
_________________________________________________________________

                 (a) (b) (c) (d) (e) (f) (g) (h)
                _________________________________
            (8) | R | N | B | Q | K | B | N | R | (8)
                _________________________________
            (7) | P | P | P | P |( )| P | P | P | (7)
                _________________________________
            (6) |   |   |   |   |   |   |   |   | (6)
                _________________________________
            (5) |   |   |   |   |(P)|   |   |   | (5)
                _________________________________
            (4) |   |   |   | p |   |   |   |   | (4)
                _________________________________
            (3) |   |   |   |   |   |   |   |   | (3)
                _________________________________
            (2) | p | p | p |   | p | p | p | p | (2)
                _________________________________
            (1) | r | n | b | q | k | b | n | r | (1)
                _________________________________
                 (a) (b) (c) (d) (e) (f) (g) (h)
</code></pre>

</td>
</tr>
</table>

## Ending the game

The game will automatically end and a victor be declared when one side has been checkmated, or if their king has been captured.

Checkmate is the point in the game where the attacking player has the opponent's king in a situation where it can't escape being captured on the next move.

The king is "trapped" and has no safe squares to move to, and no other pieces can help.

Otherwise, you may use the `abort` command if you wish to end the game midway. 

## Features 

### Make a move

To make a move, simply enter the coordinate of the piece to be moved, followed by the coordinates it is to be moved to.

Format: `move [column][row] [column][row]`

Examples: 
- `move a2 a4`
- `move b3 g6`

ChessMaster also automatically checks if the move was valid and legal before it is executed. If the move is valid, an output as shown above in the [Gameplay](#gameplay) section will be output. Else, the following error
message will be shown:

`Oops, that move isn't valid!`


### Show available moves: `moves`

Shows the available moves for a piece on the board. This command can be used for both yours and the enemy's pieces.

Format: `moves [column][row]`

Example:  `moves e5`

Expected Output:
```
                 (a) (b) (c) (d) (e) (f) (g) (h)
                _________________________________
            (8) | R | N | B | Q | K | B | N | R | (8)
                _________________________________
            (7) | P | P | P |[ ]|   |[P]| P | P | (7)
                _________________________________
            (6) |   |   |[ ]| P |   |   |[ ]|   | (6)
                _________________________________
            (5) |   |   |   |   |{n}|   |   |   | (5)
                _________________________________
            (4) |   |   |[ ]|   |   |   |[ ]|   | (4)
                _________________________________
            (3) |   |   |   |[ ]|   |[ ]|   |   | (3)
                _________________________________
            (2) | p | p | p | p | p | p | p | p | (2)
                _________________________________
            (1) | r | n | b | q | k | b |   | r | (1)
                _________________________________
                 (a) (b) (c) (d) (e) (f) (g) (h)

_________________________________________________________________

Available coordinates for Knight at e5: 
f7 d7 f3 d3 g6 g4 c6 c4 
_________________________________________________________________
```

### Show current chess board: `show`

Shows the current state of the chess board.

Format: `show`

Expected Output:


### Show chess rules: `rules`

Obtain a quick refresher on the rules of chess

Format: `rules`

Expected Output:
```
_________________________________________________________________

Here are simple chess rules to get you started:

Piece movement:
   Pawn ("p") move forward one square but capture diagonally.
   Rooks ("r") move horizontally and vertically any number of squares.
   Knights ("n") move in an L-shape.
   Bishops ("b") move diagonally any number of squares.
   Queens ("q") move any number of squares in any direction.
   Kings ("k") move one square in any direction.

Special Rules: (Refer to specific move methods in the User Guide)
   Castling - King and rook move simultaneously to safeguard the king. (e.g. e1 g1)
   En Passant - Pawn capturing when moving two squares from starting position.
   Pawn Promotion - Promote a pawn to another piece (except king) upon reaching the back rank.

Objective:
   Game ends when one player's king is in checkmate, under attack and can't escape capture.
   The delivering player wins the game.
_________________________________________________________________
```

Further details of special rules
1. Castling

   For the move to be valid:
   - It's the king's first move. 
   - It's the rook's first move. 
   - There are no pieces between the king and the rook. 
   
   To perform castling on either sides:
    - Move your king two squares to the right/left (towards the rook).
    - The rook will jump over the king and land on the square next to the king.

2. En Passant:
   
   This move only happens when your opponent moves their pawn two squares forward from its starting position and lands next to your pawn.
   To capture en passant, you must do it on your very next move. 
   - Move your pawn diagonally forward to the square that your opponent's pawn would have occupied if it had moved only one square forward.

### Show commands: `help`

Show a list of commands and what they do

Format: `help`

Expected Output:
```
_________________________________________________________________

Seems like you need some help!
Here are the following commands to play:
Move piece - Input coordinate of piece, followed by coordinate to move to
   Format: [column][row] [column][row]
   E.g. a2 a3
Show available moves - Lists all the available moves for a piece
   Format: moves [column][row]
   E.g. moves a2
Show board - Shows the current state of the chess board
   Format: show
Obtain rules - Obtain a quick refresher on the rules of chess
   Format: rules
View pieces representation - Display a legend that explains the 
                             piece representations
   Format: legend
Abort game - Exit programme
   Format: abort
_________________________________________________________________
```

### View pieces representation: `legend` 

Display a legend that explains the piece representations

Format: `legend`

Expected Output:
```
_________________________________________________________________

Black pieces:
"R" represents a black rook.
"N" represents a black knight.
"B" represents a black bishop.
"Q" represents a black queen.
"K" represents a black king.
"P" represents a black pawn.
   
White pieces:
"r" represents a white rook.
"n" represents a white knight.
"b" represents a white bishop.
"q" represents a white queen.
"k" represents a white king.
"p" represents a white pawn.
_________________________________________________________________
```

### View history of all game moves: `history`

Displays a list of all previous moves in the current game

Format: `history`

### Step back in history: `stepback`

Displays the board state as it was a certain number of moves ago. Note that this command does not reverse any moves
in the current game.

Format: `stepback [number of moves to step back]`

### Saving and loading games

Every time a turn ends, ChessMaster will save the current state of the game. When ChessMaster is restarted, the
user will be prompted to choose whether to load the saved game or begin a new one. ChessMaster only supports a single
saved game, and starting a new one will delete the existing save file.

### Aborting game: `abort`

Exit the ChessMaster application.

Format: `abort`

Expected Output:
```
_________________________________________________________________

Exiting program... Thanks for playing!
_________________________________________________________________
```

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: You can navigate to your root folder, and find the file `data/ChessMaster.txt`. Transfer the file to your other computer,
and find the `data` folder in the ChessMaster install folder in the other computer. Copy the file into the folder. Start ChessMaster,
and type `y` when prompted to load a saved game.

**Q**: How do I play with another person?

**A**: Currently, ChessMaster does not support multiplayer. 

**Q**: Can we play timed games?

**A**: ChessMaster does not come with an internal timer. However, you are able to use your own chess timer or stopwatch 
to simulate timed games.

## Command Summary

| Action        | Format                             |
|---------------|------------------------------------|
| Move          | `move [column][row] [column][row]` |
| Show moves    | `moves [column][row]`              |
| Show board    | `show`                             |
| Rules         | `rules`                            |
| Help          | `help`                             |
| Pieces legend | `legend`                           |
| History       | `history`                          |
| Step back     | `stepback`                         |
| Abort         | `abort`                            |

