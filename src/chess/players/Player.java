package chess.players;

import chess.*;
import chess.pieces.*;

import java.util.*;

/**
 * A player that plays chess.
 */
public abstract class Player {
    /**
     * The board this player plays on.
     */
    protected final Board board;

    /**
     * The color of this player.
     */
    protected final Piece.Color color;

    /**
     * Constructs a new player with the given board and color.
     *
     * @param board  the board this player plays on
     * @param color  the color of this player
     */
    public Player(Board board, Piece.Color color) {
        this.board = board;
        this.color = color;
    }

    /**
     * Returns the next move this player will make.
     *
     * @return the next move this player will make
     */
    public abstract Move getMove();

    /**
     * Returns a set containing all of this player's pieces that have not been captured.
     *
     * @return a set containing all of this player's pieces that have not been captured
     */
    public Set<Piece> getPieces() {
        return board.getPieces(color);
    }

    /**
     * Returns a set containing all the legal moves this player can make, with
     * the exception of en passant.
     *
     * @return a set containing all the legal moves this player can make, with
     *         the exception of en passant
     */
    public Set<Move> getPossibleMoves() {
        return board.getPossibleMoves(color);
    }

    /**
     * Returns true if this player is in check, false otherwise.
     *
     * @return true if this player is in check, false otherwise
     */
    public boolean inCheck() {
        return board.inCheck(color);
    }

    /**
     * Returns true if this player is in checkmate, false otherwise.
     *
     * @return true if this player is in checkmate, false otherwise
     */
    public boolean inCheckmate() {
        return board.inCheckmate(color);
    }

    /**
     * Returns true if there is a stalemate, false otherwise.
     *
     * @return true if there is a stalemate, false otherwise
     */
    public boolean inStalemate() {
        return board.inStalemate();
    }

    /**
     * Gets the next move this player will make then executes it.
     */
    public void doMove() {
        doMove(getMove());
    }

    /**
     * Prints and executes the give move.
     *
     * @param move  the move to execute
     */
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
        board.doMove(move, this instanceof HumanPlayer);
    }
}
