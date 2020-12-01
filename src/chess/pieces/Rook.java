package chess.pieces;

import chess.*;
import java.util.*;

public class Rook extends Piece {
    public static final String NOTATION = "R";
    public static final int VALUE = 5;

    public Rook(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        getStraightMoves();
        if (!alreadyMoved) {
            // For castling
        }
        // Check in each direction where closest piece is. Make sure piece doesn't go off board. If piece
        // in path is other color, can move there. If same color, can't move there
        return possibleMoves;
    }

    public void draw() {
        return;
    }
}
