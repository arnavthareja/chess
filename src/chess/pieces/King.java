package chess.pieces;

import chess.*;

import java.util.*;

public class King extends Piece {
    public static final String NOTATION = "K";
    public static final int VALUE = 200;

    public King(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    public Set<Move> getPossibleMoves(boolean considerCheck) {
        Set<Move> possibleMoves = getStraightMoves(1, considerCheck);
        possibleMoves.addAll(getDiagonalMoves(1, considerCheck));
        if (!alreadyMoved && (!considerCheck || !position.getBoard().inCheck(color))) {
            addIfLegal(getKingsideCastleMove(), possibleMoves, considerCheck);
            addIfLegal(getQueensideCastleMove(), possibleMoves, considerCheck);
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

    @Override
    protected void addIfLegal(Move move, Set<Move> possibleMoves, boolean considerCheck) {
        if (move != null) {
            super.addIfLegal(move, possibleMoves, considerCheck);
        }
    }

    private boolean isAllowed(Square currentPosition, int dx, int iteration) {
        try {
            Board b = position.getBoard();
            Square nextPos = b.squareAt(currentPosition.getRow(), currentPosition.getCol() + dx);
            Piece p = nextPos.getPiece();
            boolean throughCheck = false;
            // Evaluate if moving through check
            if (iteration == 1 && nextPos.isEmpty()) {
                Move tempMove = new Move(currentPosition, nextPos);
                b.doMove(tempMove);
                throughCheck = b.inCheck(color);
                b.undoLastMove();
            }
            // A valid position is either empty in files b-g or
            // occupied by a rook in files a or h that hasn't moved
            boolean nextPosValid = (nextPos.isEmpty() && nextPos.getCol() < Board.NUM_ROWS - 1 &&
                    nextPos.getCol() > 0) || (p instanceof Rook && !p.alreadyMoved);
            return !throughCheck && nextPosValid && isAllowed(nextPos, dx, iteration + 1);
        } catch (IllegalArgumentException e) {}
        return true;
    }
}
