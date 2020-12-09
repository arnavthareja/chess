# Chess
A console-based chess game in Java with minimax bots to play against.

## How To Play
Navigate to the parent `chess` directory (parent of `src`) then run  
`javac -d bin -sourcepath src src/chess/Chess.java && java -cp bin chess.Chess`  
Empty squares will be displayed in white, squares with black pieces will be displayed in black, and squares with white pieces will be displayed in blue.  
To move a piece, enter the starting square when prompted, then enter the ending square when prompted.  
If the move is not valid, you will have to enter a valid move.

## Known Issues
* The minimax player is not very good and makes weird decisions sometimes
