package chess.pieces;

import chess.*;
import java.util.*;

public class King extends Piece {
    public static final String NOTATION = "K";
    public static final double VALUE = Double.POSITIVE_INFINITY;

    public King(Board board, Square position, Color color) {
        super(board, position, color, VALUE, NOTATION);
    }

    public Set<Square> getPossibleMoves() {
        Set<Square> possibleMoves = new HashSet<>();
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
