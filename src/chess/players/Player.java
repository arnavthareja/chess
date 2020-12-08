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

    public Piece.Color getColor() {
        return color;
    }

    public Set<Move> getPossibleMoves() {
        return board.getPossibleMoves(color);
    }

    public boolean inCheck() {
        return board.inCheck(color);
    }

    public boolean inCheckmate() {
        return board.inCheckmate(color);
    }

    public boolean inStalemate() {
        return board.inStalemate();
    }

    public void doMove() {
        doMove(getMove());
    }

    private void doMove(Move move) {
        System.out.println(move);
        board.doMove(move);
    }

    public abstract String toString();
}
