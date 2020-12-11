# Chess

A console-based chess game in Java with minimax bots to play against.

## How To Play

Navigate to the parent `chess` directory (parent of `src`) then run
if in Ed: `./chess.sh`
if anywhere else: `javac -d bin -sourcepath src src/chess/Chess.java && java -cp bin chess.Chess`  

Upon startup, you will see a menu with different types of possible players. You will type the name or number of the player you would like to select, then press enter. You will then select the second player in the same fashion.

Once you finish selecting your players, a gameboard will appear. Empty squares will be displayed in white, squares with black pieces will be displayed in black, and squares with white pieces will be displayed in blue. Each square displays the name of the piece, followed by the name of the square.
    i.e. "Qd1" means that there is a queen at square d1.

    Note: Pawns have no notation. If the displayed name of a square is the name of the square but in either blue or black, you know there is a pawn there.

When you are prompted to move a piece, type the starting square and press enter, then type the ending square and press enter.
    Note: You must type the SQUARE, not what is displayed on the board. For example, if you would like to move your Queen from d1 to d4, the board will display Qd1, but you simply type d1, enter, d4, enter.

If the move that you enter is not valid, you will be prompted to try again until you enter a valid move.

Once you complete your move, your opponent will move. Please be patient because this may take some time.
    
    Extreme: 1-3 minutes per move

Our game follows the same rules as any chess game. Some features include castling, pawn promotion, and en passant. The game ends in either a stalemate (essentially a tie) or a checkmate.

## Known Issues

* The minimax player is not very good and makes weird decisions sometimes
* Bots can only promote pawns to a queen