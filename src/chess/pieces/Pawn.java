package chess.pieces;

import chess.*;
import java.util.*;

public class Pawn extends Piece {
    public static final String NOTATION = "";
    public static final int VALUE = 1;

    public Pawn(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Pawn(Square position, Color color, boolean alreadyMoved) {
        super(position, color, VALUE, NOTATION, alreadyMoved);
    }

    public Set<Move> getPossibleMoves() {
        return getPossibleMoves(true);
    }

    public Set<Move> getPossibleMoves(boolean considerCheck) {
        Set<Move> possibleMoves = getStraightMoves(-color.getMultiplier(), considerCheck);
        possibleMoves.addAll(getCaptureMove(1, -color.getMultiplier(), considerCheck));
        possibleMoves.addAll(getCaptureMove(-1, -color.getMultiplier(), considerCheck));
        return possibleMoves;
    }

    protected Set<Move> getStraightMoves(int dy, boolean considerCheck) {
        Set<Move> possibleMoves = new HashSet<>();
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol());
            if (finalPosition.isEmpty()) {
                addIfNotInCheck(determineMove(finalPosition), possibleMoves, considerCheck);
                finalPosition = finalPosition.getBoard().squareAt(finalPosition.getRow() + dy,
                                                                  finalPosition.getCol());
                if (!alreadyMoved && finalPosition.isEmpty()) {
                    addIfNotInCheck(new Move(position, finalPosition), possibleMoves, considerCheck);
                }
            }
        } catch (IllegalArgumentException e) {}
        return possibleMoves;
    }

    private Set<Move> getCaptureMove(int dx, int dy, boolean considerCheck) {
        Set<Move> returnMove = new HashSet<>();
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol() + dx);
            if (!finalPosition.isEmpty() && finalPosition.getPiece().color != color) {
                addIfNotInCheck(determineMove(finalPosition), returnMove, considerCheck);
            }
        } catch (IllegalArgumentException e) {}
        return returnMove;
    }

    // TODO: Implement pawn promotion
    // Maybe return the promoted piece type instead
    private Move determineMove(Square finalPosition) {
        if (isPromotion(finalPosition)) {
            return new Move(position, finalPosition, true);
        }
        return new Move(position, finalPosition);
    }
    
    private boolean isPromotion(Square finalPosition) {
        return finalPosition.getRow() == 7 || finalPosition.getRow() == 0;
    }

    public void draw() {
        return;
    }
}
