package chess;

import chess.pieces.*;
import chess.heuristics.*;

import java.util.Objects;

public class Move implements Comparable<Move> {
    private final Square start;
    private final Square end;
    private final Square rookStart;
    private final Square rookEnd;
    private final Piece capturedPiece;
    private final Piece capturingPiece;
    private final boolean pieceAlreadyMoved;
    private final boolean isCastleMove;
    private final boolean isPromotion;
    private double heuristicValue;
    private boolean heuristicValueSet;

    public Move(Square start, Square end) {
        this(start, end, 0);
    }

    public Move(Square start, Square end, double heuristicValue) {
        this(start, end, null, null, false, heuristicValue);
    }

    public Move(Square start, Square end, boolean isPromotion) {
        this(start, end, null, null, isPromotion, 0);
    }

    public Move(Square start, Square end, Square rookStart, Square rookEnd) {
        this(start, end, rookStart, rookEnd, false, 0);
    }

    public Move(Square start, Square end, Square rookStart, Square rookEnd, boolean isPromotion, double heuristicValue) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end squares must not be null.");
        } else if ((rookStart == null || rookEnd == null) && (rookStart != null || rookEnd != null)) {
            throw new IllegalArgumentException("Rook's start and end squares must both be given or null.");
        }
        this.start = start;
        this.end = end;
        this.rookStart = rookStart;
        this.rookEnd = rookEnd;
        capturedPiece = end.getPiece();
        capturingPiece = start.getPiece();
        pieceAlreadyMoved = start.getPiece().getAlreadyMoved();
        isCastleMove = rookStart != null;
        this.isPromotion = isPromotion;
        this.heuristicValue = heuristicValue;
        heuristicValueSet = false;
    }

    // Constructor to make a move from a string in proper notation
    // TODO: Implement
    public Move(String fromString) {
        this(null, null);
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(double heuristicValue) {
        this.heuristicValue = heuristicValue;
        heuristicValueSet = true;
    }

    public double calculateHeuristicValue(Heuristic heuristic, Board board, Piece.Color color) {
        setHeuristicValue(heuristic.calculateValue(board, color));
        return getHeuristicValue();
    }

    public boolean isCaptureMove() {
        return capturedPiece != null;
    }

    public boolean isCastleMove() {
        return isCastleMove;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public Square getStart() {
        return start;
    }

    public Square getEnd() {
        return end;
    }

    public Square getRookStart() {
        return rookStart;
    }

    public Square getRookEnd() {
        return rookEnd;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public Piece getCapturingPiece() {
        return capturingPiece;
    }

    public boolean getPieceAlreadyMoved() {
        return pieceAlreadyMoved;
    }

    // Returns 1 if heuristic value has not been set so that TreeSet doesn't view it as a duplicate move
    public int compareTo(Move other) {
        return heuristicValueSet || other.heuristicValueSet ? Double.compare(other.heuristicValue, heuristicValue) : 1;
    }

    public String toString() {
        // TODO: Add castling and check notation (0-0 for kingside, 0-0-0 for queenside, + for check)
        // TODO: Add file name after piece if ambiguous, maybe use descriptive notation instead
        return "" + start.getPiece() + (isCaptureMove() ? "x" : "") + end.notation();
    }

    public boolean sameAs(Move other) {
        return this == other || (start.equals(other.start) && end.equals(other.end));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return pieceAlreadyMoved == move.pieceAlreadyMoved && isCastleMove == move.isCastleMove && Double.compare(move.heuristicValue, heuristicValue) == 0 && heuristicValueSet == move.heuristicValueSet && start.equals(move.start) && end.equals(move.end) && Objects.equals(rookStart, move.rookStart) && Objects.equals(rookEnd, move.rookEnd) && Objects.equals(capturedPiece, move.capturedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, rookStart, rookEnd, capturedPiece, pieceAlreadyMoved, isCastleMove, heuristicValue, heuristicValueSet);
    }
}
