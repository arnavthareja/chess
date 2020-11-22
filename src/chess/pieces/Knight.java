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
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
