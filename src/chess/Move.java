package chess;

import chess.minimax.*;

public class Move implements Comparable<Move> {
    private Square start;
    private Square end;
    private double heuristicValue;

    public Move(Square start, Square end) {
        this(start, end, 0);
    }

    public Move(Square start, Square end, double heuristicValue) {
        this.start = start;
        this.end = end;
        this.heuristicValue = heuristicValue;
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

    public int compareTo(Move other) {
        return Double.compare(heuristicValue, other.heuristicValue);
    }
}
