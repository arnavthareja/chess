package chess.players;

import chess.*;
import chess.pieces.*;

import java.util.*;

public final class RandomPlayer extends Player {
    /**
     * The source of this player's randomness
     */
    private final Random random;

    /**
     * Constructs a new random player with the given board and color.
     *
     * @param board  the board this player plays on
     * @param color  the color of this player
     */
    public RandomPlayer(Board board, Piece.Color color) {
        this(board, color, new Random());
    }

    /**
     * Constructs a new random player with the given board, color, and seed.
     *
     * @param board  the board this player plays on
     * @param color  the color of this player
     * @param seed   the seed for the player's randomness
     */
    public RandomPlayer(Board board, Piece.Color color, long seed) {
        this(board, color, new Random(seed));
    }

    /**
     * Constructs a new random player with the given board, color, and source of randomness.
     *
     * @param board   the board this player plays on
     * @param color   the color of this player
     * @param random  the source of this player's randomness
     */
    private RandomPlayer(Board board, Piece.Color color, Random random) {
        super(board, color);
        this.random = random;
    }

    /**
     * Returns the next move this player will make.
     *
     * @return the next move this player will make
     */
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

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        return Board.ANSI_YELLOW + "Computer (Random)" + Board.ANSI_RESET;
    }
}
