package chess.heuristics;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import java.util.*;

public class MaterialHeuristic implements Heuristic {
    public double calculateValue(Board board, Piece.Color color) {
        Set<Piece> pieces = board.getPieces(color);
        Set<Piece> opposingPieces = board.getPieces(oppositeColor(color));
        double numPiecesAdv = pieces.size() - opposingPieces.size();
        double materialAdv = pieces.stream().mapToDouble(Piece::getValue).sum() -
                             opposingPieces.stream().mapToDouble(Piece::getValue).sum();
        return numPiecesAdv + materialAdv;
    }
}
