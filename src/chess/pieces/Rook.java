package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A rook. Rooks can move any number of squares straight along ranks and files,
 * but they cannot move in any other direction.
 */
public final class Rook extends Piece {
    /**
     * A rook's notation in algebraic notation.
     */
    public static final String NOTATION = "R";

    /**
     * A rook's material value.
     */
    public static final int VALUE = 5;

    /**
     * Constructs a new rook of the given color at the given position.
     *
     * @param position  the square at which to initialize the rook
     * @param color     the color of the rook
     */
    public Rook(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    /**
     * Returns a set containing all the moves this rook can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this rook can make
     */
    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        return getStraightMoves(onlyLegalMoves);
    }
}
