package chess;

import chess.pieces.*;
import static chess.pieces.Piece.Color.*;
import java.util.*;

public class Board {
    // Constants taken from https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final int NUM_ROWS = 8;

    private final Square[][] board;
    private final Deque<Move> moves;

    public Board() {
        board = new Square[NUM_ROWS][NUM_ROWS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                board[i][j] = new Square(i, j, this);
            }
        }
        // Initialize white pawns
        for (int i = 0; i < NUM_ROWS; i++) {
            new Pawn(squareAt(6, i), WHITE);
        }
        //Initialize other white pieces
        new Rook(squareAt(7, 0), WHITE);
        new Knight(squareAt(7, 1), WHITE);
        new Bishop(squareAt(7, 2), WHITE);
        new Queen(squareAt(7, 3), WHITE);
        new King(squareAt(7, 4), WHITE);
        new Bishop(squareAt(7, 5), WHITE);
        new Knight(squareAt(7, 6), WHITE);
        new Rook(squareAt(7, 7), WHITE);
        // Initialize black pawns
        for (int i = 0; i < NUM_ROWS; i++) {
            new Pawn(squareAt(1, i), BLACK);
        }
        // Initialize other white pieces
        new Rook(squareAt(0, 0), BLACK);
        new Knight(squareAt(0, 1), BLACK);
        new Bishop(squareAt(0, 2), BLACK);
        new Queen(squareAt(0, 3), BLACK);
        new King(squareAt(0, 4), BLACK);
        new Bishop(squareAt(0, 5), BLACK);
        new Knight(squareAt(0, 6), BLACK);
        new Rook(squareAt(0, 7), BLACK);
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
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Row and column must be between 0 and 7");
        }
        return board[row][col];
    }

    public void doMove(Move move) {
        moves.push(move);
        if (move.isCaptureMove()) {
            move.getEnd().getPiece().capture();
        }
        move.getStart().getPiece().setAlreadyMoved(true);
        move.getStart().getPiece().setPosition(move.getEnd());
    }

    public void undoLastMove() {
        Move move = moves.pop();
        move.getEnd().getPiece().setAlreadyMoved(move.getPieceAlreadyMoved());
        move.getEnd().getPiece().setPosition(move.getStart());
        if (move.isCaptureMove()) {
            move.getCapturedPiece().setPosition(move.getEnd());
        }
    }

    public Move getLastMove() {
        return moves.peek();
    }

    public boolean inCheck(Piece.Color color) {
        for (Move move : getPossibleMoves(oppositeColor(color))) {
            if (move.getEnd().getPiece() instanceof King) {
                return true;
            }
        }
        return false;
    }

    public boolean inCheckmate(Piece.Color color) {
        return inCheck(color) && getPossibleMoves(color).isEmpty();
    }

    public boolean inStalemate(Piece.Color color) {
        // TODO: Additional stalemate possibilities like last 3 moves repetitions of each other
        return !inCheck(color) && getPossibleMoves(color).isEmpty();
    }

    public void draw() {

    }

    public String toString() {
        String result = "";
        for (Square[] squareArr : board) {
            // Uncomment if you want to add a background color to the chessboard
            // result += ANSI_RED_BACKGROUND;
            for (Square s : squareArr) {
                result += (s.getPiece() == null ? ANSI_WHITE : s.getPiece().getColor() == WHITE ? ANSI_BLUE : ANSI_BLACK) + s + " ";
            }
            result += ANSI_RESET + "\n";
        }
        return result;
    }

    public static void main(String[] args) {
        Board b = new Board();
        System.out.println(b);
        System.out.println("\nWhite moves:");
        for (Move move : b.getPossibleMoves(WHITE)) {
            System.out.print(move + " ");
        }
        System.out.println("\n" + b.getPossibleMoves(WHITE).size() + " total");
        System.out.println("\nBlack moves:");
        for (Move move : b.getPossibleMoves(BLACK)) {
            System.out.print(move + " ");
        }
        System.out.println("\n" + b.getPossibleMoves(BLACK).size() + " total");
    }
}
