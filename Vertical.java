import java.util.ArrayList;
import java.util.List;

public class Vertical implements Direction {

    @Override
    public List<Position> getLongestSequence(BoardArray boardArray, int player) {
        int row, col, count;
        char symbol;
        NextMove move = new NextMove(boardArray);

        if (player == 1) {
            symbol = 'X';
        } else {
            symbol = 'O';
        }

        // Finds longest vertical chain
        List<Position> verticalMoves = new ArrayList<>();

        for (col = 1; col < boardArray.getWidth() - 1; col++) {
            count = 0;
            for (row = boardArray.getHeight() - 1; row >= 0; row--) {
                // If the symbol is the same as the player's symbol, increment count
                if (boardArray.getBoard()[row][col] == symbol) {
                    count++;
                }

                // If the symbol is not the same as the player's symbol, reset count and
                // continue the loop
                else if (boardArray.getBoard()[row][col] != symbol && count >= 1) {

                    if (move.validMove("vertical", row, col, count, symbol)) {
                        move.addMove(verticalMoves, count, row, col);
                    }
                    count = 0;
                }
            }
        }
        return verticalMoves;
    }

    @Override
    public boolean checkWin(BoardArray boardArray, int player) {

        int row, col, count;
        char symbol;
        boolean win = false;

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
