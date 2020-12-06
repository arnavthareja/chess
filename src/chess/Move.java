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
    private final boolean pieceAlreadyMoved;
    private final boolean isCastleMove;
    private double heuristicValue;
    private boolean heuristicValueSet;

    public Move(Square start, Square end) {
        this(start, end, 0);
    }

    public Move(Square start, Square end, double heuristicValue) {
        this(start, end, null, null, heuristicValue);
    }

    public Move(Square start, Square end, Square rookStart, Square rookEnd) {
        this(start, end, rookStart, rookEnd, 0);
    }

    public Move(Square start, Square end, Square rookStart, Square rookEnd, double heuristicValue) {
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
        pieceAlreadyMoved = start.getPiece().getAlreadyMoved();
        isCastleMove = rookStart != null;
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

    public boolean getPieceAlreadyMoved() {
        return pieceAlreadyMoved;
    }

    // Returns 1 if heuristic value has not been set so that TreeSet doesn't view it as a duplicate move
    public int compareTo(Move other) {
        return heuristicValueSet ? Double.compare(heuristicValue, other.heuristicValue) : 1;
    }

    public String toString() {
        // TODO: Add castling and check notation (0-0 for kingside, 0-0-0 for queenside, + for check)
        // TODO: Add file name after piece if ambiguous, maybe use descriptive notation instead
        return "" + start.getPiece() + (isCaptureMove() ? "x" : "") + end.notation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return pieceAlreadyMoved == move.pieceAlreadyMoved && isCastleMove == move.isCastleMove && Double.compare(move.heuristicValue, heuristicValue) == 0 && heuristicValueSet == move.heuristicValueSet && start.equals(move.start) && end.equals(move.end) && Objects.equals(rookStart, move.rookStart) && Objects.equals(rookEnd, move.rookEnd) && Objects.equals(capturedPiece, move.capturedPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, rookStart, rookEnd, capturedPiece, pieceAlreadyMoved, isCastleMove, heuristicValue, heuristicValueSet);
    }
}
