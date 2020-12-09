package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;

import java.util.*;

import static chess.pieces.Piece.Color.*;

public class MinimaxPlayer extends Player {
    public static final int DEFAULT_SEARCH_DEPTH = 3;
    public static final int LOW_PIECE_SEARCH_DEPTH = 6;
    public static final int EXTREME_DIFFICULTY_SEARCH_DEPTH = 10;

    protected int searchDepth;
    protected final Heuristic heuristic;
    protected final Map<String, SortedSet<Move>> memo;

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
        if (searchDepth < LOW_PIECE_SEARCH_DEPTH && board.fewPiecesLeft()) {
            searchDepth = LOW_PIECE_SEARCH_DEPTH;
        }
        for (int i = 1; i <= searchDepth; i++) {
            m = negamax(color, i, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        return m;
    }

    // Used sorted set instead of priority queue to iterate over it without destroying it
    private Move negamax(Piece.Color color, int depth, double alpha, double beta) {
        String boardState = board.stateString();
        Move start = board.getLastMove();
        if (depth == 0) {
            // If start is null here something went wrong. start should never be null.
            assert start != null;
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

    protected Move selectMove(SortedSet<Move> result) {
        return result.first();
    }

    public String toString() {
        if (searchDepth >= EXTREME_DIFFICULTY_SEARCH_DEPTH) {
            return Board.ANSI_RED + "Computer (Extreme)" + Board.ANSI_RESET;
        }
        return Board.ANSI_BLUE + "Computer (Hard)" + Board.ANSI_RESET;
    }
}
