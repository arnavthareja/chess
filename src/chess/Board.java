package chess;

import chess.pieces.*;
import java.util.*;

public class Board {
    public static final int NUM_ROWS = 8;

    private final Square[][] board;
    private final Deque<Move> moves;

    public Board() {
        board = new Square[NUM_ROWS][NUM_ROWS];
        // TODO: Change this so that it initializes pieces in the correct starting positions
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                board[i][j] = new Square(i, j, this);
            }
        }
        moves = new ArrayDeque<>();
    }

    public Set<Piece> getPieces(Piece.Color color) {
        Set<Piece> pieces = new HashSet<>();
        for (Square[] squareArr : board) {
            for (Square s : squareArr) {
                Piece piece = s.getPiece();
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public Set<Move> getPossibleMoves(Piece.Color color) {
        Set<Move> possibleMoves = new HashSet<>();
        for (Square[] squareArr : board) {
            for (Square s : squareArr) {
                Piece piece = s.getPiece();
                if (piece != null && piece.getColor() == color) {
                    possibleMoves.addAll(piece.getPossibleMoves());
                }
            }
        }
        return possibleMoves;
    }

    public Square squareAt(int row, int col) {
        return board[row][col];
    }

    public void doMove(Move move) {
        moves.push(move);
        if (move.isCaptureMove()) {
            move.getEnd().getPiece().capture();
        }
        move.getStart().getPiece().setPosition(move.getEnd());
    }

    public void undoLastMove() {
        Move move = moves.pop();
        move.getEnd().getPiece().setPosition(move.getStart());
        if (move.isCaptureMove()) {
            move.getEnd().setPiece(move.getCapturedPiece());
        }
    }

    public void draw() {

    }
}
