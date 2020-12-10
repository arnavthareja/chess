package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A pawn. Pawns can only move on square forward, or two squares forward if
 * they haven't moved before. Pawns cannot capture the piece in front of them,
 * they can only capture diagonally in front.
 */
public final class Pawn extends Piece {
    /**
     * A pawn's notation in algebraic notation.
     */
    public static final String NOTATION = "";

    /**
     * A pawn's material value.
     */
    public static final int VALUE = 1;

    /**
     * Constructs a new pawn of the given color at the given position.
     *
     * @param position  the square at which to initialize the pawn
     * @param color     the color of the pawn
     */
    public Pawn(Square position, Color color) {
        super(position, color, VALUE, NOTATION);
    }

    /**
     * Returns a set containing all the moves this pawn can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this pawn can make
     */
    public Set<Move> getPossibleMoves(boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getStraightMoves(-color.getMultiplier(), onlyLegalMoves);
        possibleMoves.addAll(getCaptureMove(1, -color.getMultiplier(), onlyLegalMoves));
        possibleMoves.addAll(getCaptureMove(-1, -color.getMultiplier(), onlyLegalMoves));
        return possibleMoves;
    }

    /**
     * Returns a set containing the moves this pawn can make by only going forward.
     *
     * @param  dy              the direction to move forward (-1 for white, 1 for black)
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing the moves this pawn can make by
     *                         only going forward
     */
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

    /**
     * Returns a set containing the moves this pawn can make by capturing
     * pieces in the immediate forward diagonal squares.
     *
     * @param  dx              the direction to move horizontally
     * @param  dy              the direction to move forward (-1 for white, 1 for black)
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing the moves this pawn can make by
     *                         capturing pieces in the immediate forward diagonal squares
     */
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
