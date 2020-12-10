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
        if (position == null || color == null || notation == null) {
            throw new IllegalArgumentException("Position, color, and notation must not be null");
        }
        this.position = position;
        position.setPiece(this);
        this.color = color;
        this.value = value;
        this.notation = notation;
        alreadyMoved = false;
    }

    public abstract Set<Move> getPossibleMoves(boolean onlyLegalMoves);

    public Set<Move> getPossibleMoves() {
        return getPossibleMoves(true);
    }

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

    protected Set<Move> getStraightMoves(boolean onlyLegalMoves) {
        return getStraightMoves(Board.NUM_ROWS - 1, onlyLegalMoves);
    }

    protected Set<Move> getStraightMoves(int maxDepth, boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getPossibleMoves(1, 0, maxDepth, onlyLegalMoves);
        possibleMoves.addAll(getPossibleMoves(-1, 0, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(0, 1, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(0, -1, maxDepth, onlyLegalMoves));
        return possibleMoves;
    }

    protected Set<Move> getDiagonalMoves(boolean onlyLegalMoves) {
        return getDiagonalMoves(Board.NUM_ROWS - 1, onlyLegalMoves);
    }

    protected Set<Move> getDiagonalMoves(int maxDepth, boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getPossibleMoves(1, 1, maxDepth, onlyLegalMoves);
        possibleMoves.addAll(getPossibleMoves(1, -1, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-1, -1, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-1, 1, maxDepth, onlyLegalMoves));
        return possibleMoves;
    }

    protected Set<Move> getPossibleMoves(int dx, int dy, int maxDepth, boolean onlyLegalMoves) {
        return getPossibleMoves(position, dx, dy, maxDepth, onlyLegalMoves);
    }

    protected Set<Move> getPossibleMoves(Square currentPosition, int dx, int dy, int maxDepth,
                                         boolean onlyLegalMoves) {
        Set<Move> possibleMoves = new HashSet<>();
        if (maxDepth > 0) {
            try {
                Square finalPosition = currentPosition.getBoard()
                        .squareAt(currentPosition.getRow() + dy, currentPosition.getCol() + dx);
                if (finalPosition.isEmpty()) {
                    possibleMoves = getPossibleMoves(finalPosition, dx, dy, maxDepth - 1,
                                                     onlyLegalMoves);
                    addIfLegal(new Move(position, finalPosition), possibleMoves, onlyLegalMoves);
                } else if (finalPosition.getPiece().color != color) {
                    addIfLegal(new Move(position, finalPosition), possibleMoves, onlyLegalMoves);
                }
            } catch (IllegalArgumentException e) {}
        }
        return possibleMoves;
    }

    protected void addIfLegal(Move move, Set<Move> possibleMoves, boolean onlyLegalMoves) {
        if (!onlyLegalMoves) {
            possibleMoves.add(move);
            return;
        }
        Board board = position.getBoard();
        board.doMove(move);
        if (!board.inCheck(color)) {
            possibleMoves.add(move);
        }
        board.undoLastMove();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return value == piece.value && alreadyMoved == piece.alreadyMoved &&
                notation.equals(piece.notation) && Objects.equals(position, piece.position)
                && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notation, position, color, value, alreadyMoved);
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
