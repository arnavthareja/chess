package chess.players;

import chess.*;
import chess.pieces.*;
import java.util.*;

public class HumanPlayer extends Player {
    public HumanPlayer(Board board, Piece.Color color) {
        super(board, color);
    }

    // TODO: Implement
    public Move getMove() {
        Scanner in = new Scanner(System.in);
        // TODO: Maybe use this commented logic to get the full move instead of getting start and end squares
        // Compare input string to each Move.toString() to see if possibleMoves contains the move
//        Move m = null;
//        while (m == null) {
//            System.out.println("The valid moves for you are: ");
//            for (Move move : getPossibleMoves()) {
//                System.out.print(move + " ");
//            }
//            System.out.println();
//            System.out.println("Enter the square of the piece you would like to move: ");
//            String s = in.nextLine();
//        }
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
                    m = new Move(start, end);
                    if (!possibleMoves.contains(m)) {
                        m = null;
                    }
                }
                if (m == null) {
                    System.out.printf(Board.ANSI_RED + "Invalid move%n%n" + Board.ANSI_RESET);
                }
            }
        }
        return m;
    }

    public String toString() {
        return "Human Player";
    }
}
