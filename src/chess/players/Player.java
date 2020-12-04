package chess.players;

import chess.*;
import chess.pieces.*;
import java.util.*;

public abstract class Player {
    protected final Board board;
    protected final Piece.Color color;

    public Player(Board board, Piece.Color color) {
        this.board = board;
        this.color = color;
    }

    public abstract Move getMove();

    public Set<Piece> getPieces() {
        return board.getPieces(color);
    }

    public Set<Move> getPossibleMoves() {
        // Could also iterate over every piece in getPieces and add all possible moves into one set
        // ^ Would have to account for check
        return board.getPossibleMoves(color);
    }

    public void doMove(Move move) {
        board.doMove(move);
    }
}
