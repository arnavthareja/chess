package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A bishop. Bishops can move any number of squares diagonally, but they cannot
 * move in any other direction.
 */
public final class Bishop extends Piece {
    /**
     * A bishop's notation in algebraic notation.
     */
    public static final String NOTATION = "B";

    /**
     * A bishop's material value.
     */
    public static final int VALUE = 3;

    /**
     * Constructs a new bishop of the given color at the given position.
     *
     * @param position  the square at which to initialize the bishop
     * @param color     the color of the bishop
     */
    public Bishop(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    /**
     * Returns a set containing all the moves this bishop can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this bishop can make
     */
    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        return getDiagonalMoves(onlyLegalMoves);
    }
}