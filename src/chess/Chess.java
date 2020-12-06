package chess;

import chess.heuristics.*;
import chess.pieces.*;
import chess.players.*;

public class Chess {
    public static void main(String[] args) {
        Board b = new Board();
        Player p1 = new HumanPlayer(b, Piece.Color.WHITE);
        Player p2 = new MinimaxPlayer(b, Piece.Color.BLACK, new MaterialHeuristic());
    }
}
