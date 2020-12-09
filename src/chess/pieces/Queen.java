package chess.pieces;

import chess.*;

import java.util.*;

public class Queen extends Piece {
    public static final String NOTATION = "Q";
    public static final int VALUE = 9;

    public Queen(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves(boolean considerCheck) {
        Set<Move> possibleMoves = getStraightMoves(considerCheck);
        possibleMoves.addAll(getDiagonalMoves(considerCheck));
        return possibleMoves;
    }
}
