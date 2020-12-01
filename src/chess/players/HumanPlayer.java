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
        return new Move("");
    }
}
