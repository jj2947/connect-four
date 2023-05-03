import java.util.ArrayList;

public class LongestChain {

  private BoardArray boardArray;
  private int count;
  private int row;
  private int col;
  private int prevCount;
  private ArrayList<Position> positions;
  private char symbol;

  public LongestChain(BoardArray boardArray) {
    this.boardArray = boardArray;
    positions = new ArrayList<>();
    prevCount = 0;
    symbol = 'O';
  }

  public ArrayList<Position> getNextMoves() {

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
          if (positions.isEmpty()) {
            Position position = new Position(count, row, col);
            positions.add(position);
          } else if (count > prevCount && count >= positions.get(positions.size() - 1).getCount()) {

            positions.clear();

            Position position = new Position(count, row, col);
            positions.add(position);

          } else if (count == prevCount
              && count >= positions.get(positions.size() - 1).getCount()) {
            Position position = new Position(count, row, col);
            positions.add(position);
          }
          prevCount = count;
          count = 0;
        }
      }
    }

    // Finds longest horizontal chain
    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = 1; col < boardArray.getWidth() - 1; col++) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          if (positions.isEmpty()) {
            Position position = new Position(count, row, col);
            positions.add(position);
          } else if (count > prevCount && count >= positions.get(positions.size() - 1).getCount()) {

            positions.clear();

            Position position = new Position(count, row, col);
            positions.add(position);

          } else if (count == prevCount
              && count >= positions.get(positions.size() - 1).getCount()) {
            Position position = new Position(count, row, col);
            positions.add(position);
          }
          prevCount = count;
          count = 0;
        }
      }
    }

    // Finds longest diagonal chain
    if (getLongestDiagonal().isEmpty()) {
      return positions;
    } else if (getLongestDiagonal().get(0).getCount() > positions.get(0).getCount()) {
      positions.clear();
      positions.addAll(getLongestDiagonal());
    } else if (getLongestDiagonal().get(0).getCount() == positions.get(0).getCount()) {
      positions.addAll(getLongestDiagonal());
    }

    return positions;
  }

  // Finds longest diagonal chain
  private ArrayList<Position> getLongestDiagonal() {
    ArrayList<Position> diagonalPositions = new ArrayList<>();

    int maxRow, maxCol, count;
    count = 0;

    for (row = 0; row < boardArray.getHeight(); row++) {
      for (col = 0; col < boardArray.getWidth(); col++) {
        // Set the maximum row and column to check that they will be within the boardArray bounds
        maxRow = row - 3;
        maxCol = col + 3;

        // If the maximum row and column are within the boardArray bounds and there are 4 symbols in
        // a left diagonal, the player has won
        if (maxRow >= 0
            && maxCol < boardArray.getWidth()
            && boardArray.getBoard()[row][col] == symbol
            && boardArray.getBoard()[row - 1][col + 1] == symbol
            && boardArray.getBoard()[row - 2][col + 2] == symbol) {
          count = 3;
          if (diagonalPositions.isEmpty()) {
            Position position = new Position(3, row - 3, col + 3);
            diagonalPositions.add(position);
          } else if (diagonalPositions.get(diagonalPositions.size() - 1).getCount() < count) {
            diagonalPositions.clear();
          }
          Position position = new Position(3, row - 3, col + 3);
          diagonalPositions.add(position);
        } else if (count < 3) {
          if (maxRow >= -1
              && maxCol < boardArray.getWidth() + 1
              && boardArray.getBoard()[row][col] == symbol
              && boardArray.getBoard()[row - 1][col + 1] == symbol) {
            count = 2;
            if (diagonalPositions.isEmpty()) {
              Position position = new Position(2, row - 2, col + 2);
              diagonalPositions.add(position);
            } else if (diagonalPositions.get(diagonalPositions.size() - 1).getCount() < count) {
              diagonalPositions.clear();
            }
            Position position = new Position(2, row - 2, col + 2);
            diagonalPositions.add(position);
          } else if (count < 2) {
            if (maxRow >= -2
                && maxCol < boardArray.getWidth() + 2
                && boardArray.getBoard()[row][col] == symbol) {
              count = 1;
              Position position = new Position(1, row - 1, col + 1);
              diagonalPositions.add(position);
            }
          }
        }
      }
    }

    // Find longest right diagonal
    for (row = 0; row < boardArray.getHeight(); row++) {
      for (col = 0; col < boardArray.getWidth(); col++) {
        // Set the maximum row and column to check that they will be within the boardArray bounds
        maxRow = row - 3;
        maxCol = col - 3;

        // If the maximum row and column are within the boardArray bounds and there are 4 symbols in
        // a right diagonal, the player has won
        if (maxRow >= 0
            && maxCol >= 0
            && boardArray.getBoard()[row][col] == symbol
            && boardArray.getBoard()[row - 1][col - 1] == symbol
            && boardArray.getBoard()[row - 2][col - 2] == symbol) {
          count = 3;
          if (diagonalPositions.isEmpty()) {
            Position position = new Position(3, row - 3, col - 3);
            diagonalPositions.add(position);
          } else if (diagonalPositions.get(diagonalPositions.size() - 1).getCount() < count) {
            diagonalPositions.clear();
          }
          Position position = new Position(3, row - 3, col - 3);
          diagonalPositions.add(position);
        } else if (count < 3) {
          if (maxRow >= -1
              && maxCol >= -1
              && boardArray.getBoard()[row][col] == symbol
              && boardArray.getBoard()[row - 1][col - 1] == symbol) {
            count = 2;
            if (diagonalPositions.isEmpty()) {
              Position position = new Position(2, row - 2, col - 2);
              diagonalPositions.add(position);
            } else if (diagonalPositions.get(diagonalPositions.size() - 1).getCount() < count) {
              diagonalPositions.clear();
            }
            Position position = new Position(2, row - 2, col - 2);
            diagonalPositions.add(position);
          } else if (count < 2) {
            if (maxRow >= -2 && maxCol >= -2 && boardArray.getBoard()[row][col] == symbol) {
              count = 1;
              Position position = new Position(1, row - 1, col - 1);
              diagonalPositions.add(position);
            }
          }
        }
      }
    }
    return diagonalPositions;
  }
}
