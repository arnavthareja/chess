package chess.pieces;

import chess.*;

import java.util.*;

public class Bishop extends Piece {
    public static final String NOTATION = "B";
    public static final int VALUE = 3;

    public Bishop(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves(boolean considerCheck) {
        return getDiagonalMoves(considerCheck);
    }
}