package chess.pieces;

import chess.*;
import java.util.*;

public class Rook extends Piece {
    public static final String NOTATION = "R";
    public static final double VALUE = 5;

    public Rook(Board board, Square position, Color color) {
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
