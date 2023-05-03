import java.util.Scanner;

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
    int i = 1;

    System.out.print("Player " + player + " enter column number: ");
    int column = in.nextInt();

    // If the column is full, ask the player to enter a valid column
    while (boardArray.getBoard()[1][column] != ' ') {
      // Loops through all columns to check if there are any columns that aren't full
      for (int col = 0; col < boardArray.getWidth(); col++) {
        // If there are any columns that aren't full, ask the player to enter a valid column
        if (boardArray.getBoard()[1][col] == ' ') {
          System.out.print("Invalid column, enter a valid column: ");
          column = in.nextInt();

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

      /* If the column is invalid (i has already looped through all columns),
      ask the player to enter a valid column */
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

    /* Loop through the boardArray from the bottom to the top and assign the player's symbol to the
    first empty space in the column */
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
  // Function that checks if the player has won
  public int CheckWin() {
    int count = 0;
    char symbol;
    int row;
    int col;
    int win = 0;

    // Assigns the correct symbol for each player
    if (player == 1) {
      symbol = 'X';
    } else {
      symbol = 'O';
    }

    // Check for vertical win

    for (col = 1; col < boardArray.getWidth() - 1; col++) {
      count = 0;
      for (row = boardArray.getHeight() - 1; row >= 0; row--) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;

          if (count == 4) {
            win = 1;
          }
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          count = 0;
        }
      }
    }

    // Check for horizontal win
    for (row = boardArray.getHeight() - 1; row >= 0; row--) {
      count = 0;
      for (col = 1; col < boardArray.getWidth() - 1; col++) {
        // If the symbol is the same as the player's symbol, increment count
        if (boardArray.getBoard()[row][col] == symbol) {
          count++;

          if (count == 4) {
            win = 1;
          }
        }

        // If the symbol is not the same as the player's symbol, reset count and continue the loop
        else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
          count = 0;
        }
      }
    }

    // Check for left diagonal win
    int maxRow, maxCol;

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
            && boardArray.getBoard()[row - 2][col + 2] == symbol
            && boardArray.getBoard()[row - 3][col + 3] == symbol) {
          win = 1;
        }
      }
    }

    // Check for right diagonal win
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
            && boardArray.getBoard()[row - 2][col - 2] == symbol
            && boardArray.getBoard()[row - 3][col - 3] == symbol) {
          win = 1;
        }
      }
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
}
