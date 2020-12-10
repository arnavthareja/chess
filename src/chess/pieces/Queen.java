package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A queen. Queens can move any number of squares diagonally or straight along
 * ranks and files.
 */
public final class Queen extends Piece {
    /**
     * A queen's notation in algebraic notation.
     */
    public static final String NOTATION = "Q";

    /**
     * A queen's material value.
     */
    public static final int VALUE = 9;

    /**
     * Constructs a new queen of the given color at the given position.
     *
     * @param position  the square at which to initialize the queen
     * @param color     the color of the queen
     */
    public Queen(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    /**
     * Returns a set containing all the moves this queen can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this queen can make
     */
    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getStraightMoves(onlyLegalMoves);
        possibleMoves.addAll(getDiagonalMoves(onlyLegalMoves));
        return possibleMoves;
    }
}
