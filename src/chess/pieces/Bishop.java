package chess.pieces;

import chess.*;
import java.util.*;

public class Bishop extends Piece {
    public static final String NOTATION = "B";
    public static final double VALUE = 3;

    public Bishop(Board board, Square position, Color color) {
        super(board, position, color, VALUE, NOTATION);
    }

    public Set<Square> getPossibleMoves() {
        Set<Square> possibleMoves = new HashSet<>();
        // Check each diagonal possibility. Make sure it doesn't go off board. If piece is in the way,
        // if opposite color, can go there. If not, can't go there
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}