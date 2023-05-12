package src.Directions;
import java.util.ArrayList;
import java.util.List;

import src.Position;
import src.BoardArray;
import src.NextMove;

public class Vertical implements Direction {

    @Override
    public List<Position> getLongestSequence(BoardArray boardArray, char symbol) {
        int row, col, count;
        NextMove move = new NextMove(boardArray);

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

                    if (move.validMove("", "vertical", row, col, count, symbol)) {
                        move.addMove(verticalMoves, count, row, col);
                    }
                    count = 0;
                }
            }
        }
        return verticalMoves;
    }

    @Override
    public boolean checkWin(BoardArray boardArray, char symbol) {

        int row, col, count;
        boolean win = false;

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

    @Override
    public List<Position> getGapSequence(BoardArray boardArray, char symbol) {
        List<Position> gapMoves = new ArrayList<>();
        return gapMoves;
    }

}