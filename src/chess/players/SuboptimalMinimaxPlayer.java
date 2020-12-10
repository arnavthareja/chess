package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;

import java.util.*;

/**
 * A minimax player that doesn't always pick the best move.
 */
public final class SuboptimalMinimaxPlayer extends MinimaxPlayer {
    /**
     * The source of this player's randomness
     */
    private final Random random;

    /**
     * Constructs a new suboptimal minimax player with the given board, color, and heuristic.
     *
     * @param board      the board this player plays on
     * @param color      the color of this player
     * @param heuristic  the heuristic function to use in the minimax algorithm
     */
    public SuboptimalMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    /**
     * Constructs a new suboptimal minimax player with the given board, color,
     * heuristic, and search depth.
     *
     * @param board        the board this player plays on
     * @param color        the color of this player
     * @param heuristic    the heuristic function to use in the minimax algorithm
     * @param searchDepth  the depth to search to in the minimax algorithm
     */
    public SuboptimalMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic,
                                   int searchDepth) {
        super(board, color, heuristic, searchDepth);
        random = new Random();
    }

    /**
     * Selects and returns the move that the player will make. Randomly selects
     * the move to make, with probability of selecting the move based on its
     * rank compared to other moves.
     *
     * @param  result  the sorted set to select the move from
     * @return         the move that the player will make
     */
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

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        return Board.ANSI_PURPLE + "Computer (Medium)" + Board.ANSI_RESET;
    }
}
