package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;

import java.util.*;

/**
 * A minimax player that selects the worst move for itself.
 */
public final class WorstMinimaxPlayer extends MinimaxPlayer {
    /**
     * Constructs a new worst minimax player with the given board, color, and heuristic.
     *
     * @param board      the board this player plays on
     * @param color      the color of this player
     * @param heuristic  the heuristic function to use in the minimax algorithm
     */
    public WorstMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    /**
     * Constructs a new worst minimax player with the given board, color,
     * heuristic, and search depth.
     *
     * @param board        the board this player plays on
     * @param color        the color of this player
     * @param heuristic    the heuristic function to use in the minimax algorithm
     * @param searchDepth  the depth to search to in the minimax algorithm
     */
    public WorstMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic, int searchDepth) {
        super(board, color, heuristic, searchDepth);
    }

    /**
     * Selects and returns the move that the player will make. Selects the move
     * with the highest heuristic value for the opposing player and the lowest
     * heuristic value for this player.
     *
     * @param  result  the sorted set to select the move from
     * @return         the move that the player will make
     */
    @Override
    protected Move selectMove(SortedSet<Move> result) {
        return result.last();
    }

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        return Board.ANSI_CYAN + "Computer (Easy)" + Board.ANSI_RESET;
    }
}
