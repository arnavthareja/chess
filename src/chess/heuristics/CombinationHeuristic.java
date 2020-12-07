package chess.heuristics;

import chess.*;
import chess.pieces.*;

public class CombinationHeuristic implements Heuristic {
    private final MaterialHeuristic material;
    private final PositionalHeuristic positional;

    public CombinationHeuristic() {
        material = new MaterialHeuristic();
        positional = new PositionalHeuristic();
    }

    public double calculateValue(Board board, Piece.Color color) {
        return material.calculateValue(board, color) + positional.calculateValue(board, color);
    }
}