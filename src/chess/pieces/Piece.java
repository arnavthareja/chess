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

    private Set<Move> getStraightMoves() {
        Set<Move> possibleMoves = getPossibleMoves(position, 1, 0);
        possibleMoves.addAll(getPossibleMoves(position, -1, 0));
        possibleMoves.addAll(getPossibleMoves(position, 0, 1));
        possibleMoves.addAll(getPossibleMoves(position, 0, -1));
        return possibleMoves;
    }

    private Set<Move> getDiagonalMoves() {
        Set<Move> possibleMoves = getPossibleMoves(position, 1, 1); 
        possibleMoves.addAll(getPossibleMoves(position, 1, -1));
        possibleMoves.addAll(getPossibleMoves(position, -1, -1));
        possibleMoves.addAll(getPossibleMoves(position, -1, 1));
        return possibleMoves;
    }

    private Set<Move> getPossibleMoves(Square currentPosition, int num1, int num2) {
        Set<Move> possibleMoves = new HashSet<>();
        try {
            Square finalPosition = new Square(currentPosition.getRow() + num1,
                    currentPosition.getCol() + num2, currentPosition.getBoard());
            if (finalPosition.isEmpty()) {
                possibleMoves = getPossibleMoves(finalPosition, num1, num2);
                possibleMoves.add(new Move(position, finalPosition));
            } else if (!finalPosition.getPiece().getColor().equals(getColor())) {
                possibleMoves.add(new Move(position, finalPosition));
            }
        } catch (IllegalArgumentException e) {}
    }
}
