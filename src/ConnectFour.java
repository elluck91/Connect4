/* 
    This program implements the 'Connect four' game (exercise 7.20 in the book, 8th edition)
*/
import java.util.*;

public class ConnectFour {

    // This method attempts to put the disk of the given color in the given column.
    // It returns true if successful and false if the column is filled and we cannot 
    // put a disk.
    public static boolean putDisk(char[][] field, int column, char color) {
        // If the first disk is there, the column is filled, returning false.
        if (field[0][column] != ' ')
            return false;

        // Check all elements in the column.
        for (int row = 0; row < 7; ++row) {
            // If we found something, which means if the character is not
            // zero character
            if (field[row][column] != ' ') {
                // Put the disk on top of the current one.
                field[row-1][column] = color;
                return true;
            }
        }
        // If no other disks found, we place this diak at the bottom.
        field[6][column] = color;
        return true;
    }


    // Check rows, if there are 4 or more disks of the same color - return winner color
    private static char getWinnerInRows(char[][] field) {
        // Check rows and see if there are 4 disks of the same color
        for (int row = 0; row < 7; ++row) {
            int count = 0;
            // We will compare current element with the previous
            for (int column = 1; column < 7; ++column) {
                if (field[row][column] != ' ' &&
                    field[row][column] == field[row][column-1])
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= 4) {
                    // Return color of the winner
                    return field[row][column];
                }
            }
        }
        // Otherwise return   character, which means nobody win in rows.
        return ' ';
    }

    // Check columns, if there are 4 or more disks of the same color - return winner color
    private static char getWinnerInColumns(char[][] field) {
        // Check rows and see if there are 4 disks of the same color
        for (int column = 0; column < 7; ++column) {
            int count = 0;
            // We will compare current element with the previous
            for (int row = 1; row < 7; ++row) {
                if (field[row][column] != ' ' &&
                    field[row][column] == field[row-1][column])
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= 4) {
                    // Return color of the winner
                    return field[row][column];
                }
            }
        }
        // Otherwise return   character, which means nobody win in rows.
        return ' ';
    }

    // Check diagonals, if there are 4 or more disks of the same color - return winner color
    private static char getWinnerInDiagonals(char[][] field) {
        // There are 2 kinds of diagonals, let's check those that go from top-left to bottom right

        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < 7; ++column) {
            int count = 0;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < 7; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column + row >= 7) break;
                if (field[row][column+row] != ' ' &&
                    field[row-1][column + row - 1] == field[row][column+row])
                    ++count;
                else
                    count = 1;
                if (count >= 4) return field[row][column+row];
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < 7; ++row) {
            int count = 0;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = 1; column < 7; ++column) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column + row >= 7) break;
                System.out.print("[" + (row+column) + "]" + "[" + column + "] = " + field[row + column][column] + " | "
                		+ "field[" + (row+column - 1) + "][" + (column - 1) + "] = " + field[row+column - 1][column - 1]
                				+ " =?= field[" + (row+column) + "][" + column + " = " + field[row + column][column]);
                
                if (field[row + column][column] != ' ' &&
                    field[row+column - 1][column - 1] == field[row + column][column])
                    ++count;
                else
                    count = 1;
                if (count >= 4) return field[row + column][column];
            }
        }

        // Now we need to do the same for diagonals that go from top-right to bottom-left
        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < 7; ++column) {
            int count = 0;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < 7; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (field[row][column-row] != ' ' &&
                    field[row - 1][column - row + 1] == field[row][column-row])
                    ++count;
                else
                    count = 1;
                if (count >= 4) return field[row][column-row];
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < 7; ++row) {
            int count = 0;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = 5; column >= 0; --column) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (field[column - row][column] != ' ' &&
                    field[column - row - 1][column + 1] == field[column - row][column])
                    ++count;
                else
                    count = 1;
                if (count >= 4) return field[column - row][column];
            }
        }

        // Otherwise return   character, which means nobody win in rows.
        return ' ';
    }

    public static char getWinner(char[][] field) {
        char winner = getWinnerInRows(field);
        if (winner != ' ') return winner;
        winner = getWinnerInColumns(field);
        if (winner != ' ') return winner;
        winner = getWinnerInDiagonals(field);
        if (winner != ' ') return winner;

        // Now we need to check if there are empty positions, otherwise it is a draw
        for (int i = 0; i < field.length; ++i)
            for (int j = 0; j < field[i].length; ++j)
                if (field[i][j] == ' ') return ' ';

        return 'D';
    }

    public static void printField(char[][] field) {
        for (int row = 0; row < 7; ++row) {
            System.out.print("| ");
            for (int col = 0; col < 7; ++col)
                System.out.print(field[row][col] + "| ");
            System.out.println();
        }

        // Print bottom line
        for (int col = 0; col < 7; ++col)
            System.out.print("---");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Declare field 2D array.
        char[][] field = new char[7][7];

        // Initialize with spaces
        for (int i = 0; i < 7; ++i)
            for (int j = 0; j < 7; ++j)
                field[i][j] = ' ';
        
        printField(field);

        // This variable will alternate and mean whose turn is it. It is Red's turn now.
        boolean isRed = true;
        while (true) {
            if (isRed)
                System.out.println("Red's turn!");            
            else 
                System.out.println("Yellow's turn!");
            System.out.print("Choose column (1-7) for a disk:");
            // Read the position of turn and check if value is correct.
            int column = input.nextInt();
            if (column < 1 || column > 7) {
                System.out.println("Column should be from 1 to 7");
                continue;
            }
            // Try to put disk in a column, method returns false if the columns
            // is filled and you cannot put a disk there.
            if (!putDisk(field, column - 1, isRed ? 'R' : 'Y')) {
                System.out.println("This column is filled! Choose another one.");
                continue;
            }

            printField(field);

            // Get winner, this method returns R if Red win, Y if Yellow wins, D
            // if it is a draw and space character if we need to continue the game.
            char result = getWinner(field);
            if (result == 'D') {
                System.out.println("It is a draw!");
                break;
            }
            else if (result == 'R') {
                System.out.println("Red win!");
                break;
            }
            else if (result == 'Y') {
                System.out.println("Yellow win!");
                break;
            }
            // Otherwise we just proceed to the next turn, we need to invert isRed 
            // to alternate turns.
            isRed = !isRed;
        }
    }
}