package chess.pieces;

import chess.*;
import java.util.*;

public abstract class Piece {
    private final String notation;
    protected Square position;
    protected final Color color;
    protected final int value;
    protected boolean alreadyMoved;
    protected Set<Move> possibleMoves;

    public Piece(Square position, Color color, int value, String notation) {
        this.position = position;
        this.color = color;
        this.value = value;
        this.notation = notation;
        alreadyMoved = false;
        // Do I have to initialize possibleMoves in the constructor? It's not necessary for
        // everything to work properly but they may want us to for internal correctness or whatever
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

    public void getStraightMoves() {
        getPossibleMoves(position, 1, 0);
        getPossibleMoves(position, -1, 0);
        getPossibleMoves(position, 0, 1);
        getPossibleMoves(position, 0, -1);
    }

    public void getDiagonalMoves() {
        getPossibleMoves(position, 1, 1); 
        getPossibleMoves(position, 1, -1);
        getPossibleMoves(position, -1, -1);
        getPossibleMoves(position, -1, 1);
    }

    private void getPossibleMoves(Square currentPosition, int num1, int num2) {
        try {
            Square finalPosition = new Square(currentPosition.getRow() + num1,
                    currentPosition.getCol() + num2, currentPosition.getBoard());
            if (finalPosition.isEmpty()) {
                possibleMoves.add(new Move(position, finalPosition));
                getPossibleMoves(finalPosition, num1, num2);
            } else if (!finalPosition.getPiece().getColor().equals(getColor())) {
                possibleMoves.add(new Move(position, finalPosition));
            }
        } catch (IllegalArgumentException e) {}
    }
}
