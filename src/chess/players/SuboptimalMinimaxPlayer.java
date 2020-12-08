package chess.players;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import chess.heuristics.*;
import java.util.*;

public class SuboptimalMinimaxPlayer extends MinimaxPlayer {
    private final Random random;

    public SuboptimalMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    public SuboptimalMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic, int searchDepth) {
        super(board, color, heuristic, searchDepth);
        this.random = new Random();
    }

    @Override
    // For DESIGN.md: used sorted set instead of priority queue because wanted to iterate over it without destroying it
    protected Move negamax(Piece.Color color, int depth, double alpha, double beta) {
        String boardState = board.stateString();
        Move start = board.getLastMove();
        if (depth == 0) {
            // If start is null here something went wrong. start should never be null
            assert start != null;
            start.calculateHeuristicValue(heuristic, board, color);
            return start;
        }
        SortedSet<Move> moves = memo.containsKey(boardState) ? memo.get(boardState) : new TreeSet<>(getPossibleMoves());
        SortedSet<Move> result = new TreeSet<>();
        for (Move m : moves) {
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
        memo.put(boardState, result);
//        System.out.println("Depth: " + depth + " " + color + " " + " " + result.first().getHeuristicValue() + " " + result.last().getHeuristicValue());
//        System.out.println("Selected: " + (result.isEmpty() ? null : result.last().getHeuristicValue()));
        if (result.first().getHeuristicValue() == Double.POSITIVE_INFINITY) {
            return result.first();
        }
        SortedSet<Move> taken = new TreeSet<>();
        for (int i = 0; i < result.size() / 2 && random.nextBoolean(); i++) {
            taken.add(result.first());
            result.remove(result.first());
        }
        Move selected = result.first();
        result.addAll(taken);
        return selected;
    }
}
