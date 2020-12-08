package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;
import java.util.*;

public class SuboptimalMinimaxPlayer extends MinimaxPlayer {
    private final Random random;

    public SuboptimalMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    public SuboptimalMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic, int searchDepth) {
        super(board, color, heuristic, searchDepth);
        this.random = new Random(0); // TODO: UN-SEED THIS IN PRODUCTION
    }

    @Override
    protected Move selectMove(SortedSet<Move> result) {
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
