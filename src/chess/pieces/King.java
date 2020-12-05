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
        Set<Move> possibleMoves = getStraightMoves(1);
        possibleMoves.addAll(getDiagonalMoves(1));
        if (!alreadyMoved) {
            getCastleMove();
            // For castling
            // Note from Arnav: can't castle while in check, can't castle into check, can't pass through a piece that is
            //                  under attack (can't move through check)
        }
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

    private Set<CastleMove> getCastleMove() {
        Set<CastleMove> possibleMoves = new HashSet<>();
        addIfNotNull(tryRight(), possibleMoves);
        addIfNotNull(tryLeft(), possibleMoves);
        return possibleMoves;
    }

    private CastleMove tryRight() {
        if (isAllowed(position, 1)) {
            // return the CastleMove
        }
        return null;
    }

    private CastleMove tryLeft() {
        if (isAllowed(position, -1)) {
            // return the CastleMove
        }
        return null;
    }

    private void addIfNotNull(CastleMove tempMove, Set<CastleMove> possibleMoves) {
        if (tempMove != null) {
            possibleMoves.add(tempMove);
        }
    }

    private boolean isAllowed(Square currentPosition, int dx) {
        try {
            if (!currentPosition.getBoard().inCheck(color)) {
                Square nextPosition = currentPosition.getBoard().squareAt(currentPosition.getRow(),
                                                                          currentPosition.getCol() + dx);
                if (nextPosition.getCol() == Board.NUM_ROWS - 1 && !nextPosition.getPiece().alreadyMoved) {
                    return true;
                } else if (!nextPosition.isEmpty()) {
                    return false;
                }
                return isAllowed(nextPosition, dx);
            }
        } catch (IllegalArgumentException e) {}
        return false;
    }

    // Note from Arnav: Might want to make a method and/or field to check/store if king or a square is in check as it seems to be used multiple times.
    // Should also take into account that check must be resolved before any other moves can be made from any piece

    public void draw() {
        return;
    }
}
