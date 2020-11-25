package chess.pieces;

import chess.*;
import java.util.*;

public abstract class Piece {
    private Board board;
    private String notation;
    protected Square position;
    protected Color color;
    protected double value;
    protected boolean alreadyMoved;

    public Piece(Board board, Square position, Color color, double value, String notation) {
        this.board = board;
        this.position = position;
        this.color = color;
        this.value = value;
        this.notation = notation;
        alreadyMoved = false;
    }

    public abstract Set<Square> getPossibleMoves();
    public abstract void draw();

    public double getValue() {
        return value;
    }

    public Square getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    // For castling or pawn's first move
    public boolean hasAlreadyMoved() {
        return alreadyMoved;
    }

    public String toString() {
        return notation + position;
    }

    public static enum Color {
        BLACK, WHITE
    }
}
