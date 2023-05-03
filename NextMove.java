import java.util.ArrayList;

public class NextMove {

  private BoardArray boardArray;
  private int count;
  private int row;
  private int col;

  public NextMove(BoardArray boardArray) {
    this.boardArray = boardArray;
  }

  public ArrayList<Position> getNextMoves() {

    ArrayList<Position> blockMoves = getBlockMoves();

    if (blockMoves.isEmpty()) {
      return getNext();
    } else if (blockMoves.get(0).getCount() < 2) {
      return getNext();
    } else {
      return blockMoves;
    }
  }

  private ArrayList<Position> getNext() {
    ArrayList<Position> nextMoves = new ArrayList<>();
    char symbol = 'O';

    nextMoves = null;

    if (!(getLongestDiagonal(symbol)).isEmpty()) {
      nextMoves = getLongestDiagonal(symbol);
    }

    if (nextMoves == null) {
      nextMoves = getLongestHorizontal(symbol);
    } else if (!(getLongestHorizontal(symbol).isEmpty())) {
      if (getLongestHorizontal(symbol).get(0).getCount() > nextMoves.get(0).getCount()) {
        nextMoves.clear();
        nextMoves = getLongestHorizontal(symbol);
      } else if (getLongestHorizontal(symbol).get(0).getCount() == nextMoves.get(0).getCount()) {
        nextMoves.addAll(getLongestHorizontal(symbol));
      }
    }

    if (nextMoves == null) {
      nextMoves = getLongestVertical(symbol);
    } else if (!(getLongestVertical(symbol).isEmpty())) {
      if (getLongestVertical(symbol).get(0).getCount() > nextMoves.get(0).getCount()) {
        nextMoves.clear();
        nextMoves = getLongestVertical(symbol);
      } else if (getLongestVertical(symbol).get(0).getCount() == nextMoves.get(0).getCount()) {
        nextMoves.addAll(getLongestVertical(symbol));
      }
    }

    return nextMoves;
  }

  // Block opponent
  private ArrayList<Position> getBlockMoves() {
    ArrayList<Position> blockMoves = new ArrayList<>();
    char symbol = 'X';

    blockMoves = null;

    if (!(getLongestDiagonal(symbol)).isEmpty()) {
      blockMoves = getLongestDiagonal(symbol);
    }

    if (blockMoves == null) {
      blockMoves = getLongestHorizontal(symbol);
    } else if (!(getLongestHorizontal(symbol).isEmpty()) && !(blockMoves.isEmpty())) {
      if (getLongestHorizontal(symbol).get(0).getCount() > blockMoves.get(0).getCount()) {
        blockMoves.clear();
        blockMoves.addAll(getLongestHorizontal(symbol));
      } else if (getLongestHorizontal(symbol).get(0).getCount() == blockMoves.get(0).getCount()) {
        blockMoves.addAll(getLongestHorizontal(symbol));
      }
    }

    if (blockMoves == null) {
      blockMoves = getLongestVertical(symbol);
    } else if (!(getLongestVertical(symbol).isEmpty()) && !(blockMoves.isEmpty())) {
      if (getLongestVertical(symbol).get(0).getCount() > blockMoves.get(0).getCount()) {
        blockMoves.clear();
        blockMoves.addAll(getLongestVertical(symbol));
      } else if (getLongestVertical(symbol).get(0).getCount() == blockMoves.get(0).getCount()) {
        blockMoves.addAll(getLongestVertical(symbol));
      }
    }
    return blockMoves;
  }

  // Finds longest vertical chain
  private ArrayList<Position> getLongestVertical(char symbol) {
    ArrayList<Position> verticalMoves = new ArrayList<>();
    for (col = 1; col < boardArray.getWidth() - 1; col++) {
      count = 0;
      for (row = boardArray.getHeight() - 1; row >= 0; row--) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {

          if (validMove(row, col)) {
            addMove(verticalMoves, count, row, col);
          }
          count = 0;
        }
      }
    }
    return verticalMoves;
  }

  // Finds longest horizontal chain
  private ArrayList<Position> getLongestHorizontal(char symbol) {
    ArrayList<Position> horizontalMoves = new ArrayList<>();
    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = 1; col < boardArray.getWidth() - 1; col++) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          if (validMove(row, col)) {
            addMove(horizontalMoves, count, row, col);
          }
          count = 0;
        }
      }
    }

    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = boardArray.getWidth() - 1; col >= 1; col--) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          if (validMove(row, col)) {
            addMove(horizontalMoves, count, row, col);
          }
          count = 0;
        }
      }
    }
    return horizontalMoves;
  }

  // Finds longest diagonal chain
  private ArrayList<Position> getLongestDiagonal(char symbol) {
    ArrayList<Position> diagonalMoves = new ArrayList<>();

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

          if (validMove(row - 3, col + 3)) {
            count = 3;
            addMove(diagonalMoves, count, row - 3, col + 3);
          }

        } else if (count < 3) {
          if (maxRow >= -1
              && maxCol < boardArray.getWidth() + 1
              && boardArray.getBoard()[row][col] == symbol
              && boardArray.getBoard()[row - 1][col + 1] == symbol) {

            if (validMove(row - 2, col + 2)) {
              count = 2;
              addMove(diagonalMoves, count, row - 2, col + 2);
            }

          } else if (count < 2) {
            if (maxRow >= -2
                && maxCol < boardArray.getWidth() + 2
                && boardArray.getBoard()[row][col] == symbol) {
              if (validMove(row - 1, col + 1)) {
                count = 1;
                addMove(diagonalMoves, count, row - 1, col + 1);
              }
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
          if (validMove(row - 3, col - 3)) {
            count = 3;
            addMove(diagonalMoves, count, row - 3, col - 3);
          }
        } else if (count < 3) {
          if (maxRow >= -1
              && maxCol >= -1
              && boardArray.getBoard()[row][col] == symbol
              && boardArray.getBoard()[row - 1][col - 1] == symbol) {
            if (validMove(row - 2, col - 2)) {
              count = 2;
              addMove(diagonalMoves, count, row - 2, col - 2);
            }
          } else if (count < 2) {
            if (maxRow >= -2 && maxCol >= -2 && boardArray.getBoard()[row][col] == symbol) {
              if (validMove(row - 1, col - 1)) {
                count = 1;
                addMove(diagonalMoves, count, row - 1, col - 1);
              }
            }
          }
        }
      }
    }
    return diagonalMoves;
  }

  private ArrayList<Position> addMove(ArrayList<Position> nextMoves, int count, int row, int col) {

    if (nextMoves.isEmpty()) {
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    } else if (nextMoves.get(nextMoves.size() - 1).getCount() < count) {
      nextMoves.clear();
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    } else if (count == nextMoves.get(nextMoves.size() - 1).getCount()) {
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    }

    return nextMoves;
  }

  private boolean validMove(int row, int col) {
    if (boardArray.getBoard()[row][col] == ' ' && boardArray.getBoard()[row + 1][col] != ' ') {
      return true;
    } else {
      return false;
    }
  }
}
