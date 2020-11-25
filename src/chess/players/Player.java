package chess.players;
import java.util.*;

import chess.*;
import chess.pieces.*;

public abstract class Player {
    private Board board;
    private Piece.Color color;

    public Player(Board board, Piece.Color color) {
        this.board = board;
        this.color = color;
    }

    public abstract void move(Move move);
    public abstract Set<Piece> getPieces();
}
