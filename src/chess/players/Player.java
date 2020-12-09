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
        String color = move.getStart().getPiece().getColor() == Piece.Color.WHITE ? Board.ANSI_BLUE
                : Board.ANSI_BLACK;
        System.out.print(move + color + " (");
        if (move.isCastleMove()) {
            System.out.print((move.toString().contains("0-0-0") ? "Queen" : "King") +
                    "side castle)");
        } else {
            System.out.print(move.getStart().notation() + " -> " + move.getEnd().notation() + ")");
        }
        System.out.println(Board.ANSI_RESET);
        board.doMove(move, true);
    }
}
