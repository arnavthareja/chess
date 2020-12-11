# Video

https://youtu.be/oVLsRk9sXp4

# Chess

A console-based chess game in Java with minimax bots to play against.

## Running the Game

### In the Ed workspace

In Ed, you can simply open the terminal and run `./chess.sh` to start the game.

### Running locally

1. Navigate to the `chess` directory if you are not already in it:  
   `cd chess`
2. Compile the game to the `bin` folder:  
   `javac -d bin -sourcepath src src/chess/Chess.java`
3. Run the game:  
   `java -cp bin chess.Chess`
   
## Playing the Game

Upon startup, you will see a menu with different types of possible players. To select a player, you
can type the name or number of the player you would like to select, then press enter. You can then
select the second player in the same fashion.

Once you finish selecting your players, a chessboard will appear. Empty squares will be displayed in
white, squares with black pieces will be displayed in black, and squares with white pieces will be
displayed in blue. Each square displays the letter abbreviation of the piece followed by the name of
the square in algebraic notation (i.e. `Qd1` means that there is a queen at square d1).

    Note: Pawns have no notation. If a square is displayed in blue or black and does not start with
    the letter abbreviation of a piece, a pawn is located on that square.

To make a move when prompted, type the algebraic notation of the starting square and press enter,
then type the algebraic notation of the ending square and press enter.

    Note: You must type the algebraic notation of the SQUARE, not what is displayed on the board.
    For example, if you would like to move your Queen from d1 to d4, the board will display Qd1,
    but you should type d1, enter, d4, enter.

If the move that you enter is not valid, you will be prompted to try again until you enter a valid move.

Once you complete your move, your opponent will move. Please be patient because this may take some
time. This is especially true if you are playing against an `extreme` difficulty computer, which may
take up to 1-3 minutes to select a move.

Our game follows the same rules as any chess game. Our game can handle castling and pawn promotion,
but en passant has not been implemented yet. The game ends in either a stalemate or a checkmate.

## Future Improvements

* Improve minimax algorithm runtime through better memoization
* Allow bots to promote pawns to pieces other than a queen
* Implement en passant



