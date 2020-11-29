package chess.pieces;

import chess.*;
import java.util.*;

public class Pawn extends Piece {
    public static final String NOTATION = "";
    public static final int VALUE = 1;

    public Pawn(Board board, Square position, Color color) {
        super(board, position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        Set<Move> possibleMoves = new HashSet<>();
        if (!alreadyMoved) {
            // For first move
        }
        // Check if piece of opposite color is diagonal. If so, can move there. If not, can't move there
        // Check if frontal position is blocked by anything. If so, can't move. If not, can move there
        // Can't go off board
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
