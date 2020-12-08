package chess.players;

import chess.*;
import chess.pieces.*;
import chess.heuristics.*;
import java.util.*;

public class WorstMinimaxPlayer extends MinimaxPlayer {
    public WorstMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic) {
        this(board, color, heuristic, DEFAULT_SEARCH_DEPTH);
    }

    public WorstMinimaxPlayer(Board board, Piece.Color color, Heuristic heuristic, int searchDepth) {
        super(board, color, heuristic, searchDepth);
    }

    @Override
    protected Move selectMove(SortedSet<Move> result) {
        return result.last();
    }

    public String toString() {
        return "Easy Computer";
    }
}
