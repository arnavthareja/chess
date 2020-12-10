package chess.heuristics;

import chess.*;
import chess.pieces.*;

import java.util.*;

import static chess.pieces.Piece.Color.*;

/**
 * A heuristic based on piece positions.
 */
public final class PositionalHeuristic implements Heuristic {
    /**
     * Evaluation tables for determining positional values of pieces.
     * Tables taken from https://www.chessprogramming.org/Simplified_Evaluation_Function
     */
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

    /**
     * Maps white pieces to their evaluation tables.
     */
    private static final Map<Class, double[][]> whiteEvalTables = Map.of(
            Pawn.class, whitePawnTable,
            Knight.class, whiteKnightTable,
            Bishop.class, whiteBishopTable,
            Rook.class, whiteRookTable,
            Queen.class, whiteQueenTable,
            King.class, whiteKingTable
    );

    /**
     * Maps black pieces to their evaluation tables.
     */
    private static final Map<Class, double[][]> blackEvalTables = Map.of(
            Pawn.class, blackPawnTable,
            Knight.class, blackKnightTable,
            Bishop.class, blackBishopTable,
            Rook.class, blackRookTable,
            Queen.class, blackQueenTable,
            King.class, blackKingTable
    );

    /**
     * Flips the given 2D array vertically.
     *
     * @param  table  the 2D array to flip
     * @return        the given array flipped vertically
     */
    private static double[][] flip(double[][] table) {
        double[][] result = new double[table.length][table[0].length];
        for (int i = 0; i < table.length / 2; i++) {
            result[i] = table[table.length - 1 - i];
            result[table.length - 1 - i] = table[i];
        }
        return result;
    }

    /**
     * Calculates the heuristic value for the given board and color.
     *
     * @param  board  the board for which to calculate the heuristic value
     * @param  color  the color for which to calculate the heuristic value
     * @return        the heuristic value for the given board and color
     */
    public double calculateValue(Board board, Piece.Color color) {
        return (evaluate(board, color) - evaluate(board, oppositeColor(color)));
    }

    /**
     * Calculates the heuristic value for the given board and color, only
     * considering pieces of the given color.
     *
     * @param  board  the board for which to calculate the heuristic value
     * @param  color  the color for which to calculate the heuristic value
     * @return        the heuristic value for the given board and color, only
     *                considering pieces of the given color
     */
    private double evaluate(Board board, Piece.Color color) {
        // weight by 0.02 (arbitrary) as position is more important
        double numMovesAdv = board.getPossibleMoves(color).size() * 0.02;
        // weight by 1.1 but multiply by 0.011 as tables are weighted by 100
        double positionAdv = board.getPieces(color).stream()
                .mapToDouble(PositionalHeuristic::value).sum() * 0.011;
        // Give a small bonus for check, larger bonuses cause bigger piece losses
        double checkAdv = board.inCheck(oppositeColor(color)) ? inEndgame(board) ? 6 : 3 : 0;
        return numMovesAdv + positionAdv + checkAdv;
    }

    /**
     * Returns the positional value of the given piece.
     *
     * @param  piece  the piece for which to calculate the positional value
     * @return        the positional value of the given piece
     */
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

    /**
     * Returns true if the given board is in the endgame, false otherwise. A
     * board is considered to be in the endgame if both players have pieces
     * with a total material value less than 10, excluding kings.
     *
     * @param  board  the board to determine endgame status of
     * @return        true if the given board is in the endgame, false otherwise
     */
    private static boolean inEndgame(Board board) {
        // In endgame if less both sides have less than 10 points of pieces left, not counting king
        return board.getPieces(WHITE).stream().mapToDouble(Piece::getValue).sum() < 210 &&
                board.getPieces(BLACK).stream().mapToDouble(Piece::getValue).sum() < 210;
    }
}