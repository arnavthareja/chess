package chess;

public class Board {
    public static final int NUM_ROWS = 8;

    private Square[][] board;

    public Board() {
        board = new Square[NUM_ROWS][NUM_ROWS];
    }

    public void setSquare(int row, int col, Square newSquare) {
        board[row][col] = newSquare;
    }

    public void draw() {
        return;
    }
}
