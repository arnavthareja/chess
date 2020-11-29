package chess.heuristics;

import chess.*;
import chess.pieces.*;

public interface Heuristic {
    public double calculateValue(Board board, Piece.Color team);
}
