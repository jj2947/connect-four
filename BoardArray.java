public class BoardArray {

  private char boardArray[][];
  private int width;
  private int height;

  public BoardArray(char boardArray[][], int width, int height) {
    this.boardArray = boardArray;
    this.width = width;
    this.height = height;
  }

  public void InitialiseBoard() {

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

    public void PrintBoard() {
        int row, col;
    
        for (row = 0; row < height; row++) {
        for (col = 0; col < width; col++) {
            System.out.print(boardArray[row][col]);
        }
        System.out.println();
        }
    }
}
