import java.util.ArrayList;
import java.util.List;

public class Horizontal implements Direction {

    @Override
    public List<Position> getLongestSequence(BoardArray boardArray, char symbol) {
        List<Position> horizontalMoves = new ArrayList<>();

        NextMove move = new NextMove(boardArray);

        int count, row, col;
        count = 0;

        // Right horizontal sequence
        for (row = boardArray.getHeight() - 1; row >= 0; row--) {
            count = 0;
            for (col = 1; col < boardArray.getWidth() - 1; col++) {
                // If the symbol is the same as the player's symbol, increment count
                if (boardArray.getBoard()[row][col] == symbol) {
                    count++;
                }

                // If the symbol is not the same as the player's symbol, reset count and
                // continue the loop
                else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
                    if (move.validMove("right horizontal", row, col, count, symbol)) {
                        move.addMove(horizontalMoves, count, row, col);
                    }
                    count = 0;
                }
            }
        }

        // Left horizontal sequence
        for (row = boardArray.getHeight() - 1; row >= 0; row--) {
            count = 0;
            for (col = boardArray.getWidth() - 1; col >= 1; col--) {
                // If the symbol is the same as the player's symbol, increment count
                if (boardArray.getBoard()[row][col] == symbol) {
                    count++;
                }

                // If the symbol is not the same as the player's symbol, reset count and
                // continue the loop
                else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
                    if (move.validMove("left horizontal", row, col, count, symbol)) {
                        move.addMove(horizontalMoves, count, row, col);
                    }
                    count = 0;
                }
            }
        }
        return horizontalMoves;
    }

    @Override
    public boolean checkWin(BoardArray boardArray, char symbol) {
        int row, col;
        int count;
        boolean win = false;

        for (row = boardArray.getHeight() - 1; row >= 0; row--) {
            count = 0;
            for (col = 1; col < boardArray.getWidth() - 1; col++) {
                // If the symbol is the same as the player's symbol, increment count
                if (boardArray.getBoard()[row][col] == symbol) {
                    count++;

                    if (count == 4) {
                        win = true;
                    }
                }

                // If the symbol is not the same as the player's symbol, reset count and
                // continue the loop
                else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {
                    count = 0;
                }
            }
        }
        return win;
    }

}
