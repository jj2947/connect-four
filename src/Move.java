package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import src.directions.Diagonal;
import src.directions.Horizontal;
import src.directions.Vertical;

public class Move {

  private int player;
  private Scanner in = new Scanner(System.in);
  private BoardArray boardArray;

  public Move(int player, BoardArray boardArray) {
    this.player = player;
    this.boardArray = boardArray;
  }

  public int getMove() {
    int valid = 0;
    boolean correct = true;
    int i = 1;
    int column = 0;

    System.out.print("Player " + player + " enter column number: ");

    do {
      try {
        column = Integer.parseInt(in.nextLine());
        correct = true;
      } catch (NumberFormatException e) {
        System.out.print("Invalid column, enter a valid column: ");
        correct = false;
      }
    } while (!correct);

    // If the column is full, ask the player to enter a valid column
    while (boardArray.getBoard()[1][column] != ' ') {
      // Loops through all columns to check if there are any columns that aren't full
      for (int col = 0; col < boardArray.getWidth(); col++) {
        // If there are any columns that aren't full, ask the player to enter a valid
        // column
        if (boardArray.getBoard()[1][col] == ' ') {
          System.out.print("Invalid column, enter a valid column: ");

          do {
            try {
              column = Integer.parseInt(in.nextLine());
              correct = true;
            } catch (NumberFormatException e) {
              System.out.print("Invalid column, enter a valid column: ");
              correct = false;
            }
          } while (!correct);

          // Breaks the for loop
          col = boardArray.getWidth();
        }
      }
    }

    // Ignore any invalid columns
    while (valid == 0 && i < boardArray.getWidth() - 1) {
      if (i == column) {
        valid = 1;
        break;
      }
      i++;

      /*
       * If the column is invalid (i has already looped through all columns),
       * ask the player to enter a valid column
       */
      if (i == boardArray.getWidth() - 1) {
        System.out.print("Invalid column, enter a valid column: ");
        column = in.nextInt();

        // Reset i to 1 to check if the column is valid again in the while loop
        i = 1;
      }
    }
    return column;
  }

  // Function that assigns the player's move to the boardArray
  public void makeMove(int move) {
    int i;

    /*
     * Loop through the boardArray from the bottom to the top and assign the
     * player's symbol to the
     * first empty space in the column
     */
    for (i = boardArray.getHeight() - 1; i >= 0; i--) {
      if (boardArray.getBoard()[i][move] == ' ') {

        if (player == 1) {
          boardArray.getBoard()[i][move] = 'X';
          break;
        } else {
          boardArray.getBoard()[i][move] = 'O';
          break;
        }
      }
    }
  }

  // Function that checks if the player has won
  public int CheckWin() {
    char symbol;
    int win = 0;

    // Assigns the correct symbol for each player
    if (player == 1) {
      symbol = 'X';
    } else {
      symbol = 'O';
    }

    Diagonal diagonal = new Diagonal();
    Horizontal horizontal = new Horizontal();
    Vertical vertical = new Vertical();

    // Checks if the player has won
    if (diagonal.checkWin(boardArray, symbol)
        || horizontal.checkWin(boardArray, symbol)
        || vertical.checkWin(boardArray, symbol)) {
      win = 1;
    }

    // If the player has won, print the player that won and return 1
    if (win == 1) {
      if (player == 1) {
        System.out.println("\n\nPlayer 1 Wins!!\n\n");
        return 1;
      } else {
        System.out.println("\n\nPlayer 2 Wins!!\n\n");
        return 1;
      }
    }

    // Check for tie, when the boardArray is full and neither player has won
    for (int i = 0; i < boardArray.getHeight(); i++) {
      for (int j = 0; j < boardArray.getWidth(); j++) {
        // If the board is not full, break the loop and the function will return 0
        if (boardArray.getBoard()[i][j] == ' ') {
          i = boardArray.getHeight();
          break;
        }

        // If the board is full and neither player has won, print tie and return 1
        else if (i == boardArray.getHeight() - 1 && j == boardArray.getWidth() - 1) {
          System.out.println("\n\nTie!!\n\n");
          return 1;
        }
      }
    }

    // If the player has not won, return 0
    return 0;
  }

  public int getMove2() {

    int nextCol;
    int col;
    int index;

    NextMove next = new NextMove(boardArray);
    List<Position> nextMoves = next.getNextMoves();

    Random rand = new Random();
    index = 0;

    if (nextMoves.size() == 0) {
      for (int row = 1; row < boardArray.getHeight(); row++) {
        for (col = 1; col < boardArray.getWidth(); col++) {

          if (next.validMove("gap", "horizontal", row, col, 0, 'O')
              || next.validMove("gap", "vertical", row, col, 0, 'O')
              || next.validMove("gap", "left diagonal", row, col, 0, 'O')
              || next.validMove("gap", "right diagonal", row, col, 0, 'O')) {
            nextMoves.add(new Position(0, row, col));
          }
        }
      }
    }

    List<Position> toRemove = new ArrayList<>();

    // Check if a move will cause the other player to win
    if (!nextMoves.isEmpty()) {
      for (Position move : nextMoves) {
        if (checkHorizontal(move, next) || checkDiagonal(move, next)) {
          toRemove.add(move);
        }
      }
    }

    nextMoves.removeAll(toRemove);

    if (nextMoves.size() == 0) {

      for (int row = 1; row < boardArray.getHeight(); row++) {
        for (col = 1; col < boardArray.getWidth(); col++) {

          if (next.validMove("gap", "horizontal", row, col, 0, 'O')
              || next.validMove("gap", "vertical", row, col, 0, 'O')
              || next.validMove("gap", "left diagonal", row, col, 0, 'O')
              || next.validMove("gap", "right diagonal", row, col, 0, 'O')) {
            nextMoves.add(new Position(0, row, col));
          }
        }
      }
    }

    if (nextMoves.size() != 0) {
      index = rand.nextInt(nextMoves.size());
      Position nextMove = nextMoves.get(index);
      nextCol = nextMove.getCol();
    } else {
      nextCol = rand.nextInt(boardArray.getWidth());
    }

    // If the column is full, ask the player to enter a valid column
    while (boardArray.getBoard()[1][nextCol] != ' ')

      // Loops through all columns to check if there are any columns that aren't full
      for (col = 0; col < boardArray.getWidth(); col++) {

        // If there is a column that isn't full, set nextCol to that column
        if (boardArray.getBoard()[1][col] == ' ') {
          if (index < nextMoves.size()) {
            nextMoves.remove(index);
          }
          if (nextMoves.size() == 0) {
            index = rand.nextInt(boardArray.getWidth());
            nextCol = index;
          } else {
            index = rand.nextInt(nextMoves.size());
            Position nextMove = nextMoves.get(index);
            nextCol = nextMove.getCol();
          }

          // Resets the for loop and while loop
          col = boardArray.getWidth();
        }
      }
    return nextCol;
  }

  boolean checkHorizontal(Position move, NextMove next) {
    Horizontal horizontal = new Horizontal();

    for (int i = 0; i <= 3; i++) {
      if (move.getCol() + i < boardArray.getWidth()
          && next.validMove("gap", "left horizontal", move.getRow() - 1, move.getCol() + i, 0, 'X')
          && horizontal.findGapCount(move.getRow() - 1, move.getCol() + i - 3, boardArray, 'X') > 2) {
        return true;
      }

      if (move.getCol() - i > 0
          && next.validMove("gap", "right horizontal", move.getRow() - 1, move.getCol() - i, 0, 'X')
          && horizontal.findGapCount(move.getRow() - 1, move.getCol() - i, boardArray, 'X') > 2) {
        return true;
      }
    }
    return false;
  }

  boolean checkDiagonal(Position move, NextMove next) {
    Diagonal diagonal = new Diagonal();

    for (int i = -1; i <= 2; i++) {
      if (move.getRow() + i < boardArray.getHeight()
          && move.getCol() - i - 1 > 0
          && next.validMove(
              "gap", "left diagonal", move.getRow() + i, move.getCol() - i - 1, 0, 'X')
          && diagonal.findGapCount(
                  "left", move.getRow() + i, move.getCol() - i - 1, boardArray, 'X')
              > 2) {
        return true;
      }

      if (move.getRow() + i < boardArray.getHeight()
          && move.getCol() + i + 1 < boardArray.getWidth()
          && next.validMove(
              "gap", "right diagonal", move.getRow() + i, move.getCol() + i + 1, 0, 'X')
          && diagonal.findGapCount(
                  "right", move.getRow() + i, move.getCol() + i + 1, boardArray, 'X')
              > 2) {
        return true;
      }
    }

    return false;
  }
}
