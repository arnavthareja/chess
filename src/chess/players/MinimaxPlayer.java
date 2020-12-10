package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;

import java.util.*;

import static chess.pieces.Piece.Color.*;

/**
 * A player that uses the minimax algorithm to select its moves.
 */
public class MinimaxPlayer extends Player {
    /**
     * The default search depth for the minimax algorithm.
     */
    public static final int DEFAULT_SEARCH_DEPTH = 3;

    /**
     * The search depth to use when there are few pieces left on the board.
     */
    public static final int LOW_PIECE_SEARCH_DEPTH = 6;

    /**
     * The search depth for the extreme difficulty computer player.
     */
    public static final int EXTREME_DIFFICULTY_SEARCH_DEPTH = 10;

    /**
     * The current depth for the minimax algorithm to search to.
     */
    private int searchDepth;

    /**
     * The heuristic to evaluate board states with.
     */
    private final Heuristic heuristic;

    /**
     * Maps board states to sets of possible moves to improve minimax algorithm efficiency.
     */
    private final Map<String, SortedSet<Move>> memo;

    /**
     * Constructs a new minimax player with the given board, color, and heuristic.
     *
     * @param board      the board this player plays on
     * @param color      the color of this player
     * @param heuristic  the heuristic function to use in the minimax algorithm
     */
    public MinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    /**
     * Constructs a new minimax player with the given board, color, heuristic,
     * and search depth.
     *
     * @param board        the board this player plays on
     * @param color        the color of this player
     * @param heuristic    the heuristic function to use in the minimax algorithm
     * @param searchDepth  the depth to search to in the minimax algorithm
     */
    public MinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic, int searchDepth) {
        super(board, color);
        this.heuristic = heuristic;
        memo = new HashMap<>();
        this.searchDepth = searchDepth;
    }

    /**
     * Returns the next move this player will make.
     *
     * @return the next move this player will make
     */
    public Move getMove() {
        Move m = null;
        if (searchDepth < LOW_PIECE_SEARCH_DEPTH && board.fewPiecesLeft()) {
            searchDepth = LOW_PIECE_SEARCH_DEPTH;
        }
        for (int i = 1; i <= searchDepth; i++) {
            m = negamax(color, i, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        return m;
    }

    /**
     * Chooses the move a player will make using the negamax algorithm, a
     * version of minimax applicable to zero-sum games.
     *
     * @param  color  the color of the player to make the move
     * @param  depth  the depth to search to
     * @param  alpha  the alpha value for alpha-beta pruning
     * @param  beta   the beta value for alpha-beta pruning
     * @return        the move a player will make at the current board state
     */
    private Move negamax(Piece.Color color, int depth, double alpha, double beta) {
        String boardState = board.stateString();
        Move start = board.getLastMove();
        if (depth == 0) {
            start.calculateHeuristicValue(heuristic, board, color);
            return start;
        }
        SortedSet<Move> moves = memo.containsKey(boardState) ? memo.get(boardState)
                                                             : new TreeSet<>(getPossibleMoves());
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
        return selectMove(result);
    }

    /**
     * Selects and returns the move that the player will make. Selects the move
     * with the highest heuristic value.
     *
     * @param  result  the sorted set to select the move from
     * @return         the move that the player will make
     */
    protected Move selectMove(SortedSet<Move> result) {
        return result.first();
    }

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        if (searchDepth >= EXTREME_DIFFICULTY_SEARCH_DEPTH) {
            return Board.ANSI_RED + "Computer (Extreme)" + Board.ANSI_RESET;
        }
        return Board.ANSI_BLUE + "Computer (Hard)" + Board.ANSI_RESET;
    }
}
