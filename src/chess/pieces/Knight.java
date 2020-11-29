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
        Set<Move> possibleMoves = new HashSet<>();
        // Can't go off board. If by moving 2 in any direction followed by 1 in a perpendicular direction,
        // if piece in that spot is same color, can't go. If not same color, can go. If no piece, go duh
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
