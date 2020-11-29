package chess;

import chess.pieces.Piece;

public class Square {
    private int row;
    private int col;
    private Board board;
    private Piece piece;

    public Square(int row, int col, Board board) {
        this(row, col, board, null);
    }

    public Square(int row, int col, Board board, Piece piece) {
        if (row < 0 || row > 7) {
            throw new IllegalArgumentException("Row must be between 0 and 7");
        } else if (col < 0 || col > 7) {
            throw new IllegalArgumentException("Column must be between 0 and 7");
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

    public boolean equals(Square other) {
        return row == other.row && col == other.col && board == other.board && piece == other.piece;
    }

    public String toString() {
        return "" + (char) ('a' + col) + (8 - row);
    }
}
