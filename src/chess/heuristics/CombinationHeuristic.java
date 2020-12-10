package chess.heuristics;

import chess.*;
import chess.pieces.*;

/**
 * A heuristic based on both piece positions and material piece values.
 */
public final class CombinationHeuristic implements Heuristic {
    /**
     * The material heuristic used to calculate material piece values.
     */
    private final MaterialHeuristic material;

    /**
     * The positional heuristic used to calculate positional piece values.
     */
    private final PositionalHeuristic positional;

    /**
     * Constructs a new combination heuristic.
     */
    public CombinationHeuristic() {
        material = new MaterialHeuristic();
        positional = new PositionalHeuristic();
    }

    /**
     * Calculates the heuristic value for the given board and color.
     *
     * @param  board  the board for which to calculate the heuristic value
     * @param  color  the color for which to calculate the heuristic value
     * @return        the heuristic value for the given board and color
     */
    public double calculateValue(Board board, Piece.Color color) {
        return material.calculateValue(board, color) + positional.calculateValue(board, color);
    }
}