package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;
import java.util.*;

public class MinimaxPlayer extends Player {
    private static final int SEARCH_DEPTH = 4;

    private Heuristic heuristic;

    public MinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        super(board, color);
        this.heuristic = heuristic;
    }

    public Move getMove() {
        return minimax(board, color);
    }

    private Move minimax(Board board, Piece.Color color) {
        PriorityQueue<Move> pq = new PriorityQueue<>();
        for (Move move : board.getPossibleMoves(color)) {
            board.doMove(move);
            move.setHeuristicValue(minimax(board, color, SEARCH_DEPTH, true));
            board.undoLastMove();
            pq.add(move);
        }
        return pq.peek();
    }

    private double minimax(Board board, Piece.Color color, int depth, boolean isMax) {
        if (depth == 0) {
            return heuristic.calculateValue(board, color);
        }
        if (isMax) {
            double max = Double.NEGATIVE_INFINITY;
            for (Move move : board.getPossibleMoves(color)) {
                board.doMove(move);
                max = Math.max(max, minimax(board, oppositeColor(color), depth - 1, false));
                board.undoLastMove();
            }
            return max;
        } else {
            double min = Double.POSITIVE_INFINITY;
            for (Move move : board.getPossibleMoves(color)) {
                board.doMove(move);
                min = Math.min(min, minimax(board, oppositeColor(color), depth - 1, true));
                board.undoLastMove();
            }
            return min;
        }
    }

    private Piece.Color oppositeColor(Piece.Color currentColor) {
        return currentColor == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
    }
}
