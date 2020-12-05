package chess;

import java.util.*;

public class CastleMove extends Move{
    public CastleMove(Square position) {
        
        super(position, )
    }

    private Set<CastleMove> getCastleMove() {
        Set<CastleMove> possibleMoves = new HashSet<>();
        possibleMoves.add(tryRight());
        possibleMoves.add(tryLeft());
        return possibleMoves;
    }

    private CastleMove tryRight() {
        
    }

    private CastleMove tryLeft() {

    }
}
