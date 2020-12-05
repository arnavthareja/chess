package chess.pieces;

import chess.*;
import java.util.*;

public class Pawn extends Piece {
    public static final String NOTATION = "";
    public static final int VALUE = 1;

    public Pawn(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        Set<Move> possibleMoves = getStraightMoves();
        possibleMoves.add(getCaptureMove(1, 1));
        possibleMoves.add(getCaptureMove(-1, 1));
        return possibleMoves;
    }

    protected Set<Move> getStraightMoves() {
        Set<Move> possibleMoves = new HashSet<>();
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + 1,
                                                                position.getCol());
            if (finalPosition.isEmpty()) {
                possibleMoves.add(new Move(position, finalPosition));
                finalPosition = finalPosition.getBoard().squareAt(finalPosition.getRow() + 1,
                                                                  finalPosition.getCol());
                if (!alreadyMoved && finalPosition.isEmpty()) {
                    possibleMoves.add(new Move(position, finalPosition));
                }
            }
        } catch (IllegalArgumentException e) {}
        return possibleMoves;
    }

    private Move getCaptureMove(int dx, int dy) {
        Move returnMove = null;
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol() + dx);
            if (!finalPosition.isEmpty() && finalPosition.getPiece().color != color) {
                returnMove = new Move(position, finalPosition);
            }
        } catch (IllegalArgumentException e) {}
        return returnMove;
    }

    public void draw() {
        return;
    }
}
