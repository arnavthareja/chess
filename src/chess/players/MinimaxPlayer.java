package chess.players;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import chess.heuristics.*;
import java.util.*;

public class MinimaxPlayer extends Player {
    private static final int SEARCH_DEPTH = 3;

    private final Heuristic heuristic;
    private final Map<Move, NavigableSet<Move>> memo;

    public MinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        super(board, color);
        this.heuristic = heuristic;
        memo = new HashMap<>();
    }

    public Move getMove() {
        return minimax(color, SEARCH_DEPTH, true);
    }

    // For DESIGN.md: used sorted set instead of priority queue because wanted to iterate over it without destroying it
    private Move minimax(Piece.Color color, int depth, boolean isMax) {
        Move start = board.getLastMove();
        if (depth == 0) {
            start.calculateHeuristicValue(heuristic, board, color);
            return start;
        }
        // TODO: Iterate in different order if maximizing or minimizing -- could use NavigableSet.descendingSet()
        NavigableSet<Move> moves = memo.containsKey(start) ? memo.get(start) : new TreeSet<>(board.getPossibleMoves(color));
        NavigableSet<Move> result = new TreeSet<>();
        // This reverses getPossibleMoves too, but it doesn't matter as they're in random order (I think)
        for (Move m : isMax ? moves.descendingSet() : moves) {
            board.doMove(m);
            m.setHeuristicValue(minimax(oppositeColor(color),depth - 1, !isMax).getHeuristicValue());
            result.add(m);
            board.undoLastMove();
        }
        memo.put(start, result);
        return isMax ? result.last() : result.first();
    }
}
