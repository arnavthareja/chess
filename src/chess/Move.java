package chess;

import chess.pieces.*;
import chess.heuristics.*;

import java.util.*;

/**
 * A chess move. A move can represent a regular chess move as well as pawn
 * promotion and castle moves. Moves can be compared to determine which has a
 * higher heuristic value.
 */
public final class Move implements Comparable<Move> {
    /**
     * The starting square of the move. For castle moves, the starting square
     * of the king.
     */
    private final Square start;

    /**
     * The ending square of the move. For castle moves, the ending square of
     * the king.
     */
    private final Square end;

    /**
     * For castle moves, the starting square of the rook. Null for regular moves.
     */
    private final Square rookStart;

    /**
     * For castle moves, the ending square of the rook. Null for regular moves.
     */
    private final Square rookEnd;

    /**
     * The piece that is captured at the end of this move. Null if no piece is
     * captured. Used to correctly undo moves from a board.
     */
    private final Piece capturedPiece;

    /**
     * The pawn that is promoted at the end of this move. Null if no pawn is
     * promoted. Used to correctly undo moves from a board.
     */
    private final Piece promotedPawn;

    /**
     * Whether the piece doing this move had moved before this. Used to
     * correctly undo moves from a board.
     */
    private final boolean pieceAlreadyMoved;

    /**
     * The heuristic value of this move. 0 if not calculated.
     */
    private double heuristicValue;

    /**
     * Whether the heuristic value of this move has been set or calculated.
     */
    private boolean heuristicValueSet;

    /**
     * The notation of this move in algebraic notation.
     */
    private final String notation;

    /**
     * Constructs a new move with the specified start and end squares.
     *
     * @param start  the square the move starts on
     * @param end    the square the move ends on
     */
    public Move(Square start, Square end) {
        this(start, end, null, null);
    }

    /**
     * Constructs a new move with the specified start, end, rook start, and
     * rook end squares.
     *
     * @param start      the square the move starts on, or the square the king
     *                   starts on if castling
     * @param end        the square the move ends on, or the square the king
     *                   ends on if castling
     * @param rookStart  the square for the rook to start at if castling, null
     *                   for a regular move
     * @param rookEnd    the square for the rook to end at if castling, null
     *                   for a regular move
     * @throws IllegalArgumentException if start or end squares are null or if
     *                                  only one of rook start and rook end
     *                                  squares are given
     */
    public Move(Square start, Square end, Square rookStart, Square rookEnd) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end squares must not be null.");
        } else if ((rookStart == null || rookEnd == null) &&
                   (rookStart != null || rookEnd != null)) {
            throw new IllegalArgumentException("Rook start and end must both be given or null.");
        }
        this.start = start;
        this.end = end;
        this.rookStart = rookStart;
        this.rookEnd = rookEnd;
        capturedPiece = end.getPiece();
        pieceAlreadyMoved = start.getPiece().getAlreadyMoved();
        promotedPawn = isPromotion() ? start.getPiece() : null;
        this.heuristicValue = 0;
        heuristicValueSet = false;
        notation = notation();
    }

    /**
     * Returns the heuristic value of this move.
     *
     * @return the heuristic value of this move
     */
    public double getHeuristicValue() {
        return heuristicValue;
    }

    /**
     * Sets the heuristic value of this move to the given value.
     *
     * @param heuristicValue  the value to set the heuristic value to
     */
    public void setHeuristicValue(double heuristicValue) {
        this.heuristicValue = heuristicValue;
        heuristicValueSet = true;
    }

    /**
     * Calculates the heuristic value of this move for the given heuristic,
     * board, and color, and sets the heuristic value of this move to the
     * calculated value.
     *
     * @param heuristic  the heuristic to use to calculate the value of this move
     * @param board      the board for which to calculate the value of this move
     * @param color      the color for which to calculate the value of this move
     */
    public void calculateHeuristicValue(Heuristic heuristic, Board board, Piece.Color color) {
        setHeuristicValue(heuristic.calculateValue(board, color));
    }

    /**
     * Returns true if a piece is captured as a result of this move, false otherwise.
     *
     * @return true if a piece is captured as a result of this move, false otherwise
     */
    public boolean isCaptureMove() {
        return capturedPiece != null;
    }

    /**
     * Returns true if this move is a castle move, false otherwise.
     *
     * @return true if this move is a castle move, false otherwise
     */
    public boolean isCastleMove() {
        return rookStart != null;
    }

    /**
     * Returns true if a pawn is promoted as a result of this move, false otherwise.
     *
     * @return true if a pawn is promoted as a result of this move, false otherwise
     */
    public boolean isPromotion() {
        return promotedPawn != null || (start.getPiece() instanceof Pawn && (end.getRow() == 7 ||
                                                                             end.getRow() == 0));
    }

    /**
     * Returns the square this move starts on, or the square the king starts on
     * if castling.
     *
     * @return the square this move starts on, or the square the king starts on
     *         if castling
     */
    public Square getStart() {
        return start;
    }

    /**
     * Returns the square this move ends on, or the square the king ends on
     * if castling.
     *
     * @return the square this move ends on, or the square the king ends on
     *         if castling
     */
    public Square getEnd() {
        return end;
    }

    /**
     * Returns the square the rook starts on if castling, null for a regular move.
     *
     * @return the square the rook starts on if castling, null for a regular move
     */
    public Square getRookStart() {
        return rookStart;
    }

    /**
     * Returns the square the rook ends on if castling, null for a regular move.
     *
     * @return the square the rook ends on if castling, null for a regular move
     */
    public Square getRookEnd() {
        return rookEnd;
    }

    /**
     * Returns the piece that is captured as a result of this move, null if no
     * piece is captured.
     *
     * @return the piece that is captured as a result of this move, null if no
     *         piece is captured
     */
    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    /**
     * Returns the pawn that is promoted as a result of this move, null if no
     * pawn is promoted.
     *
     * @return the pawn that is promoted as a result of this move, null if no
     *         pawn is promoted
     */
    public Piece getPromotedPawn() {
        return promotedPawn;
    }

    /**
     * Returns true if the piece doing this move has moved before, false otherwise.
     *
     * @return true if the piece doing this move has moved before, false otherwise
     */
    public boolean getPieceAlreadyMoved() {
        return pieceAlreadyMoved;
    }

    /**
     * Compares this move to another move by heuristic value in descending order.
     *
     * @param  other  the move to be compared to
     * @return        a positive integer if this move's heuristic value is less
     *                than the other move's value, a negative integer if this
     *                move's value is greater than the other move's value, 0
     *                if both values are equal, or 1 if both heuristic values
     *                have not been set
     */
    public int compareTo(Move other) {
        return heuristicValueSet || other.heuristicValueSet
                ? Double.compare(other.heuristicValue, heuristicValue) : 1;
    }

    /**
     * Returns the notation for this move in algebraic notation.
     *
     * @return the notation for this move in algebraic notation
     */
    private String notation() {
        // TODO: Add file name after piece if ambiguous, maybe use descriptive notation instead
        String result = start.getPiece().getColor() == Piece.Color.WHITE ? Board.ANSI_BLUE
                                                                         : Board.ANSI_BLACK;
        if (isCastleMove()) {
            return result + (rookStart.getCol() == 0 ? "0-0-0" : "0-0") + Board.ANSI_RESET;
        }
        return result + start.getPiece() + (isCaptureMove() ? "x" : "") + end.notation()
                + Board.ANSI_RESET;
        // TODO: Fix check notation -- currently causes StackOverflowError
        // + (start.getBoard().inCheck(oppositeColor(start.getPiece().getColor())) ? "+" : "");
    }

    /**
     * Returns a string representation of this move.
     *
     * @return a string representation of this move
     */
    @Override
    public String toString() {
        return notation;
    }

    /**
     * Returns true if this move has the the same starting and ending squares
     * as the other move, false otherwise.
     *
     * @param  other  the move to determine equality to
     * @return        true if this move has the the same starting and ending
     *                squares as the other move, false otherwise
     */
    public boolean sameAs(Move other) {
        return this == other || (start.equals(other.start) && end.equals(other.end));
    }

    /**
     * Returns true if this move equals the other object, false otherwise.
     *
     * @param  o  the object to determine equality to
     * @return    true if this move equals the other object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return pieceAlreadyMoved == move.pieceAlreadyMoved && isCastleMove() == move.isCastleMove()
                && Double.compare(move.heuristicValue, heuristicValue) == 0
                && heuristicValueSet == move.heuristicValueSet && start.equals(move.start)
                && end.equals(move.end) && Objects.equals(rookStart, move.rookStart)
                && Objects.equals(rookEnd, move.rookEnd)
                && Objects.equals(capturedPiece, move.capturedPiece);
    }

    /**
     * Returns a hash code representation of this move.
     *
     * @return a hash code representation of this move
     */
    @Override
    public int hashCode() {
        return Objects.hash(start, end, rookStart, rookEnd, capturedPiece, pieceAlreadyMoved,
                            heuristicValue, heuristicValueSet);
    }
}
