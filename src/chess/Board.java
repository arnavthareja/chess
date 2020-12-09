package chess;

import chess.pieces.*;

import java.util.*;

import static chess.pieces.Piece.Color.*;

public class Board {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
        //initializePieces(7, 6, WHITE);
        //initializePieces(0, 1, BLACK);
        new Pawn(squareAt(1, 6), WHITE);
        new Pawn(squareAt(1, 7), WHITE);
        new Pawn(squareAt(6, 7), BLACK);
        new Pawn(squareAt(6, 6), BLACK);
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

    public Move getLastMove() {
        return moves.peek();
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
        doMove(move, false);
    }

    public void doMove(Move move, boolean realMove) {
        moves.push(move);
        if (move.isCaptureMove()) {
            move.getEnd().getPiece().capture();
        }
        move.getStart().getPiece().setAlreadyMoved(true);
        move.getStart().getPiece().setPosition(move.getEnd());
        if (move.isPromotion()) {
            Piece.Color color = move.getEnd().getPiece().getColor();
            move.getEnd().getPiece().capture();
            Piece promoted;
            if (!realMove) {
                promoted = new Queen(move.getEnd(), color);
            } else {
                promoted = promote(move, color);
            }
            promoted.setAlreadyMoved(true);
        }
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
        if (move.isPromotion()) {
            move.getPromotedPawn().setPosition(move.getStart());
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

    public boolean inStalemate() {
        boolean lastThreeMovesSame = false;
        if (moves.size() >= 6) {
            Move[] temp = new Move[6];
            Iterator<Move> iter = moves.descendingIterator();
            for (int i = 0; i < temp.length; i++) {
                temp[i] = iter.next();
            }
            lastThreeMovesSame = temp[0].sameAs(temp[2]) && temp[2].sameAs(temp[4])
                              && temp[1].sameAs(temp[3]) && temp[3].sameAs(temp[5]);
        }
        return (getPieces(WHITE).size() <= 1 && getPieces(BLACK).size() <= 1) || lastThreeMovesSame
                || inStalemate(WHITE) || inStalemate(BLACK);
    }

    private boolean inStalemate(Piece.Color color) {
        // TODO: Additional stalemate possibilities like last 3 moves repetitions of each other
        return !inCheck(color) && getPossibleMoves(color).isEmpty();
    }

    public boolean fewPiecesLeft() {
        Set<Piece> blackPieces = getPieces(BLACK);
        Set<Piece> whitePieces = getPieces(WHITE);
        return blackPieces.stream().filter(p -> !(p instanceof Pawn)).count() < 4 ||
                whitePieces.stream().filter(p -> !(p instanceof Pawn)).count() < 4;
    }

    private Piece promote(Move move, Piece.Color color) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nPlease select your new Piece:");
            System.out.println(Board.ANSI_GREEN + "1: Queen\n"
                    + Board.ANSI_CYAN + "2: Rook\n"
                    + Board.ANSI_PURPLE + "3: Bishop\n"
                    + Board.ANSI_BLUE + "4: Knight"
                    + Board.ANSI_RESET);
            System.out.println("\nType in the number or piece name and press enter to select.");
            String piece = in.nextLine().trim().toLowerCase();
            switch (piece) {
                case "1":
                case "queen":
                    return new Queen(move.getEnd(), color);
                case "2":
                case "rook":
                case "castle":
                    return new Rook(move.getEnd(), color);
                case "3":
                case "bishop":
                    return new Bishop(move.getEnd(), color);
                case "4":
                case "knight":
                case "horse":
                    return new Knight(move.getEnd(), color);
                default:
                    System.out.print(Board.ANSI_RED + "\nInvalid Piece type. Please try again\n"
                                       + Board.ANSI_RESET);
            }
        }
    }

    public String stateString() {
        StringBuilder result = new StringBuilder();
        for (Square[] squareArr : board) {
            for (Square s : squareArr) {
                if (s.isEmpty()) {
                    result.append("-");
                } else {
                    Piece p = s.getPiece();
                    result.append(p.getColor().toString().charAt(0));
                    result.append(p instanceof Pawn ? "P" : p);
                    if ((p instanceof Rook || p instanceof King) && !p.getAlreadyMoved()) {
                        result.append("E");
                    }
                }
            }
        }
        return result.toString();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Square s : board[0]) {
            result.append("   ").append(ANSI_RED).append((char) ('a' + s.getCol()));
        }
        for (int i = 0; i < board.length; i++) {
            // Uncomment if you want to add a background color to the chessboard
            // result.append(ANSI_RED_BACKGROUND);
            result.append("\n").append(ANSI_RED).append(8 - i).append(" ");
            for (Square s : board[i]) {
                result.append(s.getPiece() == null ? ANSI_WHITE : s.getPiece().getColor() == WHITE
                        ? ANSI_BLUE : ANSI_BLACK).append(s).append(" ");
            }
            result.append(ANSI_RESET);
        }
        return result.append("\n").toString();
    }
}
