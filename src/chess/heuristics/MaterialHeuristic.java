package chess.heuristics;

import chess.*;
import chess.pieces.*;

import java.util.*;

import static chess.pieces.Piece.Color.*;

public class MaterialHeuristic implements Heuristic {
    public double calculateValue(Board board, Piece.Color color) {
        return (evaluate(board, color) - evaluate(board, oppositeColor(color)));
    }

    private double evaluate(Board board, Piece.Color color) {
        Set<Piece> pieces = board.getPieces(color);
        // weight by 0.05 (arbitrary) as number of points matter more
        double numPiecesAdv = pieces.size() * 0.05;
        // weight by 1.5 as this is important
        double materialAdv = pieces.stream().mapToDouble(Piece::getValue).sum() * 1.5;
        return numPiecesAdv + materialAdv;
    }
}
