package src;

import java.util.ArrayList;
import java.util.List;
import src.directions.Diagonal;
import src.directions.Horizontal;
import src.directions.Vertical;

// Class for getting the computer's next move
public class NextMove {

  private BoardArray boardArray;

  public enum Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL
  }

  Diagonal diagonal = new Diagonal();
  Horizontal horizontal = new Horizontal();
  Vertical vertical = new Vertical();

  public NextMove(BoardArray boardArray) {
    this.boardArray = boardArray;
  }

  // Gets the arraylist of the best possible next moves
  public List<Position> getNextMoves() {

    List<Position> blockMoves = getNext('X');
    List<Position> nextMoves = getNext('O');

    // If there are no valid moves to block the human player, return best next moves for the
    // computer player
    if (blockMoves.isEmpty()) {
      return nextMoves;
    }

    // Selects the best moves to return
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

    // Gets the longest sequence and longest gap for each direction
    List<Position> longestDiagonal = diagonal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestHorizontal = horizontal.getLongestSequence(boardArray, currentSymbol);
    List<Position> longestVertical = vertical.getLongestSequence(boardArray, currentSymbol);
    List<Position> horizontalGapMoves = horizontal.getGapSequence(boardArray, currentSymbol);
    List<Position> diagonalGapMoves = diagonal.getGapSequence(boardArray, currentSymbol);

    // Compares the longest sequences and longest gaps to get the best possible next moves
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

  // Helper method that compares the longest sequences and longest gaps to get the best possible
  // next moves
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

  // Helper method that adds the best possible next moves to the arraylist
  public List<Position> addMove(List<Position> nextMoves, int count, int row, int col) {

    // If the arraylist is empty, add the move
    if (nextMoves.isEmpty()) {
      Position position = new Position(count, row, col);
      nextMoves.add(position);
      // If the count of the move is greater than the count of the last move in the arraylist,
      // remove all the current moves and add the new move
    } else if (nextMoves.get(nextMoves.size() - 1).getCount() < count) {
      nextMoves.clear();
      Position position = new Position(count, row, col);
      nextMoves.add(position);
      // If the count of the new move and count of moves in the arraylist is the same, add the new
      // move
    } else if (count == nextMoves.get(nextMoves.size() - 1).getCount()) {
      Position position = new Position(count, row, col);
      nextMoves.add(position);
    }

    return nextMoves;
  }

  // Method that checks if a move is valid for either player
  public boolean validMove(
      String type, String direction, int row, int col, int count, char symbol) {

    // If the move is a gap move, check if the position is empty
    if (type.equals("gap")
        && (boardArray.getBoard()[row][col] != ' ' && boardArray.getBoard()[row][col] != symbol)) {
      return false;
      // If the move is not a gap move, check if the position is empty and the position below is not
      // empty
    } else if (!(type.equals("gap"))
        && (boardArray.getBoard()[row][col] != ' ' || boardArray.getBoard()[row + 1][col] == ' ')) {
      return false;
    }

    // Algorithm for checking if a move is valid for different counts
    if (count == 2) {
      switch (direction) {
        case "left horizontal":
          if (boardArray.getBoard()[row][col - 1] != ' '
              && boardArray.getBoard()[row][col - 1] != symbol) {
            return false;
          }
          break;

        case "right horizontal":
          if (boardArray.getBoard()[row][col + 1] != ' '
              && boardArray.getBoard()[row][col + 1] != symbol) {
            return false;
          }
          break;

        case "left diagonal":
          if (boardArray.getBoard()[row - 1][col + 1] != ' '
              && boardArray.getBoard()[row - 1][col + 1] != symbol) {
            return false;
          }
          break;

        case "right diagonal":
          if (boardArray.getBoard()[row - 1][col - 1] != ' '
              && boardArray.getBoard()[row - 1][col - 1] != symbol) {
            return false;
          }
          break;

        case "vertical":
          if (boardArray.getBoard()[row - 1][col] != ' '
              && boardArray.getBoard()[row - 1][col] != symbol) {
            return false;
          }
          break;
      }

    } else if (count == 1) {
      int maxCol = boardArray.getWidth() - 1;
      int minCol = 0;
      int minRow = 0;

      switch (direction) {
        case "left horizontal":
          for (int i = 1; i <= 2; i++) {
            if (col - 2 < minCol
                || (boardArray.getBoard()[row][col - i] != ' '
                    && boardArray.getBoard()[row][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "right horizontal":
          for (int i = 1; i <= 2; i++) {
            if (col + 2 > maxCol
                || (boardArray.getBoard()[row][col + i] != ' '
                    && boardArray.getBoard()[row][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "left diagonal":
          for (int i = 1; i <= 2; i++) {
            if (row - 2 < minRow
                || col + 2 > maxCol
                || (boardArray.getBoard()[row - i][col + i] != ' '
                    && boardArray.getBoard()[row - i][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "right diagonal":
          for (int i = 1; i <= 2; i++) {
            if (row - 2 < minRow
                || col - 2 < minCol
                || (boardArray.getBoard()[row - i][col - i] != ' '
                    && boardArray.getBoard()[row - i][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "vertical":
          for (int i = 1; i <= 2; i++) {
            if (row - 2 < minRow
                || boardArray.getBoard()[row - i][col] != ' '
                    && boardArray.getBoard()[row - i][col] != symbol) {
              return false;
            }
          }
          break;
      }

    } else if (count == 0) {
      int maxCol = boardArray.getWidth() - 1;
      int minCol = 0;
      int minRow = 0;

      switch (direction) {
        case "left horizontal":
          for (int i = 1; i <= 3; i++) {
            if (col - 3 < minCol
                || (boardArray.getBoard()[row][col - i] != ' '
                    && boardArray.getBoard()[row][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "right horizontal":
          for (int i = 1; i <= 3; i++) {
            if (col + 3 > maxCol
                || (boardArray.getBoard()[row][col + i] != ' '
                    && boardArray.getBoard()[row][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "left diagonal":
          for (int i = 1; i <= 3; i++) {
            if (row - 3 < minRow
                || col + 3 > maxCol
                || (boardArray.getBoard()[row - i][col + i] != ' '
                    && boardArray.getBoard()[row - i][col + i] != symbol)) {
              return false;
            }
          }
          break;

        case "right diagonal":
          for (int i = 1; i <= 3; i++) {
            if (row - 3 < minRow
                || col - 3 < minCol
                || (boardArray.getBoard()[row - i][col - i] != ' '
                    && boardArray.getBoard()[row - i][col - i] != symbol)) {
              return false;
            }
          }
          break;

        case "vertical":
          for (int i = 1; i <= 3; i++) {
            if (row - 3 < minRow
                || (boardArray.getBoard()[row - i][col] != ' '
                    && boardArray.getBoard()[row - i][col] != symbol)) {
              return false;
            }
          }
          break;
      }
    }
    return true;
  }
}
