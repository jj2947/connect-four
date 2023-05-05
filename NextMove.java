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

    List<Position> blockMoves = getNext('X');
    List<Position> nextMoves = getNext('O');

    if (blockMoves.isEmpty()) {
      return nextMoves;
    }

    if (nextMoves.isEmpty()) {
      return blockMoves;
    } else if (nextMoves.get(0).getCount() == 3 || blockMoves.get(0).getCount() < 2) {
      return nextMoves;
    } else {
      return blockMoves;
    }
  }

  private List<Position> getNext(char currentSymbol) {
    List<Position> nextMoves = new ArrayList<>();

    List<Position> longestDiagonal = diagonal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestHorizontal = horizontal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestVertical = vertical.getLongestSequence(boardArray, currentSymbol);
    List<Position> horizontalGapMoves = horizontal.getGapSequence(boardArray, currentSymbol);
    List<Position> diagonalGapMoves = diagonal.getGapSequence(boardArray, currentSymbol);

    nextMoves = longestDiagonal;

    if (nextMoves.isEmpty()) {
      nextMoves = longestHorizontal;
    } else {
      nextMoves = compareLongestSequences(nextMoves, longestHorizontal);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = longestVertical;
    } else {
      nextMoves = compareLongestSequences(nextMoves, longestVertical);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = horizontalGapMoves;
    } else {
      nextMoves = compareLongestSequences(nextMoves, horizontalGapMoves);
    }

    if (nextMoves.isEmpty()) {
      nextMoves = diagonalGapMoves;
    } else {
      nextMoves = compareLongestSequences(nextMoves, diagonalGapMoves);
    }

    return nextMoves;
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

  public boolean validMove(String type, String direction, int row, int col, int count, char symbol) {
    boolean result = false;

    if (type.equals("gap") && (boardArray.getBoard()[row][col] != ' ' && boardArray.getBoard()[row][col] != symbol)
        || boardArray.getBoard()[row + 1][col] == ' ') {
      return false;
    } else if (!(type.equals("gap")) && (boardArray.getBoard()[row][col] != ' ' || boardArray.getBoard()[row + 1][col] == ' ')) {
      return false;
    }

    if (count == 2) {
      switch (direction) {
        case "left horizontal":
          if (boardArray.getBoard()[row][col - 1] == ' ' || boardArray.getBoard()[row][col - 1] == symbol) {
            result = true;
          }
          break;

        case "right horizontal":
          if (boardArray.getBoard()[row][col + 1] == ' ' || boardArray.getBoard()[row][col + 1] == symbol) {
            result = true;
          }
          break;

        case "left diagonal":
          if (boardArray.getBoard()[row - 1][col + 1] == ' ' || boardArray.getBoard()[row - 1][col + 1] == symbol) {
            result = true;
          }
          break;

        case "right diagonal":
          if (boardArray.getBoard()[row - 1][col - 1] == ' ' || boardArray.getBoard()[row - 1][col - 1] == symbol) {
            result = true;
          }
          break;

        case "vertical":
          if (boardArray.getBoard()[row - 1][col] == ' ' || boardArray.getBoard()[row - 1][col] == symbol) {
            result = true;
          }
          break;
      }

    } else if (count == 1) {
      int maxRow = boardArray.getHeight() - 1;
      int maxCol = boardArray.getWidth() - 1;
      int minCol = 0;
      int minRow = 0;

      if (row - 2 >= minRow && row <= maxRow && col - 2 >= minCol && col + 2 <= maxCol) {
        switch (direction) {
          case "left horizontal":
            if ((boardArray.getBoard()[row][col - 1] == ' ' || boardArray.getBoard()[row][col - 1] == symbol)
                && (boardArray.getBoard()[row][col - 2] == ' ' || boardArray.getBoard()[row][col - 2] == symbol)) {
              result = true;
            }
            break;

          case "right horizontal":
            if ((boardArray.getBoard()[row][col + 1] == ' ' || boardArray.getBoard()[row][col + 1] == symbol)
                && (boardArray.getBoard()[row][col + 2] == ' ' || boardArray.getBoard()[row][col + 2] == symbol)) {
              result = true;
            }
            break;

          case "left diagonal":
            if ((boardArray.getBoard()[row - 1][col + 1] == ' ' || boardArray.getBoard()[row - 1][col + 1] == symbol)
                && (boardArray.getBoard()[row - 2][col + 2] == ' '
                    || boardArray.getBoard()[row - 2][col + 2] == symbol)) {
              result = true;
            }
            break;

          case "right diagonal":
            if ((boardArray.getBoard()[row - 1][col - 1] == ' ' || boardArray.getBoard()[row - 1][col - 1] == symbol)
                && (boardArray.getBoard()[row - 2][col - 2] == ' '
                    || boardArray.getBoard()[row - 2][col - 2] == symbol)) {
              result = true;
            }
            break;

          case "vertical":
            if ((boardArray.getBoard()[row - 1][col] == ' ' || boardArray.getBoard()[row - 1][col] == symbol)
                && (boardArray.getBoard()[row - 2][col] == ' ' || boardArray.getBoard()[row - 2][col] == symbol)) {
              result = true;
            }
        }
      }
    }

    return result;
  }
}
