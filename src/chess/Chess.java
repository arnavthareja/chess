package chess;

import chess.heuristics.*;
import chess.pieces.*;
import chess.players.*;
import java.util.*;

public class Chess {
    private static int count = 1;
    
    public static void main(String[] args) {
        Board board = new Board();
        Player p1 = selectPlayer(board);
        count++;
        Player p2 = selectPlayer(board);
        //Player p1 = new HumanPlayer(board, Piece.Color.WHITE);
        //Player p2 = new MinimaxPlayer(board, Piece.Color.BLACK, new CombinationHeuristic());
        System.out.println(p1 + " vs. " + p2);
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

    private static Player selectPlayer(Board board) {
        Scanner s = new Scanner(System.in);
        Player player;
        System.out.println("Please select Player " + count + ":");
        System.out.println("1: Human\n2: Computer (Easy)\n3: Computer (Medium)\n4: Computer (Hard)\n5: Computer (Impossible)");
        String response = s.next().trim();
        if (response.equals("1") || response.equalsIgnoreCase("human")) {
            player = new HumanPlayer(board, getColor());
        } else if (response.equals("2") || response.equalsIgnoreCase("easy")) {
            player = new WorstMinimaxPlayer(board, getColor(), new CombinationHeuristic());
        } else if (response.equals("3") || response.equalsIgnoreCase("medium")) {
            player = new SuboptimalMinimaxPlayer(board, getColor(), new CombinationHeuristic());
        } else if (response.equals(4) || response.equalsIgnoreCase("hard")) {
            player = new MinimaxPlayer(board, getColor(), new CombinationHeuristic());
        } else if (response.equals(5) || response.equalsIgnoreCase("impossible")) {
            player = new MinimaxPlayer(board, getColor(), new CombinationHeuristic(), 10);
        } else {
            System.out.println("\nInvalid Player type. Please try again");
            player = selectPlayer(board);
        }
        System.out.println();
        return player;
    }

    private static Piece.Color getColor() {
        if (count == 1) {
            return Piece.Color.WHITE;
        } else {
            return Piece.Color.BLACK;
        }
    }
}
