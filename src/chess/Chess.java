package chess;

import chess.heuristics.*;
import chess.pieces.*;
import chess.players.*;

public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
//        Player p1 = new HumanPlayer(board, Piece.Color.WHITE);
//        Player p1 = new RandomPlayer(board, Piece.Color.WHITE, 0);
        Player p1 = new MinimaxPlayer(board, Piece.Color.WHITE, new CombinationHeuristic());
        Player p2 = new MinimaxPlayer(board, Piece.Color.BLACK, new CombinationHeuristic());
        Player currentPlayer = p1;
        while(!p1.inCheckmate() && !p1.inStalemate() && !p2.inCheckmate()) {
            System.out.println(board);
            if (p1.inCheck()) {
                System.out.print("White in check, ");
            }
            if (p2.inCheck()) {
                System.out.print("Black in check, ");
            }
            if (currentPlayer == p1) {
                System.out.print("White");
            } else {
                System.out.print("Black");
            }
            System.out.println(" to move");
            // The above 5 lines used to be the commented line below, but it changed behavior for whatever reason
            // System.out.println((currentPlayer == p1 ? "White" : "Black") + " to move");
            currentPlayer.doMove();
            currentPlayer = currentPlayer == p1 ? p2 : p1;
        }
        System.out.println(board);
        if (p1.inCheckmate()) {
            System.out.println("Black wins!");
        } else if (p2.inCheckmate()) {
            System.out.println("White wins!");
        } else {
            System.out.println("Stalemate!");
        }
    }
}
