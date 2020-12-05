package chess.pieces;

import chess.*;
import java.util.*;

public class Knight extends Piece {
    public static final String NOTATION = "N";
    public static final int VALUE = 3;

    public Knight(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        Set<Move> possibleMoves = getPossibleMoves(position, 2, 1, 1);
        possibleMoves.addAll(getPossibleMoves(position, 2, -1, 1));
        possibleMoves.addAll(getPossibleMoves(position, -2, 1, 1));
        possibleMoves.addAll(getPossibleMoves(position, -2, -1, 1));
        possibleMoves.addAll(getPossibleMoves(position, 1, 2, 1));
        possibleMoves.addAll(getPossibleMoves(position, 1, -2, 1));
        possibleMoves.addAll(getPossibleMoves(position, -1, 2, 1));
        possibleMoves.addAll(getPossibleMoves(position, -1, -2, 1));
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
