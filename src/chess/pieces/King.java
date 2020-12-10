package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A king. Kings can move one square in any direction, or can castle if they
 * and the rook they are castling with have not moved before.
 */
public class King extends Piece {
    /**
     * A king's notation in algebraic notation.
     */
    public static final String NOTATION = "K";

    /**
     * A king's material value.
     */
    public static final int VALUE = 200;

    /**
     * Constructs a new king of the given color at the given position.
     *
     * @param position  the square at which to initialize the king
     * @param color     the color of the king
     */
    public King(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    /**
     * Returns a set containing all the moves this king can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this king can make
     */
    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getStraightMoves(1, onlyLegalMoves);
        possibleMoves.addAll(getDiagonalMoves(1, onlyLegalMoves));
        if (!alreadyMoved && (!onlyLegalMoves || !position.getBoard().inCheck(color))) {
            addIfLegal(getKingsideCastleMove(), possibleMoves, onlyLegalMoves);
            addIfLegal(getQueensideCastleMove(), possibleMoves, onlyLegalMoves);
        }
        return possibleMoves;
    }

    /**
     * Returns the kingside castle move if it is allowed, null otherwise.
     *
     * @return the kingside castle move if it is allowed, null otherwise
     */
    private Move getKingsideCastleMove() {
        Move castleMove = null;
        if (isCastleAllowed(position, 1, 0)) {
            Board b = position.getBoard();
            int row = position.getRow(), col = position.getCol();
            castleMove = new Move(position, b.squareAt(row, col + 2), b.squareAt(row, 7),
                                  b.squareAt(row, col + 1));
        }
        return castleMove;
    }

    /**
     * Returns the queenside castle move if it is allowed, null otherwise.
     *
     * @return the queenside castle move if it is allowed, null otherwise
     */
    private Move getQueensideCastleMove() {
        Move castleMove = null;
        if (isCastleAllowed(position, -1, 0)) {
            Board b = position.getBoard();
            int row = position.getRow(), col = position.getCol();
            castleMove = new Move(position, b.squareAt(row, col - 2), b.squareAt(row, 0),
                    b.squareAt(row, col - 1));
        }
        return castleMove;
    }

    /**
     * Adds the given move to the given set if the given move is legal and not null.
     *
     * @param move            the move to add to the set if it is legal
     * @param possibleMoves   the set to add the move to if it is legal
     * @param onlyLegalMoves  whether legality of the move should be considered
     */
    @Override
    protected void addIfLegal(Move move, Set<Move> possibleMoves, boolean onlyLegalMoves) {
        if (move != null) {
            super.addIfLegal(move, possibleMoves, onlyLegalMoves);
        }
    }

    /**
     * Returns true if the given square meets the conditions of castling, false
     * otherwise. Castling requires the king to not be in check, the king to
     * not move through check, and the king to not end in check. Castling also
     * requires all the squares between the king and rook to be empty and for
     * both the king and the rook to not have moved before. This method checks
     * if the king moves through check, if all the squares between the king and
     * rook are empty, and if the rook hasn't moved before.
     *
     * @param  startPosition  the square to start checking legality at
     * @param  dx             the number of squares to move horizontally
     * @param  iteration      the number of squares already checked
     * @return                true if the given square meets the conditions
     *                        of castling, false otherwise
     */
    private boolean isCastleAllowed(Square startPosition, int dx, int iteration) {
        try {
            Board board = position.getBoard();
            Square nextPos = board.squareAt(startPosition.getRow(), startPosition.getCol() + dx);
            boolean throughCheck = false;
            // Evaluate if moving through check
            if (iteration == 0 && nextPos.isEmpty()) {
                Move tempMove = new Move(startPosition, nextPos);
                board.doMove(tempMove);
                throughCheck = board.inCheck(color);
                board.undoLastMove();
            }
            // A valid square is either empty in files b-g or
            // occupied by a rook in files a or h that hasn't moved
            Piece p = nextPos.getPiece();
            boolean nextPosValid = (nextPos.isEmpty() && nextPos.getCol() < Board.NUM_ROWS - 1 &&
                    nextPos.getCol() > 0) || (p instanceof Rook && !p.alreadyMoved);
            return !throughCheck && nextPosValid && isCastleAllowed(nextPos, dx, iteration + 1);
        } catch (IllegalArgumentException e) {}
        return true;
    }
}
