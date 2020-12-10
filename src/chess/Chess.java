package chess;

import chess.heuristics.*;
import chess.pieces.*;
import chess.players.*;

import java.util.*;

/**
 * Playing logic for a game of chess.
 */
public final class Chess {
    /**
     * Plays a game of chess between two players.
     *
     * @param args  command line arguments, not used
     */
    public static void main(String[] args) {
        Board board = new Board();
        Player p1 = selectPlayer(board, Piece.Color.WHITE, 1);
        Player p2 = selectPlayer(board, Piece.Color.BLACK, 2);
        System.out.println("\n" + p1 + " vs. " + p2 + "\n");
        Player currentPlayer = p1;
        while(!p1.inCheckmate() && !p1.inStalemate() && !p2.inCheckmate()) {
            System.out.println(board);
            if (p1.inCheck()) {
                System.out.print(Board.ANSI_BLUE + "White" + Board.ANSI_RESET + " in check, ");
            }
            if (p2.inCheck()) {
                System.out.print(Board.ANSI_BLACK + "Black" + Board.ANSI_RESET + " in check, ");
            }
            if (currentPlayer == p1) {
                System.out.print(Board.ANSI_BLUE + "White" + Board.ANSI_RESET);
            } else {
                System.out.print(Board.ANSI_BLACK + "Black" + Board.ANSI_RESET);
            }
            System.out.println(" to move");
            currentPlayer.doMove();
            currentPlayer = currentPlayer == p1 ? p2 : p1;
        }
        System.out.println(board);
        if (p1.inCheckmate()) {
            System.out.println(Board.ANSI_BLACK + "Black" + Board.ANSI_RESET + " wins!\n");
        } else if (p2.inCheckmate()) {
            System.out.println(Board.ANSI_BLUE + "White" + Board.ANSI_RESET + " wins!\n");
        } else {
            System.out.println("Stalemate!\n");
        }
        System.out.println("Display summary of all moves? (Yes/No)");
        Scanner in = new Scanner(System.in);
        String showSummary = in.nextLine().trim().toLowerCase();
        if (showSummary.equals("yes") || showSummary.equals("y")) {
            Deque<Move> allMoves = board.getAllMoves();
            Iterator<Move> iter = allMoves.descendingIterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        }
    }

    /**
     * Prompts the user to select a player type out of human and computer players.
     *
     * @param  board  the board to associate the player with
     * @param  color  the color of the player
     * @param  count  the number of the player (1 or 2)
     * @return        the player with the user-selected type and specified
     *                board and color.
     */
    private static Player selectPlayer(Board board, Piece.Color color, int count) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\nPlease select Player " + count + ":");
            System.out.println(Board.ANSI_GREEN + "1: Human\n"
                    + Board.ANSI_CYAN + "2: Computer (Easy)\n"
                    + Board.ANSI_PURPLE + "3: Computer (Medium)\n"
                    + Board.ANSI_BLUE + "4: Computer (Hard)\n"
                    + Board.ANSI_RED + "5: Computer (Extreme) (Will take a long time to move)\n"
                    + Board.ANSI_YELLOW + "6: Computer (Random) (Will select moves randomly)"
                    + Board.ANSI_RESET);
            System.out.println("\nType in the number or player name and press enter to select.");
            String player = in.nextLine().trim().toLowerCase();
            switch (player) {
                case "1":
                case "human":
                    return new HumanPlayer(board, color);
                case "2":
                case "easy":
                case "computer (easy)":
                case "computer easy":
                    return new WorstMinimaxPlayer(board, color, new CombinationHeuristic());
                case "3":
                case "medium":
                case "computer (medium)":
                case "computer medium":
                    return new SuboptimalMinimaxPlayer(board, color, new CombinationHeuristic());
                case "4":
                case "hard":
                case "computer (hard)":
                case "computer hard":
                    return new MinimaxPlayer(board, color, new CombinationHeuristic());
                case "5":
                case "extreme":
                case "computer (extreme)":
                case "computer extreme":
                    return new MinimaxPlayer(board, color, new CombinationHeuristic(),
                                             MinimaxPlayer.EXTREME_DIFFICULTY_SEARCH_DEPTH);
                case "6":
                case "random":
                case "computer (random)":
                case "computer random":
                    return new RandomPlayer(board, color);
                default:
                    System.out.print(Board.ANSI_RED + "\nInvalid Player type. Please try again\n"
                                       + Board.ANSI_RESET);
            }
        }
    }
}
