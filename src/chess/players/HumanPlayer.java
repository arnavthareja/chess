package chess.players;

import chess.*;
import chess.pieces.*;

import java.util.*;

/**
 * A human chess player.
 */
public final class HumanPlayer extends Player {
    /**
     * Constructs a human player with the given board and color.
     *
     * @param board  the board this player plays on
     * @param color  the color of this player
     */
    public HumanPlayer(Board board, Piece.Color color) {
        super(board, color);
    }

    /**
     * Prompts the human player to select the next move and returns the selected move.
     *
     * @return the move that this human player selected to make
     */
    public Move getMove() {
        Scanner in = new Scanner(System.in);
        Square start = null;
        Move m = null;
        while (start == null) {
            System.out.println("The valid moves for you are: ");
            for (Move move : getPossibleMoves()) {
                System.out.print(move + " ");
            }
            System.out.println();
            System.out.println("Enter the square of the piece you would like to move: ");
            start = board.squareAt(in.nextLine());
            if (start == null || start.isEmpty() || start.getPiece().getColor() != color) {
                System.out.printf(Board.ANSI_RED + "Invalid square%n%n" + Board.ANSI_RESET);
                start = null;
                continue;
            }
            Piece p = start.getPiece();
            while (m == null) {
                Set<Move> possibleMoves = p.getPossibleMoves();
                if (possibleMoves.isEmpty()) {
                    System.out.printf(Board.ANSI_RED + "Invalid square%n%n" + Board.ANSI_RESET);
                    start = null;
                    break;
                }
                System.out.println("Valid moves for this piece are: ");
                for (Move move : possibleMoves) {
                    System.out.print(move + " ");
                }
                System.out.println();
                System.out.println("Enter the square you would like to move to: ");
                Square end = board.squareAt(in.nextLine());
                if (end != null) {
                    if (possibleMoves.stream().anyMatch(move -> move.getEnd().equals(end))) {
                        if (start.getPiece() instanceof King &&
                                Math.abs(end.getCol() - start.getCol()) > 1) {
                            // Castle move
                            Square rookStart;
                            Square rookEnd;
                            if (end.getCol() - start.getCol() > 0) {
                                rookStart = board.squareAt(start.getRow(), 7);
                                rookEnd = board.squareAt(start.getRow(), 5);
                            } else {
                                rookStart = board.squareAt(start.getRow(), 0);
                                rookEnd = board.squareAt(start.getRow(), 3);
                            }
                            m = new Move(start, end, rookStart, rookEnd);
                        } else {
                            m = new Move(start, end);
                        }
                    }
                }
                if (m == null) {
                    System.out.printf(Board.ANSI_RED + "Invalid move%n%n" + Board.ANSI_RESET);
                }
            }
        }
        return m;
    }

    /**
     * Returns a string representation of this player.
     *
     * @return a string representation of this player
     */
    @Override
    public String toString() {
        return Board.ANSI_GREEN + "Human Player" + Board.ANSI_RESET;
    }
}
