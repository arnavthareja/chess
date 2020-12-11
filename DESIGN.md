# Design

## Chess logic

A `Board` is represented by an 8x8 2D array of `Square`s. Each `Square` contains information about
the row and column it is on, as well as the piece that occupies it. A `Board` contains useful methods
for executing and undoing moves, finding possible moves, determining if the game is over, and much more.
The `Board` class handles most of the game logic, but is abstracted away from the user by the `Chess`
class.

The `Chess` class is the user-facing class that is used to play the game of chess. The user can choose
which players, human or computer, should play, and the class handles gameplay, alternating turns
between the two players.

The `Move` class provides an easy and intuitive way to represent moves. To simplify implementation,
all possible chess moves (except for en passant) can be handled by this class. This includes regular
moves as well as more complex moves like castling or pawn promotion. The `Move` class also stores
information about the state before the move so that moves can be easily undone.

## Pieces

Since there are multiple different pieces in chess, we created a separate package for piece classes to keep our project organized.

The piece abstract class does most of the work for pieces, providing methods to easily gather possible moves and get relevant piece information.

To keep track of piece color, the Piece class has a public Color enum with helpful methods relating to piece colors.

Since there are multiple different pieces in chess, we created a separate package for piece classes to keep our project organized. We decided to make a parent class "Piece" and made each piece class inherit the features of Piece as a child of that class. It made sense to do this because each piece has a notation, value, position, and various other undeniable features. Each individual piece has a getPossibleMoves method that returns all the possible moves of that piece as a Set<Move>. The pieces with special requirements, such as the king and pawn classes, have various helper methods to fit their specific needs.

The piece abstract class does most of the work for pieces, providing methods to easily gather possible moves and get relevant piece information.

## Players

All players extend the `Player` abstract class, which defines behavior that all players should have.
The `Player` abstract class contains the implementation for most of the player methods, as they are
common to all players. The only methods that each player needs to define are `getMove()` to get the
next move to make and `toString()` to print out the name of the player.

The `HumanPlayer` class contains the logic for getting moves from the user. It accepts user input
through the console and validates the input to ensure the requested move is possible and legal.

The `RandomPlayer` class chooses the move it will make at random out of all the possible legal moves.
It can be seeded for predictable behavior.

The `MinimaxPlayer` class implements the minimax algorithm through negamax, a variant which allows
for shorter code. Using negamax allowed us to iterate through the cached possible move sets in sorted
order without having to reverse some to account for the playing color. We cached sets of possible
moves for each board state using a `HashMap`. This allowed us to avoid having to recompute sets of
possible moves for a board state if we had already visited that board state. The memoization can be
improved further, but we didn't optimize it further because of the time constraint. To store the
sets of possible moves for memoization, we decided to use a `SortedSet` so that the sets could
easily be iterated over in order. Initially, we had considered using a `PriorityQueue` for faster
access to the front element, but we chose to use a `SortedSet` so we could iterate over it in order
without having to destroy and reconstruct it or make a new array containing its elements.

The `SuboptimalMinimaxPlayer` and `WorstMinimaxPlayer` classes both extend `MinimaxPlayer` so that
the negamax algorithm could be reused without being rewritten. Every class that extends `MinimaxPlayer`
should provide a method `selectMove(SortedSet<Move>)` that chooses the move the player will make.
For `MinimaxPlayer`, this method chooses the best move. For `WorstMinimaxPlayer`, this method chooses
the worst move. For `SuboptimalMinimaxPlayer`, this method randomly chooses a move, with better moves
having a higher chance of being selected.

## Heuristics

All heuristics implement the `Heuristic` interface, which defines one public method that returns
the calculated heuristic value of a given board and color. Using this interface makes it easier to
quickly change and test different heuristic functions. Both the `MaterialHeuristic` and
`PositionalHeuristic` calculate heuristic value by calculating the difference between each color's
position. The `CombinationHeuristic` simply combines the material and positional heuristic values.
