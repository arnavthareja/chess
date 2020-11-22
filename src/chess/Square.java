package chess;

import chess.pieces.Piece;

public class Square {
    private int row;
    private int col;
    private Piece piece;

    public Square(int row, int col) {
        this(row, col, null);
    }

    public Square(int row, int col, Piece piece) {
        if (row < 0 || row > 7) {
            throw new IllegalArgumentException("Row must be between 0 and 7");
        } else if (col < 0 || col > 7) {
            throw new IllegalArgumentException("Column must be between 0 and 7");
        }
        this.row = row;
        this.col = col;
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

    public String toString() {
        return "" + (char) ('a' + col) + (8 - row);
    }
}
