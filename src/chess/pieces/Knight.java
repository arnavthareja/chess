package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A knight. Knights can only move in special moves that are made of 2 squares
 * in one direction and one square in a direction perpendicular to the first.
 * Knights have the unique ability to move through other pieces.
 */
public final class Knight extends Piece {
    /**
     * A knight's notation in algebraic notation.
     */
    public static final String NOTATION = "N";

    /**
     * A knight's material value.
     */
    public static final int VALUE = 3;

    /**
     * Constructs a new knight of the given color at the given position.
     *
     * @param position  the square at which to initialize the knight
     * @param color     the color of the knight
     */
    public Knight(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    /**
     * Returns a set containing all the moves this knight can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this knight can make
     */
    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getPossibleMoves(2, 1, 1, onlyLegalMoves);
        possibleMoves.addAll(getPossibleMoves(2, -1, 1, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-2, 1, 1, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-2, -1, 1, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(1, 2, 1, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(1, -2, 1, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-1, 2, 1, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-1, -2, 1, onlyLegalMoves));
        return possibleMoves;
    }
}
