package chess.pieces;

import chess.*;

import java.util.*;

/**
 * A chess piece. When moving, a piece captures the piece on the square that it
 * end its move on. Pieces can only capture pieces of the opposite color. All
 * pieces except the knight cannot move through other pieces of either color.
 */
public abstract class Piece {
    /**
     * The letter representing this piece in algebraic notation.
     */
    private final String notation;

    /**
     * The square this piece currently occupies.
     */
    protected Square position;

    /**
     * The color of this piece.
     */
    protected final Color color;

    /**
     * The material value of this piece.
     */
    protected final int value;

    /**
     * Whether this piece has already moved or not.
     */
    protected boolean alreadyMoved;

    /**
     * Constructs a new piece at the given position with the given color,
     * material value, and notation.
     *
     * @param position  the square at which to initialize the piece
     * @param color     the color of the piece
     * @param value     the material value of the piece
     * @param notation  the notation of the piece
     * @throws IllegalArgumentException if position, color, or notation is null
     */
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

    /**
     * Returns a set containing all the moves this piece can make. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return a set containing all the moves this piece can make
     */
    public abstract Set<Move> getPossibleMoves(boolean onlyLegalMoves);

    /**
     * Returns a set containing all the legal moves this piece can make.
     *
     * @return a set containing all the legal moves this piece can make
     */
    public Set<Move> getPossibleMoves() {
        return getPossibleMoves(true);
    }

    /**
     * Returns the material value of this piece.
     *
     * @return the material value of this piece
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the square this piece currently occupies.
     *
     * @return the square this piece currently occupies
     */
    public Square getPosition() {
        return position;
    }

    /**
     * Moves this piece to the given square.
     *
     * @param newPosition  the square to move this piece to
     */
    public void setPosition(Square newPosition) {
        if (position != null) {
            position.setPiece(null);
        }
        if (newPosition != null) {
            newPosition.setPiece(this);
        }
        position = newPosition;
    }

    /**
     * Captures this piece.
     */
    public void capture() {
        setPosition(null);
    }

    /**
     * Returns the color of this piece.
     *
     * @return the color of this piece.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns true if this piece has already moved, false otherwise.
     *
     * @return true if this piece has already moved, false otherwise
     */
    public boolean getAlreadyMoved() {
        return alreadyMoved;
    }

    /**
     * Sets this piece's already moved indicator to the given boolean.
     *
     * @param alreadyMoved  the value to set this piece's already moved indicator to
     */
    public void setAlreadyMoved(boolean alreadyMoved) {
        this.alreadyMoved = alreadyMoved;
    }

    /**
     * Returns a set containing all the moves this piece can make by only
     * moving straight along ranks and files. May return illegal moves if
     * onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves this piece can
     *                         make by only moving straight along ranks and files
     */
    protected Set<Move> getStraightMoves(boolean onlyLegalMoves) {
        return getStraightMoves(Board.NUM_ROWS - 1, onlyLegalMoves);
    }

    /**
     * Returns a set containing all the moves this piece can make by only
     * moving straight along ranks and files to the maximum depth. May return
     * illegal moves if onlyLegalMoves is false.
     *
     * @param  maxDepth        the maximum number of squares the piece can move
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves this piece can
     *                         make by only moving straight along ranks and
     *                         files to the maximum depth
     */
    protected Set<Move> getStraightMoves(int maxDepth, boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getPossibleMoves(1, 0, maxDepth, onlyLegalMoves);
        possibleMoves.addAll(getPossibleMoves(-1, 0, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(0, 1, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(0, -1, maxDepth, onlyLegalMoves));
        return possibleMoves;
    }

    /**
     * Returns a set containing all the moves this piece can make by only
     * moving diagonally. May return illegal moves if onlyLegalMoves is false.
     *
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves this piece can
     *                         make by only moving diagonally
     */
    protected Set<Move> getDiagonalMoves(boolean onlyLegalMoves) {
        return getDiagonalMoves(Board.NUM_ROWS - 1, onlyLegalMoves);
    }

    /**
     * Returns a set containing all the moves this piece can make by only
     * moving diagonally to the maximum depth. May return illegal moves if
     * onlyLegalMoves is false.
     *
     * @param  maxDepth        the maximum number of squares the piece can move
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves this piece can
     *                         make by only moving diagonally to the maximum depth
     */
    protected Set<Move> getDiagonalMoves(int maxDepth, boolean onlyLegalMoves) {
        Set<Move> possibleMoves = getPossibleMoves(1, 1, maxDepth, onlyLegalMoves);
        possibleMoves.addAll(getPossibleMoves(1, -1, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-1, -1, maxDepth, onlyLegalMoves));
        possibleMoves.addAll(getPossibleMoves(-1, 1, maxDepth, onlyLegalMoves));
        return possibleMoves;
    }

    /**
     * Returns a set containing all the moves this piece can make by only
     * moving in multiples of dx and dy to the maximum depth.
     *
     * @param  dx              the number of squares to move horizontally
     * @param  dy              the number of squares to move vertically
     * @param  maxDepth        the maximum number of squares the piece can move
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves this piece can
     *                         make by only moving in multiples of dx and dy to
     *                         the maximum depth
     */
    protected Set<Move> getPossibleMoves(int dx, int dy, int maxDepth, boolean onlyLegalMoves) {
        return getPossibleMoves(position, dx, dy, maxDepth, onlyLegalMoves);
    }

    /**
     * Returns a set containing all the moves this piece can make by starting
     * at the specified position and only moving in multiples of dx and dy to
     * the maximum depth.
     *
     * @param  startPosition   the square to start moving from
     * @param  dx              the number of squares to move horizontally
     * @param  dy              the number of squares to move vertically
     * @param  maxDepth        the maximum number of squares the piece can move
     * @param  onlyLegalMoves  whether to only consider legal moves or not
     * @return                 a set containing all the moves this piece can
     *                         make by only moving in multiples of dx and dy to
     *                         the maximum depth
     */
    protected Set<Move> getPossibleMoves(Square startPosition, int dx, int dy, int maxDepth,
                                         boolean onlyLegalMoves) {
        Set<Move> possibleMoves = new HashSet<>();
        if (maxDepth > 0) {
            try {
                Square finalPosition = startPosition.getBoard()
                        .squareAt(startPosition.getRow() + dy, startPosition.getCol() + dx);
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

    /**
     * Adds the given move to the given set if the given move is legal.
     *
     * @param move            the move to add to the set if it is legal
     * @param possibleMoves   the set to add the move to if it is legal
     * @param onlyLegalMoves  whether legality of the move should be considered
     */
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

    /**
     * Returns a string representation of this piece.
     *
     * @return a string representation of this piece
     */
    @Override
    public String toString() {
        return notation;
    }

    /**
     * Returns true if this is equal to the other object, false otherwise.
     *
     * @param  o  the object to determine equality to
     * @return    true if this is equals to the other object, false otherwise
     */
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

    /**
     * Returns a hash code representation of this piece.
     *
     * @return a hash code representation of this piece
     */
    @Override
    public int hashCode() {
        return Objects.hash(notation, position, color, value, alreadyMoved);
    }

    /**
     * The possible colors of pieces.
     */
    public enum Color {
        WHITE(1),
        BLACK(-1);

        /**
         * The multiplier of the color, used to determine which direction pawns
         * should move in.
         */
        private final int multiplier;

        /**
         * Constructs a new color with the given multiplier.
         *
         * @param multiplier  the multiplier of this color
         */
        Color(int multiplier) {
            this.multiplier = multiplier;
        }

        /**
         * Returns this color's multiplier.
         *
         * @return this color's multiplier
         */
        public int getMultiplier() {
            return multiplier;
        }

        /**
         * Returns the color opposite of the given color.
         *
         * @param  color  the color to find the opposite of
         * @return        to color opposite of the given color
         */
        public static Color oppositeColor(Color color) {
            return color == WHITE ? BLACK : WHITE;
        }
    }
}
