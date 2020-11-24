package chess.pieces;

import chess.*;
import java.util.*;

public class Queen extends Piece {
    public static final String NOTATION = "Q";
    public static final double VALUE = 9;

    public Queen(Board board, Square position, Color color) {
        super(board, position, color, VALUE, NOTATION);
    }

    public Set<Square> getPossibleMoves() {
        Set<Square> possibleMoves = new HashSet<>();
        // Check each direction. Don't go off board. If piece in path is of the same color, can't go there.
        // If not same color, can go there
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
