package chess;

public class Board {
    public static final int NUM_ROWS = 8;

    private Square[][] board;

    public Board() {
        board = new Square[NUM_ROWS][NUM_ROWS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                board[i][j] = new Square(i, j, this);
            }
        }
    }

    public Square squareAt(int row, int col) {
        return board[row][col];
    }

    public void draw() {
        return;
    }
}
