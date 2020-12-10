package chess;

import chess.pieces.*;

import java.util.*;

import static chess.pieces.Piece.Color.*;

/**
 * A standard 8x8 chessboard.
 */
public final class Board {
    /**
     * ANSI color escape codes for printing to the console in color. Taken from
     * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * The number of rows and columns on the chessboard.
     */
    public static final int NUM_ROWS = 8;

    /**
     * A 2D array of squares representing the actual board.
     */
    private final Square[][] board;

    /**
     * A deque storing every move that has been made so far.
     */
    private final Deque<Move> moves;

    /**
     * Constructs a new 8x8 chessboard with pieces in their starting positions.
     */
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

    /**
     * Initializes pieces to the correct starting positions on this board.
     *
     * @param row      the row at which to initialize non-pawn pieces
     * @param pawnRow  the row at which to initialize pawns
     * @param color    the color of the pieces to initialize
     */
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

    /**
     * Returns a set containing every piece of the given color on this board.
     *
     * @param  color  the color of the pieces to get
     * @return        a set containing  every piece of the given color on this board
     */
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

    /**
     * Returns a set containing all the legal moves the player of the given
     * color can make, with the exception of en passant.
     *
     * @param  color  the color for which to get legal moves
     * @return        a set containing all the legal moves the player of the
     *                given color can make, with the exception of en passant
     */
    public Set<Move> getPossibleMoves(Piece.Color color) {
        return getPossibleMoves(color, true);
    }

    /**
     * Returns a set containing all the moves the player of the given color can
     * make, with the exception of en passant. May return illegal moves if
     * onlyLegalMoves is false.
     *
     * @param  color           the color for which to get moves
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves the player of the
     *                         given color can make
     */
    public Set<Move> getPossibleMoves(Piece.Color color, boolean onlyLegalMoves) {
        Set<Move> possibleMoves = new HashSet<>();
        for (Square[] squareArr : board) {
            for (Square s : squareArr) {
                Piece piece = s.getPiece();
                if (piece != null && piece.getColor() == color) {
                    possibleMoves.addAll(piece.getPossibleMoves(onlyLegalMoves));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Returns a deque containing all the moves that have been made so far.
     *
     * @return a deque containing all the moves that have been made so far
     */
    public Deque<Move> getAllMoves() {
        return moves;
    }

    /**
     * Returns the last move that was made.
     *
     * @return the last move that was made.
     */
    public Move getLastMove() {
        return moves.peek();
    }

    /**
     * Returns the square on this board with the given notation.
     *
     * @param  notation  the notation of the square to get
     * @return           the square on this board with the given notation
     */
    public Square squareAt(String notation) {
        if (notation.length() != 2) {
            return null;
        }
        int col = notation.charAt(0) - 'a';
        int row = 7 - (notation.charAt(1) - '1');
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return null;
        }
        return squareAt(row, col);
    }

    /**
     * Returns the square on this board at the given row and column.
     *
     * @param  row  the row of the square to get
     * @param  col  the column of the square to get
     * @return      the square on this board at the given row and column
     * @throws IllegalArgumentException if row or column is out of bounds
     */
    public Square squareAt(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            throw new IllegalArgumentException("Row and column must be between 0 and 7");
        }
        return board[row][col];
    }

    /**
     * Executes the given move.
     *
     * @param move  the move to execute
     */
    public void doMove(Move move) {
        doMove(move, false);
    }

    /**
     * Executes the given move.
     *
     * @param move         the move to execute
     * @param humanPlayer  whether the player making the move is human or not
     */
    public void doMove(Move move, boolean humanPlayer) {
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
            if (humanPlayer) {
                promoted = promote(move.getEnd(), color);
            } else {
                promoted = new Queen(move.getEnd(), color);
            }
            promoted.setAlreadyMoved(true);
        }
        if (move.isCastleMove()) {
            move.getRookStart().getPiece().setAlreadyMoved(true);
            move.getRookStart().getPiece().setPosition(move.getRookEnd());
        }
    }

    /**
     * Undoes the last move made on this board.
     */
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

    /**
     * Returns true if the player of the given color is in check, false otherwise.
     *
     * @param  color  the color to evaluate if in check
     * @return        true if the player of the given color is in check, false otherwise
     */
    public boolean inCheck(Piece.Color color) {
        return getPossibleMoves(oppositeColor(color), false).stream()
                .anyMatch(m -> m.getEnd().getPiece() instanceof King);
    }

    /**
     * Returns true if the player of the given color is in checkmate, false otherwise.
     *
     * @param  color  the color to evaluate if in checkmate
     * @return        true if the player of the given color is in checkmate, false otherwise
     */
    public boolean inCheckmate(Piece.Color color) {
        return inCheck(color) && getPossibleMoves(color).isEmpty();
    }

    /**
     * Returns true if there is a stalemate, false otherwise.
     *
     * @return true if there is a stalemate, false otherwise
     */
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

    /**
     * Returns true if the player of the given color has no legal moves and is
     * not in checkmate, false otherwise.
     *
     * @param  color  the color to evaluate if in stalemate
     * @return        true if the player of the given color has no legal moves
     *                and is not in checkmate, false otherwise
     */
    private boolean inStalemate(Piece.Color color) {
        return !inCheck(color) && getPossibleMoves(color).isEmpty();
    }

    /**
     * Returns true if either color has less than 4 non-pawn pieces left, false otherwise.
     *
     * @return true if either color has less than 4 non-pawn pieces left, false otherwise.
     */
    public boolean fewPiecesLeft() {
        return getPieces(BLACK).stream().filter(p -> !(p instanceof Pawn)).count() < 4 ||
                getPieces(WHITE).stream().filter(p -> !(p instanceof Pawn)).count() < 4;
    }

    /**
     * Promotes a pawn to the player's piece of choice.
     *
     * @param  end    the square the promotion move ends on
     * @param  color  the color of the promoted pawn
     * @return        the promoted piece
     */
    private Piece promote(Square end, Piece.Color color) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nPlease select your new Piece:");
            System.out.println(ANSI_GREEN + "1: Queen\n"
                    + ANSI_CYAN + "2: Rook\n"
                    + ANSI_PURPLE + "3: Bishop\n"
                    + ANSI_BLUE + "4: Knight" + ANSI_RESET);
            System.out.println("\nType in the number or piece name and press enter to select.");
            String piece = in.nextLine().trim().toLowerCase();
            switch (piece) {
                case "1":
                case "queen":
                case "q":
                    return new Queen(end, color);
                case "2":
                case "rook":
                case "castle":
                case "r":
                    return new Rook(end, color);
                case "3":
                case "bishop":
                case "b":
                    return new Bishop(end, color);
                case "4":
                case "knight":
                case "horse":
                case "n":
                    return new Knight(end, color);
                default:
                    System.out.print(ANSI_RED + "\nInvalid Piece type. Please try again\n"
                                       + ANSI_RESET);
            }
        }
    }

    /**
     * Returns a string representing the current state of this board. More
     * detailed and shorter than toString().
     *
     * @return a string representing the current state of this board
     */
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

    /**
     * Returns a string representation of this board.
     *
     * @return a string representation of this board.
     */
    @Override
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
