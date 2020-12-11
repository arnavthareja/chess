# Design

## Chess logic and utils? helper classes? What's a better word for this

We wanted an easy and intuitive way to represent moves. To simplify implementation, we made a Move class that is capable of handling the different types of chess moves.

Each square represents a square on a chessboard and is capable of holding a piece.

A chessboard is represented by a 2D array of squares.

This class is used to play the game of chess. The user can choose which players, human or computer, should play, and the class handles gameplay, alternating turns between the two players.

## Pieces

Since there are multiple different pieces in chess, we created a separate package for piece classes to keep our project organized.

The piece abstract class does most of the work for pieces, providing methods to easily gather possible moves and get relevant piece information.

To keep track of piece color, the Piece class has a public Color enum with helpful methods relating to piece colors.

Since there are multiple different pieces in chess, we created a separate package for piece classes to keep our project organized. We decided to make a parent class "Piece" and made each piece class inherit the features of Piece as a child of that class. It made sense to do this because each piece has a notation, value, position, and various other undeniable features. Each individual piece has a getPossibleMoves method that returns all the possible moves of that piece as a Set<Move>. The pieces with special requirements, such as the king and pawn classes, have various helper methods to fit their specific needs.

The piece abstract class does most of the work for pieces, providing methods to easily gather possible moves and get relevant piece information.

## Players

Used sorted set instead of priority queue to iterate over it without destroying it

## Heuristics
