package chess;

import chess.pieces.*;
import java.util.*;

public class Square {
    private final int row;
    private final int col;
    private final Board board;
    private Piece piece;

    public Square(int row, int col, Board board) {
        this(row, col, board, null);
    }

    public Square(int row, int col, Board board, Piece piece) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Row and column must be between 0 and 7");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board must not be null");
        }
        this.row = row;
        this.col = col;
        this.board = board;
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public void draw() {

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece newPiece) {
        piece = newPiece;
    }

    public String notation() {
        return "" + (char) ('a' + col) + (8 - row);
    }

    public String toString() {
        return "" + (isEmpty() ? " " + notation() : (piece instanceof Pawn ? " " : piece) + notation());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row && col == square.col && board.equals(square.board) && Objects.equals(piece, square.piece);
    }

    @Override
    public int hashCode() {
        // Hashing piece here leads to StackOverflowError
        return Objects.hash(row, col, board);
    }
}
