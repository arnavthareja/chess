package chess.pieces;

import chess.*;
import java.util.*;

public abstract class Piece {
    private final String notation;
    protected Square position;
    protected final Color color;
    protected final int value;
    protected boolean alreadyMoved;

    public Piece(Square position, Color color, int value, String notation) {
        this.position = position;
        this.color = color;
        this.value = value;
        this.notation = notation;
        alreadyMoved = false;
    }

    public abstract Set<Move> getPossibleMoves();
    public abstract void draw();

    public int getValue() {
        return value;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square newPosition) {
        if (position != null) {
            position.setPiece(null);
        }
        if (newPosition != null) {
            newPosition.setPiece(this);
        }
        position = newPosition;
    }

    public void capture() {
        setPosition(null);
    }

    public Color getColor() {
        return color;
    }

    public String toString() {
        return notation;
    }

    public enum Color {
        WHITE, BLACK
    }
}
