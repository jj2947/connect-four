import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    System.out.println("  ______   ______   .__   __. .__   __.  _______   ______ .___________.    _  _    ");
    System.out.println(" /      | /  __  \\  |  \\ |  | |  \\ |  | |   ____| /      ||           |   | || |   ");
    System.out.println("|  ,----'|  |  |  | |   \\|  | |   \\|  | |  |__   |  ,----'`---|  |----`   | || |_  ");
    System.out.println("|  |     |  |  |  | |  . `  | |  . `  | |   __|  |  |         |  |        |__   _| ");
    System.out.println("|  `----.|  `--'  | |  |\\   | |  |\\   | |  |____ |  `----.    |  |           | |   ");
    System.out.println(" \\______| \\______/  |__| \\__| |__| \\__| |_______| \\______|    |__|           |_|   \n");

    System.out.print("Enter number of players: ");
    int numPlayers = input.nextInt();

    // If the number of players is invalid, ask the user to enter a valid number of
    // players
    while (numPlayers != 1 && numPlayers != 2) {
      System.out.print("Invalid number of players, enter a valid number of players: ");
      numPlayers = input.nextInt();
    }

    BoardArray boardArray = new BoardArray(new char[7][8], 8, 7);

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

      if (numPlayers == 2) {
        // Player 2's turn
        Move move2 = new Move(2, boardArray);
        move2.makeMove(move2.getMove());
        boardArray.printBoard();

        // Checks if player 2 has won and assigns the value to p2Win
        p2Win = move2.CheckWin();
      } else if (numPlayers == 1) {
        // Computer's turn
        Move move2 = new Move(2, boardArray);
        move2.makeMove(move2.getMove2());
        boardArray.printBoard();

        // Checks if player 2 has won and assigns the value to p2Win
        p2Win = move2.CheckWin();
      }
    }
    input.close();
  }
}
