import java.util.ArrayList;
import java.util.List;

public class NextMove {

  private BoardArray boardArray;

  public enum Direction {
    HORIZONTAL, VERTICAL, DIAGONAL
  }

  Diagonal diagonal = new Diagonal();
  Horizontal horizontal = new Horizontal();
  Vertical vertical = new Vertical();

  public NextMove(BoardArray boardArray) {
    this.boardArray = boardArray;
  }

  public List<Position> getNextMoves() {

    List<Position> blockMoves = getBlockMoves();

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

  private List<Position> getNext() {
    List<Position> nextMoves = new ArrayList<>();
    char currentSymbol = 'O';
    List<Position> longestHorizontal = horizontal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestVertical = vertical.getLongestSequence(boardArray, currentSymbol);

    nextMoves = diagonal.getLongestSequence(boardArray, currentSymbol);

    if (nextMoves.isEmpty()) {
      nextMoves = longestHorizontal;
    } else {
      compareLongestSequences(nextMoves, longestHorizontal);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = diagonal.getLongestSequence(boardArray, currentSymbol);
    } else {
      compareLongestSequences(nextMoves, longestVertical);
    }
    return nextMoves;
  }

  // Moves to block the opponent
  private List<Position> getBlockMoves() {
    List<Position> blockMoves = new ArrayList<>();
    char currentSymbol = 'X';
    List<Position> longestHorizontal = horizontal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestVertical = vertical.getLongestSequence(boardArray, currentSymbol);

    blockMoves = diagonal.getLongestSequence(boardArray, currentSymbol);

    if (blockMoves.isEmpty()) {
      blockMoves = longestHorizontal;
    } else {
      blockMoves = compareLongestSequences(blockMoves, longestHorizontal);
    }

    if (blockMoves.isEmpty()) {
      blockMoves = longestVertical;
    } else {
      blockMoves = compareLongestSequences(blockMoves, longestVertical);
    }

    return blockMoves;
  }

  private List<Position> compareLongestSequences(List<Position> seq1, List<Position> seq2) {
    if (!seq2.isEmpty()) {
      if (seq2.get(0).getCount() > seq1.get(0).getCount()) {
        return seq2;
      } else if (seq2.get(0).getCount() == seq1.get(0).getCount()) {
        seq1.addAll(seq2);
      }
    }
    return seq1;
  }

  public List<Position> addMove(List<Position> nextMoves, int count, int row, int col) {

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

  public boolean validMove(String direction, int row, int col, int count, char symbol) {
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
          if (boardArray.getBoard()[row - 1][col + 1] == ' ') {
            result = true;
          }
          break;

        case "right diagonal":
          if (boardArray.getBoard()[row - 1][col - 1] == ' ') {
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
      int maxRow = boardArray.getHeight() - 1;
      int maxCol = boardArray.getWidth() - 1;
      int minCol = 0;
      int minRow = 0;

      if (row > minRow && row < maxRow && col > minCol && col < maxCol) {
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
            if (boardArray.getBoard()[row - 1][col + 1] == ' '
                && boardArray.getBoard()[row - 2][col + 2] == ' ') {
              result = true;
            }
            break;

          case "right diagonal":
            if (boardArray.getBoard()[row - 1][col - 1] == ' '
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
    }

    return result;
  }
}
