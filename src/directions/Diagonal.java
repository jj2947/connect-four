package src.directions;
import java.util.ArrayList;
import java.util.List;

import src.Position;
import src.BoardArray;
import src.NextMove;

public class Diagonal implements Direction {

    @Override
    public List<Position> getLongestSequence(BoardArray boardArray, char symbol) {
        // Finds longest diagonal sequence
        List<Position> diagonalMoves = new ArrayList<>();
        NextMove move = new NextMove(boardArray);

        int minRow, maxCol, minCol, count, row, col;
        count = 0;

        // Left diagonal sequence
        for (row = 0; row < boardArray.getHeight(); row++) {
            for (col = 0; col < boardArray.getWidth(); col++) {
                // Set the maximum row and column to check that they will be within the
                // boardArray bounds
                minRow = 0;
                maxCol = boardArray.getWidth() - 1;

                if (row > minRow && col < maxCol) {
                    if (boardArray.getBoard()[row][col] == symbol
                            && boardArray.getBoard()[row - 1][col + 1] == symbol
                            && boardArray.getBoard()[row - 2][col + 2] == symbol
                            && move.validMove("", "", row - 3, col + 3, 3, symbol)) {
                        count = 3;
                        move.addMove(diagonalMoves, count, row - 3, col + 3);
                    } else if (count < 3) {
                        if (boardArray.getBoard()[row][col] == symbol
                                && boardArray.getBoard()[row - 1][col + 1] == symbol
                                && move.validMove("", "left diagonal", row - 2, col + 2, 2, symbol)) {
                            count = 2;
                            move.addMove(diagonalMoves, count, row - 2, col + 2);
                        } else if (boardArray.getBoard()[row][col] == symbol
                                && move.validMove("", "left diagonal", row - 1, col + 1, 1, symbol)) {
                            count = 1;
                            move.addMove(diagonalMoves, count, row - 1, col + 1);
                        }
                    }
                }
            }
        }

        // Find longest right diagonal sequence
        for (row = 0; row < boardArray.getHeight(); row++) {
            for (col = 0; col < boardArray.getWidth(); col++) {
                // Set the maximum row and column to check that they will be within the
                // boardArray bounds
                minRow = 0;
                minCol = 0;

                if (row > minRow && col > minCol) {
                    if (boardArray.getBoard()[row][col] == symbol
                            && boardArray.getBoard()[row - 1][col - 1] == symbol
                            && boardArray.getBoard()[row - 2][col - 2] == symbol
                            && move.validMove("", "", row - 3, col - 3, 3, symbol)) {
                        count = 3;
                        move.addMove(diagonalMoves, count, row - 3, col - 3);

                    } else if (count < 3) {
                        if (boardArray.getBoard()[row][col] == symbol
                                && boardArray.getBoard()[row - 1][col - 1] == symbol
                                && move.validMove("", "right diagonal", row - 2, col - 2, 2, symbol)) {
                            count = 2;
                            move.addMove(diagonalMoves, count, row - 2, col - 2);

                        } else if (boardArray.getBoard()[row][col] == symbol
                                && move.validMove("", "right diagonal", row - 1, col - 1, 1, symbol)) {
                            count = 1;
                            move.addMove(diagonalMoves, count, row - 1, col - 1);

                        }
                    }
                }
            }
        }
        return diagonalMoves;
    }

    @Override
    public boolean checkWin(BoardArray boardArray, char symbol) {
        // Check for left diagonal win
        int maxRow, maxCol, row, col;
        boolean win = false;

        for (row = 0; row < boardArray.getHeight(); row++) {
            for (col = 0; col < boardArray.getWidth(); col++) {
                // Set the maximum row and column to check that they will be within the
                // boardArray bounds
                maxRow = row - 3;
                maxCol = col + 3;

                // If the maximum row and column are within the boardArray bounds and there are
                // 4 symbols in
                // a left diagonal, the player has won
                if (maxRow >= 0
                        && maxCol < boardArray.getWidth()
                        && boardArray.getBoard()[row][col] == symbol
                        && boardArray.getBoard()[row - 1][col + 1] == symbol
                        && boardArray.getBoard()[row - 2][col + 2] == symbol
                        && boardArray.getBoard()[row - 3][col + 3] == symbol) {
                    win = true;
                }
            }
        }

        // Check for right diagonal win
        for (row = 0; row < boardArray.getHeight(); row++) {
            for (col = 0; col < boardArray.getWidth(); col++) {
                // Set the maximum row and column to check that they will be within the
                // boardArray bounds
                maxRow = row - 3;
                maxCol = col - 3;

                // If the maximum row and column are within the boardArray bounds and there are
                // 4 symbols in
                // a right diagonal, the player has won
                if (maxRow >= 0
                        && maxCol >= 0
                        && boardArray.getBoard()[row][col] == symbol
                        && boardArray.getBoard()[row - 1][col - 1] == symbol
                        && boardArray.getBoard()[row - 2][col - 2] == symbol
                        && boardArray.getBoard()[row - 3][col - 3] == symbol) {
                    win = true;
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

        // Find longest right diagonal sequence
        for (row = boardArray.getHeight() - 1; row >= 0; row--) {
            for (col = 1; col < boardArray.getWidth() - 2; col++) {
                if (move.validMove("gap", "right diagonal", row, col, 2, symbol)
                        && move.validMove("gap", "right diagonal", row - 1, col - 1, 1, symbol)) {
                    count = findGapCount("right", row, col, boardArray, symbol);
                    if (count > 1) {
                        for (int i = 0; i < 4; i++) {
                            if (boardArray.getBoard()[row - i][col - i] == ' '
                                    && boardArray.getBoard()[row -i + 1][col-i] != ' ') {
                                move.addMove(gapMoves, count, row - i, col - i);
                            }

                        }
                    }

                }
            }
        }

        // Find longest left diagonal sequence
        for (row = boardArray.getHeight() - 1; row >= 0; row--) {
            for (col = 0; col < boardArray.getWidth() - 2; col++) {
                if (move.validMove("gap", "left diagonal", row, col, 2, symbol)
                        && move.validMove("gap", "left diagonal", row - 1, col + 1, 1, symbol)) {
                    count = findGapCount("left", row, col, boardArray, symbol);
                    if (count > 1) {
                        for (int i = 0; i < 4; i++) {
                            if (boardArray.getBoard()[row - i][col + i] == ' '
                                    && boardArray.getBoard()[row - i + 1][col+i] != ' ') {
                                move.addMove(gapMoves, count, row - i, col + i);
                            }
                        }
                    }
                }
            }
        }
        return gapMoves;
    }

    private int findGapCount(String direction, int row, int col, BoardArray boardArray, char symbol) {
        int count = 0;

        if (direction.equals("right")) {
            for (int i = 0; i < 4; i++) {
                if (boardArray.getBoard()[row - i][col - i] == symbol) {
                    count++;
                }
            }
        } else if (direction.equals("left")) {
            for (int i = 0; i < 4; i++) {
                if (boardArray.getBoard()[row - i][col + i] == symbol) {
                    count++;
                }
            }
        }
        return count;
    }

}
