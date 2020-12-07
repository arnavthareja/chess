package chess.heuristics;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import java.util.*;

public class MaterialHeuristic implements Heuristic {
    public double calculateValue(Board board, Piece.Color color) {
        return (evaluate(board, color) - evaluate(board, oppositeColor(color)));// * color.getMultiplier();
    }

    private double evaluate(Board board, Piece.Color color) {
        Set<Piece> pieces = board.getPieces(color);
        double numPiecesAdv = pieces.size() * 0.1; // weight by 0.1 (arbitrary) as number of points matter more
        double materialAdv = pieces.stream().mapToDouble(Piece::getValue).sum();
        double numMovesAdv = board.getPossibleMoves(color).size() * 0.1; // weight mobility at 0.1 (arbitrary) as it may lead to aggressive playing
        return numPiecesAdv + materialAdv + numMovesAdv;
    }
}
