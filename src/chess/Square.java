package chess;

import chess.pieces.*;

import java.util.*;

/**
 * A square on a chessboard.
 */
public final class Square {
    /**
     * The row this square is located at.
     */
    private final int row;

    /**
     * The column this square is located at.
     */
    private final int col;

    /**
     * The board this square is on.
     */
    private final Board board;

    /**
     * The piece that occupies this square, null if this square is empty.
     */
    private Piece piece;

    /**
     * Constructs a new square on the given board at the given row and column.
     *
     * @param row    the row this square is located at
     * @param col    the column this square is located at
     * @param board  the board this square is on
     */
    public Square(int row, int col, Board board) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Row and column must be between 0 and 7");
        }
        if (board == null) {
            throw new IllegalArgumentException("Board must not be null");
        }
        this.row = row;
        this.col = col;
        this.board = board;
        this.piece = null;
    }

    /**
     * Returns true if no piece occupies this square, false otherwise.
     *
     * @return true if no piece occupies this square, false otherwise
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Returns the row this square is located at.
     *
     * @return the row this square is located at
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column this square is located at.
     *
     * @return the column this square is located at
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the board this square is on.
     *
     * @return the board this square is on
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the piece that occupies this square, null if this square is empty.
     *
     * @return the piece that occupies this square, null if this square is empty
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets the piece that occupies this square to the given piece.
     *
     * @param newPiece  the new piece to occupy this square
     */
    public void setPiece(Piece newPiece) {
        piece = newPiece;
    }

    /**
     * Returns the notation of this square in algebraic notation.
     *
     * @return the notation of this square in algebraic notation
     */
    public String notation() {
        return "" + (char) ('a' + col) + (8 - row);
    }

    /**
     * Returns a string representation of this square.
     *
     * @return a string representation of this square
     */
    public String toString() {
        return (isEmpty() ? " " : (piece instanceof Pawn ? " " : piece)) + notation();
    }

    /**
     * Returns true if this square equals the other object, false otherwise.
     *
     * @param  o  the object to determine equality to
     * @return    true if this square equals the other object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Square square = (Square) o;
        return row == square.row && col == square.col && board.equals(square.board) &&
                Objects.equals(piece, square.piece);
    }

    /**
     * Returns a hash code representation of this square.
     *
     * @return a hash code representation of this square
     */
    @Override
    public int hashCode() {
        // Hashing piece here leads to StackOverflowError
        // (infinite recursion, as piece hashes its square)
        return Objects.hash(row, col, board);
    }
}
