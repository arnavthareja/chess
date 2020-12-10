package chess.pieces;

import chess.*;

import java.util.*;

public class Knight extends Piece {
    public static final String NOTATION = "N";
    public static final int VALUE = 3;

    public Knight(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

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
