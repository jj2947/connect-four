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

    @Override
    public List<Position> getGapSequence(BoardArray boardArray, char symbol) {
        int row, col, count;
        NextMove move = new NextMove(boardArray);
        List<Position> gapMoves = new ArrayList<>();

        for (row = boardArray.getHeight() - 1; row >= 0; row--) {
            for (col = 1; col < boardArray.getWidth() - 3; col++) {
                if (move.validMove("right horizontal", row, col, 2, symbol)
                        && move.validMove("right horizontal", row, col + 2, 2, symbol)) {
                    count = findGapCount(row, col, boardArray, symbol);
                    move.addMove(gapMoves, count, row, col);
                }

            }
        }
        return gapMoves;
    }

    private int findGapCount(int row, int col, BoardArray boardArray, char symbol) {
        int count = 0;

        for (int i = col; i < col + 5; i++) {
            if (boardArray.getBoard()[row][i] == symbol) {
                count++;
            }
        }
        return count;
    }

}
