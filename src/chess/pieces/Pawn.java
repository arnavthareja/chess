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
        Set<Move> possibleMoves = getStraightMoves(-color.getMultiplier());
        addIfNotNull(getCaptureMove(1, -color.getMultiplier()), possibleMoves);
        addIfNotNull(getCaptureMove(-1, -color.getMultiplier()), possibleMoves);
        return possibleMoves;
    }

    protected Set<Move> getStraightMoves(int dy) {
        Set<Move> possibleMoves = new HashSet<>();
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol());
            if (finalPosition.isEmpty()) {
                possibleMoves.add(new Move(position, finalPosition));
                finalPosition = finalPosition.getBoard().squareAt(finalPosition.getRow() + dy,
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

    private void addIfNotNull(Move tempMove, Set<Move> possibleMoves) {
        if (tempMove != null) {
            possibleMoves.add(tempMove);
        }
    }

    // TODO: Implement pawn promotion
    // Maybe return the promoted piece type instead
    public void promote() {

    }

    public void draw() {
        return;
    }
}
