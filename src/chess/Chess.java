package chess;

import chess.heuristics.*;
import chess.pieces.*;
import chess.players.*;

public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        //Player p1 = new HumanPlayer(board, Piece.Color.WHITE);
        //Player p1 = new RandomPlayer(board, Piece.Color.WHITE, 0);
        Player p1 = new MinimaxPlayer(board, Piece.Color.WHITE, new CombinationHeuristic(), 1);
        Player p2 = new MinimaxPlayer(board, Piece.Color.BLACK, new CombinationHeuristic());
        Player currentPlayer = p1;
        while(!p1.inCheckmate() && !p1.inStalemate() && !p2.inCheckmate() && !p2.inStalemate()) {
            System.out.println(board);
            currentPlayer.doMove();
            currentPlayer = currentPlayer == p1 ? p2 : p1;
        }
        if (p1.inCheckmate()) {
            System.out.println("Player 2 wins!");
        } else if (p2.inCheckmate()) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Stalemate!");
        }
    }
}
