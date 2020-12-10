package chess.pieces;

import chess.*;

import java.util.*;

public class Pawn extends Piece {
    public static final String NOTATION = "";
    public static final int VALUE = 1;

    public Pawn(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getStraightMoves(-color.getMultiplier(), onlyLegalMoves);
        possibleMoves.addAll(getCaptureMove(1, -color.getMultiplier(), onlyLegalMoves));
        possibleMoves.addAll(getCaptureMove(-1, -color.getMultiplier(), onlyLegalMoves));
        return possibleMoves;
    }

    protected Set<Move> getStraightMoves(int dy, boolean onlyLegalMoves) {
        Set<Move> possibleMoves = new HashSet<>();
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol());
            if (finalPosition.isEmpty()) {
                addIfLegal(new Move(position, finalPosition), possibleMoves, onlyLegalMoves);
                finalPosition = finalPosition.getBoard().squareAt(finalPosition.getRow() + dy,
                                                                  finalPosition.getCol());
                if (!alreadyMoved && finalPosition.isEmpty()) {
                    addIfLegal(new Move(position, finalPosition), possibleMoves,
                                    onlyLegalMoves);
                }
            }
        } catch (IllegalArgumentException e) {}
        return possibleMoves;
    }

    private Set<Move> getCaptureMove(int dx, int dy, boolean onlyLegalMoves) {
        Set<Move> returnMove = new HashSet<>();
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol() + dx);
            if (!finalPosition.isEmpty() && finalPosition.getPiece().color != color) {
                addIfLegal(new Move(position, finalPosition), returnMove, onlyLegalMoves);
            }
        } catch (IllegalArgumentException e) {}
        return returnMove;
    }
}
