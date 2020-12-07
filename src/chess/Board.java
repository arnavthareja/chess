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
        initializePieces(7, 6, WHITE);
        initializePieces(0, 1, BLACK);
        moves = new ArrayDeque<>();
    }

    private void initializePieces(int row, int pawnRow, Piece.Color color) {
        for (int i = 0; i < NUM_ROWS; i++) {
            new Pawn(squareAt(pawnRow, i), color);
        }
        new Rook(squareAt(row, 0), color);
        new Knight(squareAt(row, 1), color);
        new Bishop(squareAt(row, 2), color);
        new Queen(squareAt(row, 3), color);
        new King(squareAt(row, 4), color);
        new Bishop(squareAt(row, 5), color);
        new Knight(squareAt(row, 6), color);
        new Rook(squareAt(row, 7), color);
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
        return getPossibleMoves(color, true);
    }

    public Set<Move> getPossibleMoves(Piece.Color color, boolean considerCheck) {
        Set<Move> possibleMoves = new HashSet<>();
        for (Square[] squareArr : board) {
            for (Square s : squareArr) {
                Piece piece = s.getPiece();
                if (piece != null && piece.getColor() == color) {
                    possibleMoves.addAll(piece.getPossibleMoves(considerCheck));
                }
            }
        }
        return possibleMoves;
    }

    public Deque<Move> getAllMoves() {
        return moves;
    }

    public Square squareAt(String fromString) {
        if (fromString.length() != 2) {
            return null;
        }
        int col = fromString.charAt(0) - 'a';
        int row = 7 - (fromString.charAt(1) - '1');
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return null;
        }
        return squareAt(row, col);
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
        if (move.isCastleMove()) {
            move.getRookStart().getPiece().setAlreadyMoved(true);
            move.getRookStart().getPiece().setPosition(move.getRookEnd());
        }
    }

    public void undoLastMove() {
        Move move = moves.pop();
        move.getEnd().getPiece().setAlreadyMoved(move.getPieceAlreadyMoved());
        move.getEnd().getPiece().setPosition(move.getStart());
        if (move.isCaptureMove()) {
            move.getCapturedPiece().setPosition(move.getEnd());
        }
        if (move.isCastleMove()) {
            move.getRookEnd().getPiece().setAlreadyMoved(false);
            move.getRookEnd().getPiece().setPosition(move.getRookStart());
        }
    }

    public boolean inCheck(Piece.Color color) {
        for (Move move : getPossibleMoves(oppositeColor(color), false)) {
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
        boolean lastThreeMovesSame = false;
        if (moves.size() >= 6) {
            Move[] temp = new Move[6];
            for (int i = 5; i >= 0; i--) {
                temp[i] = moves.pop();
            }
            for (int i = 0; i < 6; i++) {
                moves.push(temp[i]);
            }
            lastThreeMovesSame = temp[0].equals(temp[2]) && temp[2].equals(temp[4]) && temp[1].equals(temp[3]) && temp[3].equals(temp[5]);
        }
        return lastThreeMovesSame || !inCheck(color) && getPossibleMoves(color).isEmpty() || (getPieces(WHITE).size() <= 1 && getPieces(BLACK).size() <= 1);
    }

    public void draw() {

    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Square[] squareArr : board) {
            // Uncomment if you want to add a background color to the chessboard
            // result.append(ANSI_RED_BACKGROUND);
            for (Square s : squareArr) {
                result.append(s.getPiece() == null ? ANSI_WHITE : s.getPiece().getColor() == WHITE ? ANSI_BLUE : ANSI_BLACK).append(s).append(" ");
            }
            result.append(ANSI_RESET).append("\n");
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        Board b = new Board();
//        System.out.println(b);
//        System.out.println("\nWhite moves:");
//        for (Move move : b.getPossibleMoves(WHITE)) {
//            System.out.print(move + " ");
//        }
//        System.out.println("\n" + b.getPossibleMoves(WHITE).size() + " total");
//        System.out.println("\nBlack moves:");
//        for (Move move : b.getPossibleMoves(BLACK)) {
//            System.out.print(move + " ");
//        }
//        System.out.println("\n" + b.getPossibleMoves(BLACK).size() + " total");
//    }
}
