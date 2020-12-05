package chess.pieces;

import chess.*;
import java.util.*;

public class King extends Piece {
    public static final String NOTATION = "K";
    public static final int VALUE = 200;

    public King(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        return getPossibleMoves(true);
    }

    public Set<Move> getPossibleMoves(boolean considerCheck) {
        Set<Move> possibleMoves = getStraightMoves(1, considerCheck);
        possibleMoves.addAll(getDiagonalMoves(1, considerCheck));
        if (!alreadyMoved) {
            // For castling
            // Note from Arnav: can't castle while in check, can't castle into check, can't pass through a piece that is
            //                  under attack (can't move through check)
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

        // Response from Arnav:
        // We want to save both the starting and ending squares so that it's easier to execute the move at playing/evaluating time.
        // We can simply take the piece from the start square and move it to the end square instead of having to guess where the piece
        // came from if we were only given an end square

        return possibleMoves;
    }

    // Note from Arnav: Might want to make a method and/or field to check/store if king or a square is in check as it seems to be used multiple times.
    // Should also take into account that check must be resolved before any other moves can be made from any piece

    public void draw() {
        return;
    }
}
