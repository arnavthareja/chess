package chess.pieces;

import chess.*;

import java.util.*;

public class Pawn extends Piece {
    public static final String NOTATION = "";
    public static final int VALUE = 1;

    public Pawn(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
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
                addIfNotInCheck(new Move(position, finalPosition), possibleMoves, considerCheck);
                finalPosition = finalPosition.getBoard().squareAt(finalPosition.getRow() + dy,
                                                                  finalPosition.getCol());
                if (!alreadyMoved && finalPosition.isEmpty()) {
                    addIfNotInCheck(new Move(position, finalPosition), possibleMoves,
                                    considerCheck);
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
                addIfNotInCheck(new Move(position, finalPosition), returnMove, considerCheck);
            }
        } catch (IllegalArgumentException e) {}
        return returnMove;
    }
}
