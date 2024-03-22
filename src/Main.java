package src;

import java.util.Scanner;

// Game implementation
public class Main {

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    int width;
    int height;
    int numPlayers;

    System.out.println(
        "  ______   ______   .__   __. .__   __.  _______   ______ .___________.    _  _    ");
    System.out.println(
        " /      | /  __  \\  |  \\ |  | |  \\ |  | |   ____| /      ||           |   | || |   ");
    System.out.println(
        "|  ,----'|  |  |  | |   \\|  | |   \\|  | |  |__   |  ,----'`---|  |----`   | || |_  ");
    System.out.println(
        "|  |     |  |  |  | |  . `  | |  . `  | |   __|  |  |         |  |        |__   _| ");
    System.out.println(
        "|  `----.|  `--'  | |  |\\   | |  |\\   | |  |____ |  `----.    |  |           | |   ");
    System.out.println(
        " \\______| \\______/  |__| \\__| |__| \\__| |_______| \\______|    |__|           |_|  "
            + " \n");

    numPlayers = getUserInput(input, "Enter number of players: ", true);
    width = getUserInput(input, "Enter the width of the board (Max 10): ", false);
    height = getUserInput(input, "Enter the height of the board (Max 10): ", false);

    // Creates a new board
    BoardArray boardArray = new BoardArray(new char[height + 2][width + 2], width + 2, height + 2);

    boardArray.initialiseBoard();
    boardArray.printBoard();

    int p1Win = 0;
    int p2Win = 0;

    /* Main game loop */
    // While neither player has won, continue the game
    while (p1Win == 0 && p2Win == 0) {

      // Player 1's turn
      Move move1 = new Move(1, boardArray);
      move1.makeMove(move1.getMove());
      boardArray.printBoard();

      // Checks if player 1 has won and assigns the value to p1Win
      p1Win = move1.CheckWin();

      // If player 1 has won, break the while loop
      if (p1Win == 1) {
        break;
      }

      // If there are two players, get player two's move
      if (numPlayers == 2) {
        Move move2 = new Move(2, boardArray);
        move2.makeMove(move2.getMove());
        boardArray.printBoard();

        // Checks if player 2 has won and assigns the value to p2Win
        p2Win = move2.CheckWin();

        // If there is a single player, get AI's move
      } else if (numPlayers == 1) {

        Move move2 = new Move(2, boardArray);
        move2.makeMove(move2.getMove2());
        boardArray.printBoard();

        // Checks if player 2 has won and assigns the value to p2Win
        p2Win = move2.CheckWin();
      }
    }
    input.close();
  }

  private static int getUserInput(Scanner input, String prompt, boolean isNumPlayers) {
    int number = 0;
    boolean correct;
    System.out.print(prompt);
    do {
      try {
        number = Integer.parseInt(input.nextLine());
        if ((isNumPlayers && (number != 1 && number != 2)) || !isNumPlayers && (number > 10 || number <= 0)) {
          System.out.print("Invalid input, please enter a valid number: ");
          correct = false;
        } else {
          correct = true;
        }
      } catch (NumberFormatException e) {
        System.out.print("Invalid input, please enter a valid number: ");
        correct = false;
      }
    } while (!correct);
    return number;
  }
}
