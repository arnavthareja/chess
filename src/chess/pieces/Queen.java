package chess.pieces;

import chess.*;
import java.util.*;

public class Queen extends Piece {
    public static final String NOTATION = "Q";
    public static final int VALUE = 9;

    public Queen(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        possibleMoves = new HashSet<>();
        getStraightMoves();
        getDiagonalMoves();
        // Check each direction. Don't go off board. If piece in path is of the same color, can't go there.
        // If not same color, can go there
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
