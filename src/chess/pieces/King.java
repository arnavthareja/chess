package chess.pieces;

import chess.*;
import java.util.*;

public class King extends Piece {
    public static final String NOTATION = "K";
    public static final double VALUE = Double.POSITIVE_INFINITY;

    public King(Board board, Square position, Color color) {
        super(board, position, color, VALUE, NOTATION);
    }

    public Set<Square> getPossibleMoves() {
        Set<Square> possibleMoves = new HashSet<>();
        if (!hasAlreadyMoved()) {
            // For castling
        }
        // Check to make sure piece doesn't go off board. If piece is in path and is other color, can
        // move there. If same color, can't move there. If possibleMoves of the other team's
        // pieces include the possibleMove, it's no longer possible to move there. Also, for each
        // of the other pieces on our team, if they move and possibleMoves of the other team's
        // pieces include the location of the king, it's no longer a possible move
        
        // If no possibleMoves and the king's location is a possibleMove for a piece from other team, 
        // check to see if moving a piece can block the path of the other team without putting
        // the king in check. If so, do that. If not, end game
        
        // Question for Arnav: Why do we want to mark the beginning and final position in possibleMoves?
        // Shouldn't we just need final position?
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
