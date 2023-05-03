import java.util.Scanner;

public class Move {

  private int player;
  private Scanner input = new Scanner(System.in);
  private BoardArray boardArray;

  public Move(int player, BoardArray boardArray) {
    this.player = player;
    this.boardArray = boardArray;
  }

  public int getMove() {
    int valid = 0;
    int i = 1;

    System.out.print("Player " + player + " enter column number: ");
    int column = input.nextInt();

    // If the column is full, ask the player to enter a valid column
    while (boardArray.getBoard()[1][column] != ' ') {
      // Loops through all columns to check if there are any columns that aren't full
      for (int col = 0; col < boardArray.getWidth(); col++) {
        // If there are any columns that aren't full, ask the player to enter a valid column
        if (boardArray.getBoard()[1][col] == ' ') {
          System.out.print("Column is full, enter a valid column: ");
          column = input.nextInt();

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
        column = input.nextInt();

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
}
