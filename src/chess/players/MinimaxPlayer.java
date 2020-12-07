package chess.players;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import chess.heuristics.*;
import java.util.*;

public class MinimaxPlayer extends Player {
    public static final int DEFAULT_SEARCH_DEPTH = 2;

    private final int searchDepth;
    private final Heuristic heuristic;
    private final Map<Deque<Move>, NavigableSet<Move>> memo;

    public MinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    public MinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic, int searchDepth) {
        super(board, color);
        this.heuristic = heuristic;
        memo = new HashMap<>();
        this.searchDepth = searchDepth;
    }

    public Move getMove() {
        Move m = null;
        for (int i = 1; i <= searchDepth; i++) {
            m = negamax(color, i, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        return m;
    }

    // For DESIGN.md: used sorted set instead of priority queue because wanted to iterate over it without destroying it
    private Move negamax(Piece.Color color, int depth, double alpha, double beta) {
        // Create a new deque to avoid weirdness with reference semantics
        Deque<Move> allMoves = new ArrayDeque<>(board.getAllMoves());
        Move start = allMoves.peek();
        if (depth == 0) {
            // If start is null here something went wrong. start should never be null
            assert start != null;
            start.calculateHeuristicValue(heuristic, board, color);
            return start;
        }
        NavigableSet<Move> moves = memo.containsKey(allMoves) ? memo.get(allMoves) : new TreeSet<>(getPossibleMoves());
        NavigableSet<Move> result = new TreeSet<>();
        // This reverses getPossibleMoves too, but it doesn't matter as they're in random order (I think)
        for (Move m : moves.descendingSet()) {
            board.doMove(m);
            double value;
            if (board.inCheckmate(color)) {
                value = Double.NEGATIVE_INFINITY;
            } else if (board.inCheckmate(oppositeColor(color))) {
                value = Double.POSITIVE_INFINITY;
            } else if (board.inStalemate()) {
                value = 0;
            } else {
                value = -negamax(oppositeColor(color),depth - 1, -beta, -alpha).getHeuristicValue();
            }
            m.setHeuristicValue(value);
            result.add(m);
            board.undoLastMove();
            alpha = Math.max(alpha, m.getHeuristicValue());
            if (alpha >= beta) {
                break;
            }
        }
        memo.put(allMoves, result);
//        System.out.println("" + color + " " + " " + isMax + " " + result.first().getHeuristicValue() + " " + result.last().getHeuristicValue());
//        System.out.println("Selected: " + (result.isEmpty() ? null : isMax ? result.last().getHeuristicValue() : result.first().getHeuristicValue()));
        return result.isEmpty() ? null : result.last();
    }

    // For DESIGN.md: used sorted set instead of priority queue because wanted to iterate over it without destroying it
    private Move minimax(Piece.Color color, int depth, double alpha, double beta, boolean isMax) {
        // Create a new deque to avoid weirdness with reference semantics
        Deque<Move> allMoves = new ArrayDeque<>(board.getAllMoves());
        Move start = allMoves.peek();
        if (depth == 0) {
            // If start is null here something went wrong. start should never be null
            assert start != null;
            start.calculateHeuristicValue(heuristic, board, color);
            return start;
        }
        NavigableSet<Move> moves = memo.containsKey(allMoves) ? memo.get(allMoves) : new TreeSet<>(getPossibleMoves());
        NavigableSet<Move> result = new TreeSet<>();
        // This reverses getPossibleMoves too, but it doesn't matter as they're in random order (I think)
        for (Move m : isMax ? moves.descendingSet() : moves) {
            board.doMove(m);
            Move next = minimax(oppositeColor(color),depth - 1, alpha, beta, !isMax);
            double value;
            if (next == null) {
                if (board.inCheckmate(color)) {
                    value = Double.NEGATIVE_INFINITY;
                } else if (board.inCheckmate(oppositeColor(color))) {
                    value = Double.POSITIVE_INFINITY;
                } else {
                    value = 0;
                }
            } else {
                value = next.getHeuristicValue(); // Test this not being negated
            }
            m.setHeuristicValue(value);
            result.add(m);
            board.undoLastMove();
            if (isMax) {
                alpha = Math.max(alpha, m.getHeuristicValue());
            } else {
                beta = Math.min(beta, m.getHeuristicValue());
            }
            if (alpha >= beta) {
                break;
            }
        }
        memo.put(allMoves, result);
//        System.out.println("" + color + " " + " " + isMax + " " + result.first().getHeuristicValue() + " " + result.last().getHeuristicValue());
//        System.out.println("Selected: " + (result.isEmpty() ? null : isMax ? result.last().getHeuristicValue() : result.first().getHeuristicValue()));
        return result.isEmpty() ? null : isMax ? result.last() : result.first();
    }
}
