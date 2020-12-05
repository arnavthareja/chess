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

    public boolean getAlreadyMoved() {
        return alreadyMoved;
    }

    public void setAlreadyMoved(boolean alreadyMoved) {
        this.alreadyMoved = alreadyMoved;
    }

    public String toString() {
        return notation;
    }

    protected Set<Move> getStraightMoves() {
        return getStraightMoves(Board.NUM_ROWS);
    }

    protected Set<Move> getStraightMoves(int maxDepth) {
        Set<Move> possibleMoves = getPossibleMoves(1, 0, maxDepth);
        possibleMoves.addAll(getPossibleMoves(-1, 0, maxDepth));
        possibleMoves.addAll(getPossibleMoves(0, 1, maxDepth));
        possibleMoves.addAll(getPossibleMoves(0, -1, maxDepth));
        return possibleMoves;
    }

    protected Set<Move> getDiagonalMoves() {
        return getDiagonalMoves(Board.NUM_ROWS);
    }

    protected Set<Move> getDiagonalMoves(int maxDepth) {
        Set<Move> possibleMoves = getPossibleMoves(1, 1, maxDepth);
        possibleMoves.addAll(getPossibleMoves(1, -1, maxDepth));
        possibleMoves.addAll(getPossibleMoves(-1, -1, maxDepth));
        possibleMoves.addAll(getPossibleMoves(-1, 1, maxDepth));
        return possibleMoves;
    }

    protected Set<Move> getPossibleMoves(int dx, int dy, int maxDepth) {
        return getPossibleMoves(position, dx, dy, maxDepth);
    }

    protected Set<Move> getPossibleMoves(Square currentPosition, int dx, int dy, int maxDepth) {
        Set<Move> possibleMoves = new HashSet<>();
        if (maxDepth > 0) {
            try {
                Square finalPosition = currentPosition.getBoard().squareAt(currentPosition.getRow() + dy,
                        currentPosition.getCol() + dx);
                if (finalPosition.isEmpty()) {
                    possibleMoves = getPossibleMoves(finalPosition, dx, dy, maxDepth - 1);
                    possibleMoves.add(new Move(position, finalPosition));
                } else if (finalPosition.getPiece().color != color) {
                    possibleMoves.add(new Move(position, finalPosition));
                }
            } catch (IllegalArgumentException e) {}
        }
        return possibleMoves;
    }

    public enum Color {
        WHITE(1),
        BLACK(-1);

        private final int multiplier;

        Color(int multiplier) {
            this.multiplier = multiplier;
        }

        public int getMultiplier() {
            return multiplier;
        }

        public static Color oppositeColor(Color color) {
            return color == WHITE ? BLACK : WHITE;
        }
    }
}
