package chess.players;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import chess.heuristics.*;
import java.util.*;

public class MinimaxPlayer extends Player {
    private static final int SEARCH_DEPTH = 3;

    private final Heuristic heuristic;
    private final Map<Move, SortedSet<Move>> memo;

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
            start.calculateHeuristicValue(heuristic);
            return start;
        }
        // TODO: Iterate in different order if maximizing or minimizing -- could use NavigableSet.descendingSet()
        SortedSet<Move> set = new TreeSet<>();
        for (Move m : memo.containsKey(start) ? memo.get(start) : board.getPossibleMoves(color)) {
            board.doMove(m);
            m.setHeuristicValue(minimax(oppositeColor(color),depth - 1, !isMax).getHeuristicValue());
            set.add(m);
            board.undoLastMove();
        }
        memo.put(start, set);
        return isMax ? set.last() : set.first();
    }
}
