package chess.players;

import chess.*;
import chess.pieces.*;

import java.util.*;

public class RandomPlayer extends Player {
    private final Random random;

    public RandomPlayer(Board board, Piece.Color color) {
        this(board, color, new Random());
    }

    public RandomPlayer(Board board, Piece.Color color, long seed) {
        this(board, color, new Random(seed));
    }

    private RandomPlayer(Board board, Piece.Color color, Random random) {
        super(board, color);
        this.random = random;
    }

    public Move getMove() {
        Set<Move> possibleMoves = getPossibleMoves();
        int index = random.nextInt(possibleMoves.size());
        for (Move m : possibleMoves) {
            if (index-- == 0) {
                return m;
            }
        }
        return null;
    }

    public String toString() {
        return Board.ANSI_YELLOW + "Computer (Random)" + Board.ANSI_RESET;
    }
}
