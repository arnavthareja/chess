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
        // Check if piece of opposite color is diagonal. If so, can move there. If not, can't move there
        // Check if frontal position is blocked by anything. If so, can't move. If not, can move there
        // Can't go off board
        Set<Move> possibleMoves = getStraightMoves();
        possibleMoves.add(getDiagonalMoves(1, 1));
        possibleMoves.add(getDiagonalMoves(-1, 1));
        // possibleMoves.addAll(getDiagonalMoves());
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

    private Move getDiagonalMoves(int dx, int dy) {
        Move returnMove = null;
        try {
            Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
                                                                position.getCol() + dx);
            if (!finalPosition.isEmpty() && !finalPosition.getPiece().getColor().equals(color)) {
                returnMove = new Move(position, finalPosition);
            }
        } catch (IllegalArgumentException e) {}
        return returnMove;
    }

    // private Set<Move> getDiagonalMoves(int dx, int dy) {
    //     Set<Move> possibleMoves = new HashSet<>();
    //     try {
    //         Square finalPosition = position.getBoard().squareAt(position.getRow() + dy,
    //                                                             position.getCol() + dx);
    //         if (!finalPosition.isEmpty() && !finalPosition.getPiece().getColor().equals(color)) {
    //             possibleMoves.add(new Move(position, finalPosition));
    //         }
    //     } catch (IllegalArgumentException e) {}
    //     return possibleMoves;
    // }

    public void draw() {
        return;
    }
}
