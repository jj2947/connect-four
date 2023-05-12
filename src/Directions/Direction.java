package src.Directions;
import java.util.List;

import src.Position;
import src.BoardArray;

public interface Direction {
    
    public abstract List<Position> getLongestSequence(BoardArray boardArray, char symbol);
    public abstract boolean checkWin(BoardArray boardArray, char symbol);
    public abstract List<Position> getGapSequence(BoardArray boardArray, char symbol);
}
