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
    }

    if (getNext().isEmpty()) {
      return blockMoves;
    } else if (getNext().get(0).getCount() == 3 || blockMoves.get(0).getCount() < 2) {
      return getNext();
    } else {
      return blockMoves;
    }
  }

  private ArrayList<Position> getNext() {
    ArrayList<Position> nextMoves = new ArrayList<>();
    char symbol = 'O';

    nextMoves = getLongestDiagonal(symbol);

    if (nextMoves.isEmpty()) {
      nextMoves = getLongestHorizontal(symbol);
    } else if (!(getLongestHorizontal(symbol).isEmpty())) {
      if (getLongestHorizontal(symbol).get(0).getCount() > nextMoves.get(0).getCount()) {
        nextMoves.clear();
        nextMoves = getLongestHorizontal(symbol);
      } else if (getLongestHorizontal(symbol).get(0).getCount() == nextMoves.get(0).getCount()) {
        nextMoves.addAll(getLongestHorizontal(symbol));
      }
    }

    if (nextMoves.isEmpty()) {
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

    blockMoves = getLongestDiagonal(symbol);

    if (blockMoves.isEmpty()) {
      blockMoves = getLongestHorizontal(symbol);
    } else if (!(getLongestHorizontal(symbol).isEmpty())) {
      if (getLongestHorizontal(symbol).get(0).getCount() > blockMoves.get(0).getCount()) {
        blockMoves.clear();
        blockMoves.addAll(getLongestHorizontal(symbol));
      } else if (getLongestHorizontal(symbol).get(0).getCount() == blockMoves.get(0).getCount()) {
        blockMoves.addAll(getLongestHorizontal(symbol));
      }
    }

    if (blockMoves.isEmpty()) {
      blockMoves = getLongestVertical(symbol);
    } else if (!(getLongestVertical(symbol).isEmpty())) {
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

          if (validMove("vertical", row, col, count, symbol)) {
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
          if (validMove("right horizontal", row, col, count, symbol)) {
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
          if (validMove("left horizontal", row, col, count, symbol)) {
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

    // Left diagonal
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

          if (validMove("", row - 3, col + 3, 3, symbol)) {
            count = 3;
            addMove(diagonalMoves, count, row - 3, col + 3);
          }

        } else if (count <= 2) {
          if (maxRow >= -1
              && maxCol < boardArray.getWidth() + 1
              && boardArray.getBoard()[row][col] == symbol
              && boardArray.getBoard()[row - 1][col + 1] == symbol) {

            if (validMove("left diagonal", row - 2, col + 2, 2, symbol)) {
              count = 2;
              addMove(diagonalMoves, count, row - 2, col + 2);
            }

          } else {
            if (maxRow >= -2
                && maxCol < boardArray.getWidth() + 2
                && boardArray.getBoard()[row][col] == symbol) {
              if (validMove("left diagonal", row - 1, col + 1, 1, symbol)) {
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
          if (validMove("", row - 3, col - 3, 3, symbol)) {
            count = 3;
            addMove(diagonalMoves, count, row - 3, col - 3);
          }
        } else if (count < 3) {
          if (maxRow >= -1
              && maxCol >= -1
              && boardArray.getBoard()[row][col] == symbol
              && boardArray.getBoard()[row - 1][col - 1] == symbol) {
            if (validMove("right diagonal", row - 2, col - 2, 2, symbol)) {
              count = 2;
              addMove(diagonalMoves, count, row - 2, col - 2);
            }
          } else {
            if (maxRow >= -2 && maxCol >= -2 && boardArray.getBoard()[row][col] == symbol) {
              if (validMove("right diagonal", row - 1, col - 1, 3, symbol)) {
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

  private boolean validMove(String direction, int row, int col, int count, char symbol) {
    boolean result = false;

    if (boardArray.getBoard()[row][col] != ' ' || boardArray.getBoard()[row + 1][col] == ' ') {
      return false;
    }

    if (count == 2) {
      switch (direction) {
        case "left horizontal":
          if (boardArray.getBoard()[row][col - 1] == ' ') {
            result = true;
          }
          break;

        case "right horizontal":
          if (boardArray.getBoard()[row][col + 1] == ' ') {
            result = true;
          }
          break;

        case "left diagonal":
          int maxRow = row - 1;
          int maxCol = col + 1;
          if (maxRow >= 0
              && maxCol < boardArray.getWidth()
              && boardArray.getBoard()[row - 1][col + 1] == ' ') {
            result = true;
          }
          break;

        case "right diagonal":
          maxRow = row - 1;
          maxCol = col - 1;
          if (maxRow >= 0 && maxCol >= 0 && boardArray.getBoard()[row - 1][col - 1] == ' ') {
            result = true;
          }
          break;

        case "vertical":
          if (boardArray.getBoard()[row - 1][col] == ' ') {
            result = true;
          }
          break;
      }

    } else if (count == 1) {
      switch (direction) {
        case "left horizontal":
          if (boardArray.getBoard()[row][col - 1] == ' '
              && boardArray.getBoard()[row][col - 2] == ' ') {
            result = true;
          }
          break;

        case "right horizontal":
          if (boardArray.getBoard()[row][col + 1] == ' '
              && boardArray.getBoard()[row][col + 2] == ' ') {
            result = true;
          }
          break;

        case "left diagonal":
          int maxRow = row - 2;
          int maxCol = col + 2;
          if (maxRow >= 0
              && maxCol < boardArray.getWidth()
              && boardArray.getBoard()[row - 1][col + 1] == ' '
              && boardArray.getBoard()[row - 2][col + 2] == ' ') {
            result = true;
          }
          break;

        case "right diagonal":
          maxRow = row - 2;
          maxCol = col - 2;
          if (maxRow >= 0
              && maxCol >= 0
              && boardArray.getBoard()[row - 1][col - 1] == ' '
              && boardArray.getBoard()[row - 2][col - 2] == ' ') {
            result = true;
          }
          break;

        case "vertical":
          if (boardArray.getBoard()[row - 1][col] == ' '
              && boardArray.getBoard()[row - 2][col] == ' ') {
            result = true;
          }
      }
    }

    return result;
  }
}
