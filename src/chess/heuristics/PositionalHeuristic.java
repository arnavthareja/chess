package chess.heuristics;

import chess.*;
import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import java.util.*;
import static java.util.Map.entry;

public class PositionalHeuristic implements Heuristic {
    // Piece evaluation tables taken from https://www.chessprogramming.org/Simplified_Evaluation_Function
    private static final double[][] whitePawnTable = {
            {100, 100, 100, 100, 100, 100, 100, 100},
            {50, 50, 50, 50, 50, 50, 50, 50},
            {10, 10, 20, 30, 30, 20, 10, 10},
            {5,  5, 10, 25, 25, 10,  5,  5},
            {0,  0,  0, 20, 20,  0,  0,  0},
            {5, -5,-10,  0,  0,-10, -5,  5},
            {5, 10, 10,-20,-20, 10, 10,  5},
            {0,  0,  0,  0,  0,  0,  0,  0}
    };

    private static final double[][] whiteKnightTable = {
            {-50,-40,-30,-30,-30,-30,-40,-50},
            {-40,-20,  0,  0,  0,  0,-20,-40},
            {-30,  0, 10, 15, 15, 10,  0,-30},
            {-30,  5, 15, 20, 20, 15,  5,-30},
            {-30,  0, 15, 20, 20, 15,  0,-30},
            {-30,  5, 10, 15, 15, 10,  5,-30},
            {-40,-20,  0,  5,  5,  0,-20,-40},
            {-50,-40,-30,-30,-30,-30,-40,-50}
    };

    private static final double[][] whiteBishopTable = {
            {-20,-10,-10,-10,-10,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5, 10, 10,  5,  0,-10},
            {-10,  5,  5, 10, 10,  5,  5,-10},
            {-10,  0, 10, 10, 10, 10,  0,-10},
            {-10, 10, 10, 10, 10, 10, 10,-10},
            {-10,  5,  0,  0,  0,  0,  5,-10},
            {-20,-10,-10,-10,-10,-10,-10,-20}
    };

    private static final double[][] whiteRookTable = {
            {0,  0,  0,  0,  0,  0,  0,  0},
            {5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {0,  0,  0,  5,  5,  0,  0,  0}
    };

    private static final double[][] whiteQueenTable = {
            {-20,-10,-10, -5, -5,-10,-10,-20},
            {-10,  0,  0,  0,  0,  0,  0,-10},
            {-10,  0,  5,  5,  5,  5,  0,-10},
            {-5,  0,  5,  5,  5,  5,  0, -5},
            {0,  0,  5,  5,  5,  5,  0, -5},
            {-10,  5,  5,  5,  5,  5,  0,-10},
            {-10,  0,  5,  0,  0,  0,  0,-10},
            {-20,-10,-10, -5, -5,-10,-10,-20}
    };

    private static final double[][] whiteKingTable = {
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-30,-40,-40,-50,-50,-40,-40,-30},
            {-20,-30,-30,-40,-40,-30,-30,-20},
            {-10,-20,-20,-20,-20,-20,-20,-10},
            {20, 20,  0,  0,  0,  0, 20, 20},
            {20, 30, 10,  0,  0, 10, 30, 20}
    };

    private static final double[][] whiteKingEndgameTable = {
            {-50,-40,-30,-20,-20,-30,-40,-50},
            {-30,-20,-10,  0,  0,-10,-20,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 30, 40, 40, 30,-10,-30},
            {-30,-10, 20, 30, 30, 20,-10,-30},
            {-30,-30,  0,  0,  0,  0,-30,-30},
            {-50,-30,-30,-30,-30,-30,-30,-50}
    };

    private static final double[][] blackPawnTable = flip(whitePawnTable);
    private static final double[][] blackKnightTable = flip(whiteKnightTable);
    private static final double[][] blackBishopTable = flip(whiteBishopTable);
    private static final double[][] blackRookTable = flip(whiteRookTable);
    private static final double[][] blackQueenTable = flip(whiteQueenTable);
    private static final double[][] blackKingTable = flip(whiteKingTable);
    private static final double[][] blackKingEndgameTable = flip(whiteKingEndgameTable);

    private static final Map<Class, double[][]> whiteEvalTables = Map.ofEntries(
            entry(Pawn.class, whitePawnTable),
            entry(Knight.class, whiteKnightTable),
            entry(Bishop.class, whiteBishopTable),
            entry(Rook.class, whiteRookTable),
            entry(Queen.class, whiteQueenTable),
            entry(King.class, whiteKingTable)
    );

    private static final Map<Class, double[][]> blackEvalTables = Map.ofEntries(
            entry(Pawn.class, blackPawnTable),
            entry(Knight.class, blackKnightTable),
            entry(Bishop.class, blackBishopTable),
            entry(Rook.class, blackRookTable),
            entry(Queen.class, blackQueenTable),
            entry(King.class, blackKingTable)
    );

    private static double[][] flip(double[][] table) {
        double[][] result = new double[table.length][table[0].length];
        for (int i = 0; i < table.length / 2; i++) {
            result[i] = table[table.length - 1 - i];
            result[table.length - 1 - i] = table[i];
        }
        return result;
    }

    public double calculateValue(Board board, Piece.Color color) {
        return (evaluate(board, color) - evaluate(board, oppositeColor(color)));// * color.getMultiplier();
    }

    private double evaluate(Board board, Piece.Color color) {
        return board.getPieces(color).stream().mapToDouble(PositionalHeuristic::value).sum() * 0.01 // remove weight, as tables are weighted by 100
               + (board.inCheck(oppositeColor(color)) ? inEndgame(board) ? 20 : 10 : 0);
    }

    private static double value(Piece piece) {
        int row = piece.getPosition().getRow();
        int col = piece.getPosition().getCol();
        if (piece.getColor() == WHITE) {
            if (piece instanceof King && inEndgame(piece.getPosition().getBoard())) {
                return whiteKingEndgameTable[row][col];
            }
            return whiteEvalTables.get(piece.getClass())[row][col];
        } else {
            if (piece instanceof King && inEndgame(piece.getPosition().getBoard())) {
                return blackKingEndgameTable[row][col];
            }
            return blackEvalTables.get(piece.getClass())[row][col];
        }
    }

    // TODO: Implement
    private static boolean inEndgame(Board board) {
        return board.getPieces(WHITE).stream().mapToDouble(Piece::getValue).sum() < 210 &&
                board.getPieces(BLACK).stream().mapToDouble(Piece::getValue).sum() < 210;
    }
}