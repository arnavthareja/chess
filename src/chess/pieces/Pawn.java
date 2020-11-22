package chess.pieces;

import chess.*;
import java.util.*;

public class Pawn extends Piece {
    public static final String NOTATION = "";
    public static final double VALUE = 1;

    public Pawn(Board board, Square position, Color color) {
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
