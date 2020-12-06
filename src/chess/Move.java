package chess;

import chess.pieces.*;
import chess.heuristics.*;

public class Move implements Comparable<Move> {
    private final Square start;
    private final Square end;
    private final Square rookStart;
    private final Square rookEnd;
    private final Piece capturedPiece;
    private final boolean pieceAlreadyMoved;
    private final boolean isCastleMove;
    private double heuristicValue;

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
        isCastleMove = rookStart != null;
        this.heuristicValue = heuristicValue;
        capturedPiece = end.getPiece();
        pieceAlreadyMoved = start.getPiece().getAlreadyMoved();
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
    }

    public double calculateHeuristicValue(Heuristic heuristic) {
        setHeuristicValue(heuristic.calculateValue(start.getBoard(), start.getPiece().getColor()));
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

    public int compareTo(Move other) {
        return Double.compare(heuristicValue, other.heuristicValue);
    }

    public String toString() {
        // TODO: Add castling and check notation (0-0 for kingside, 0-0-0 for queenside, + for check)
        // TODO: Add file name after piece if ambiguous, maybe use descriptive notation instead
        return "" + start.getPiece() + (isCaptureMove() ? "x" : "") + end.notation();
    }

    public boolean equals(Move other) {
        return capturedPiece == other.capturedPiece && heuristicValue == other.heuristicValue &&
               start.equals(other.start) && end.equals(other.end);
    }
}
