import java.util.Scanner;

public class GameLogic {
	private static int winSize;
	private static char[][] field;
	private static boolean isRed;
	
	public GameLogic(int boardSize, int winSize) {
		field = new char[boardSize][boardSize];
		
    	for (int y = 0; y < field.length; y++) {
    		for (int x = 0; x < field[y].length; x++) {
    			field[y][x] = 'c';
    		}
    	}
		this.winSize = winSize;
		isRed = true;
	}
	
	public static boolean makeMove(int column) {
		if (column < 0 || column > field.length) {
            return false;
        }
		else if (!putDisk(column, isRed ? 'R' : 'Y')) {
            return false;
        }
		
		return true;
	}
	
    // This method attempts to put the disk of the given color in the given column.
    // It returns true if successful and false if the column is filled and we cannot 
    // put a disk.
    public static boolean putDisk(int column, char color) {
        // If the first disk is there, the column is filled, returning false.
        if (field[0][column] != 'c')
            return false;

        // Check all elements in the column.
        for (int row = 0; row < field.length; ++row) {
            // If we found something, which means if the character is not
            // zero character
            if (field[row][column] != 'c') {
                // Put the disk on top of the current one.
                field[row-1][column] = color;
                isRed = !isRed;
                return true;
            }
        }
        
        // If no other disks found, we place this disk at the bottom.
        field[field.length-1][column] = color;
        isRed = !isRed;
        return true;
    }
    
    public char isOver() {
        char winner = getWinnerInRows();
        if (winner != 'c') return winner;
        winner = getWinnerInColumns();
        if (winner != 'c') return winner;
        winner = getWinnerInDiagonals();
        if (winner != 'c') return winner;

        // Now we need to check if there are empty positions, otherwise it is a draw
        for (int i = 0; i < field.length; ++i)
            for (int j = 0; j < field[i].length; ++j)
                if (field[i][j] == 'c') return 'c';

        return 'D';
    }
    
    // Check rows, if there are 4 or more disks of the same color - return winner color
    private static char getWinnerInRows() {
        // Check rows and see if there are 4 disks of the same color
        for (int row = 0; row < field.length; ++row) {
            int count = 1;
            // We will compare current element with the previous
            for (int column = 1; column < field[row].length; ++column) {
                if (field[row][column] != 'c' &&
                    field[row][column] == field[row][column-1])
                    ++count;
                else
                    count = 1;

                // Check if there are winSize in a row.
                if (count >= winSize) {
                    // Return color of the winner
                    return field[row][column];
                }
            }
        }
        // Otherwise return c character, which means nobody win in rows.
        return 'c';
    }
    
    // Check columns, if there are 4 or more disks of the same color - return winner color
    private static char getWinnerInColumns() {
        // Check rows and see if there are 4 disks of the same color
        for (int column = 0; column < field.length; ++column) {
            int count = 1;
            // We will compare current element with the previous
            for (int row = 1; row < field.length; ++row) {
                if (field[row][column] != 'c' &&
                    field[row][column] == field[row-1][column])
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= winSize) {
                    // Return color of the winner
                    return field[row][column];
                }
            }
        }
        // Otherwise return 'c' character, which means nobody win in rows.
        return 'c';
    }
    
 // Check diagonals, if there are 4 or more disks of the same color - return winner color
    private static char getWinnerInDiagonals() {
        // There are 2 kinds of diagonals, let's check those that go from top-left to bottom right

        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < field.length; ++column) {
            int count = 0;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < field.length; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column + row >= field.length) break;
                if (field[row][column+row] != 'c' &&
                    field[row-1][column + row - 1] == field[row][column+row])
                    ++count;
                else
                    count = 1;
                if (count >= winSize) return field[row][column+row];
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < field.length; ++row) {
            int count = 0;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = 1; column < field.length; ++column) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column + row >= field.length) break;
                if (field[row + column][column] != 'c' &&
                    field[row+column - 1][column - 1] == field[row + column][column])
                    ++count;
                else
                    count = 1;
                if (count >= winSize) return field[row + column][column];
            }
        }

        // Now we need to do the same for diagonals that go from top-right to bottom-left
        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < field.length; ++column) {
            int count = 0;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < field.length; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (field[row][column-row] != 'c' &&
                    field[row - 1][column - row + 1] == field[row][column-row])
                    ++count;
                else
                    count = 1;
                if (count >= winSize) return field[row][column-row];
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < field.length; ++row) {
            int count = 0;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            
            // Used to be int column = 5, don't know why !!!!!!!!!!!!!!!!!!
            for (int column = (field.length-2); column >= 0; --column) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (field[column - row][column] != 'c' &&
                    field[column - row - 1][column + 1] == field[column - row][column])
                    ++count;
                else
                    count = 1;
                if (count >= winSize) return field[column - row][column];
            }
        }

        // Otherwise return 'c'  character, which means nobody win in rows.
        return 'c';
    }

	public int getRows() {
		return field.length;
	}

	public int getColumns() {
		return field.length;
	}
    
}
