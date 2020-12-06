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
        while (start == null || start.isEmpty() || start.getPiece().getColor() != color) {
            System.out.println("The valid moves for you are: ");
            for (Move move : getPossibleMoves()) {
                System.out.print(move + " ");
            }
            System.out.println();
            System.out.println("Enter the square of the piece you would like to move: ");
            start = board.squareAt(in.nextLine());
            if (start == null || start.isEmpty() || start.getPiece().getColor() != color) {
                System.out.println("Invalid square");
            }
        }
        Piece p = start.getPiece();
        Move m = null;
        while (m == null) {
            System.out.println("Valid moves for this piece are: ");
            Set<Move> possibleMoves = p.getPossibleMoves();
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
                System.out.println("Invalid move");
            }
        }
        return m;
    }
}
