package chess.pieces;

import chess.*;
import java.util.*;

public class Bishop extends Piece {
    public static final String NOTATION = "B";
    public static final int VALUE = 3;

    public Bishop(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        // Check each diagonal possibility. Make sure it doesn't go off board. If piece is in the way,
        // if opposite color, can go there. If not, can't go there
        return getDiagonalMoves();
    }

    public void draw() {
        return;
    }
}