import java.util.List;

public interface Direction {
    
    public abstract List<Position> getLongestSequence(BoardArray boardArray, int player);
    public abstract boolean checkWin(BoardArray boardArray, int player);
}
