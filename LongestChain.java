import java.util.ArrayList;
import java.util.List;

public class LongestChain {

  private BoardArray boardArray;
  private int count;
  private int row;
  private int col;
  private int prevCount;
  private List<Position> positions;

  public LongestChain(BoardArray boardArray) {
    this.boardArray = boardArray;
    positions = new ArrayList<>();
    prevCount = 0;
  }

  public void getLongestChain() {
    char symbol = 'X';

    // Finds longest vertical chain
    for (col = 1; col < boardArray.getWidth() - 1; col++) {
      count = 0;
      for (row = boardArray.getHeight() - 1; row >= 0; row--) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          if (count > prevCount) {
            positions.remove(positions.size() - 1);
            Position position = new Position(row, col);
            positions.add(position);
          } else if (count == prevCount) {
            Position position = new Position(row, col);
            positions.add(position);
          }
          count = 0;
        }
      }
    }
  }
}
