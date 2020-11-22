package chess.pieces;

import chess.*;
import java.util.*;

public abstract class Piece {
    private Board board;
    private String notation;
    protected Square position;
    protected Color color;
    protected double value;
    protected boolean moved;

    public Piece(Board board, Square position, Color color, double value, String notation) {
        this.board = board;
        this.position = position;
        this.color = color;
        this.value = value;
        this.notation = notation;
        moved = false;
    }

    public abstract Set<Square> getPossibleMoves();
    public abstract void draw();

    public void move(Square destination) {
        if (!getPossibleMoves().contains(destination)) {
            throw new IllegalArgumentException();
        }
        board.setSquare(position.getRow(), position.getCol(), null);
        position = destination;
        board.setSquare(position.getRow(), position.getCol(), position);
    }

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
        return moved;
    }

    public String toString() {
        return notation + position;
    }

    protected enum Color {
        BLACK, WHITE
    }
}
