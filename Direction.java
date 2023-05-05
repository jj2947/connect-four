import java.util.List;

public interface Direction {
    
    public abstract List<Position> getLongestSequence(BoardArray boardArray, char symbol);
    public abstract boolean checkWin(BoardArray boardArray, char symbol);
}
