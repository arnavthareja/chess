package chess.pieces;

import chess.*;
import java.util.*;

public class Knight extends Piece {
    public static final String NOTATION = "N";
    public static final double VALUE = 3;

    public Knight(Board board, Square position, Color color) {
        super(board, position, color, VALUE, NOTATION);
    }

    public Set<Square> getPossibleMoves() {
        Set<Square> possibleMoves = new HashSet<>();
        // Can't go off board. If by moving 2 in any direction followed by 1 in a perpendicular direction,
        // if piece in that spot is same color, can't go. If not same color, can go. If no piece, go duh
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
