public class BoardArray {

  private char boardArray[][];
  private int width;
  private int height;

  public BoardArray(char boardArray[][], int width, int height) {
    this.boardArray = boardArray;
    this.width = width;
    this.height = height;
  }

  public void initialiseBoard() {

    int row, col, column;

    for (row = 0; row < height; row++) {
      column = 0;
      for (col = 0; col < width; col++) {
        // If it's the first or last row, character is '-'
        if (row == 0 || row == height - 1) {
          boardArray[row][column] = '-';
        }

        // If its the first of last column character is '|'
        else if (col == 0 || col == width - 1) {
          boardArray[row][column] = '|';
        } else {
          boardArray[row][column] = ' ';
        }
        column++;
      }
    }
  }

  public void printBoard() {

    int row, col;

    System.out.print("  ");

    // Prints the column numbers on top of the board
    for (col = 0; col < width - 2; col++)
    {
        System.out.print(col + 1 + " ");
    }

    System.out.println();

    for (row = 0; row < height; row++) {
      for (col = 0; col < width; col++) {
        System.out.print(boardArray[row][col]+ " ");
      }
      System.out.println();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public char[][] getBoard() {
    return boardArray;
  }

  public void setBoard(int row, int col, char symbol) {
    boardArray[row][col] = symbol;
  }
}
