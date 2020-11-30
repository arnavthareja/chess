package chess.pieces;

import chess.*;
import java.util.*;

public class Bishop extends Piece {
    public static final String NOTATION = "B";
    public static final int VALUE = 3;
    public static Set<Move> possibleMoves;

    public Bishop(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        possibleMoves = new HashSet<>();
        getPossibleMoves(position, 1, 1); 
        getPossibleMoves(position, 1, -1);
        getPossibleMoves(position, -1, -1);
        getPossibleMoves(position, -1, 1);       
        // Check each diagonal possibility. Make sure it doesn't go off board. If piece is in the way,
        // if opposite color, can go there. If not, can't go there
        return possibleMoves;
    }

    private void getPossibleMoves(Square currentPosition, int num1, int num2) {
        try {
            Square finalPosition = new Square(currentPosition.getRow() + num1, currentPosition.getCol() + num2, currentPosition.getBoard());
            if (finalPosition.isEmpty()) {
                possibleMoves.add(new Move(currentPosition, finalPosition));
                getPossibleMoves(finalPosition, num1, num2);
            } else if (!finalPosition.getPiece().getColor().equals(getColor())) {
                possibleMoves.add(new Move(currentPosition, finalPosition));
            }
        } catch (IllegalArgumentException()) {

        }
    }

    public void draw() {
        return;
    }
}