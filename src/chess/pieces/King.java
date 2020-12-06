package chess.pieces;

import chess.*;
import java.util.*;

public class King extends Piece {
    public static final String NOTATION = "K";
    public static final int VALUE = 200;

    public King(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves() {
        return getPossibleMoves(true);
    }

    public Set<Move> getPossibleMoves(boolean considerCheck) {
        Set<Move> possibleMoves = getStraightMoves(1, considerCheck);
        possibleMoves.addAll(getDiagonalMoves(1, considerCheck));
        if (!alreadyMoved && (!considerCheck || !position.getBoard().inCheck(color))) {
            addIfNotInCheck(getKingsideCastleMove(), possibleMoves, considerCheck, true);
            addIfNotInCheck(getQueensideCastleMove(), possibleMoves, considerCheck, true);
        }
        return possibleMoves;
    }

    private Move getKingsideCastleMove() {
        Move castleMove = null;
        if (isAllowed(position, 1, 1)) {
            Board b = position.getBoard();
            int row = position.getRow(), col = position.getCol();
            castleMove = new Move(position, b.squareAt(row, col + 2), b.squareAt(row, 7),
                                  b.squareAt(row, col + 1));
        }
        return castleMove;
    }

    private Move getQueensideCastleMove() {
        Move castleMove = null;
        if (isAllowed(position, -1, 1)) {
            Board b = position.getBoard();
            int row = position.getRow(), col = position.getCol();
            castleMove = new Move(position, b.squareAt(row, col - 2), b.squareAt(row, 0),
                    b.squareAt(row, col - 1));
        }
        return castleMove;
    }

    private void addIfNotInCheck(Move move, Set<Move> possibleMoves, boolean considerCheck, boolean considerNull) {
        if (!considerNull || move != null) {
            addIfNotInCheck(move, possibleMoves, considerCheck);
        }
    }

    private boolean isAllowed(Square currentPosition, int dx, int iteration) {
        try {
            Board b = position.getBoard();
            Square nextPos = b.squareAt(currentPosition.getRow(), currentPosition.getCol() + dx);
            Piece p = nextPos.getPiece();
            boolean throughCheck = false;
            // Only check if moving through check on the first iteration if next square is empty
            if (iteration == 1 && nextPos.isEmpty()) {
                Move tempMove = new Move(currentPosition, nextPos);
                b.doMove(tempMove);
                throughCheck = b.inCheck(color);
                b.undoLastMove();
            }
            // A valid position is either empty in files b-g or occupied by a rook in files a or h that hasn't moved
            boolean nextPosValid = (nextPos.isEmpty() && nextPos.getCol() < Board.NUM_ROWS - 1 &&
                                    nextPos.getCol() > 0) || (p instanceof Rook && !p.alreadyMoved);
            return !throughCheck && nextPosValid && isAllowed(nextPos, dx, iteration + 1);
        } catch (IllegalArgumentException e) {}
        return true;
    }

    // Note from Arnav: Might want to make a method and/or field to check/store if king or a square is in check as it seems to be used multiple times.
    // Should also take into account that check must be resolved before any other moves can be made from any piece

    public void draw() {
        return;
    }
}
