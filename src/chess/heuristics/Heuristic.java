package chess.heuristics;

import chess.*;
import chess.pieces.*;

/**
 * A heuristic that can calculate the value of board states.
 */
public interface Heuristic {
    /**
     * Calculates the heuristic value for the given board and color.
     *
     * @param  board  the board for which to calculate the heuristic value
     * @param  color  the color for which to calculate the heuristic value
     * @return        the heuristic value for the given board and color
     */
    double calculateValue(Board board, Piece.Color color);
}
