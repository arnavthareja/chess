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
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
