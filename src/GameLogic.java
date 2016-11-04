public class GameLogic {
	private int winSize;
	private static char[][] field;
	private static boolean isRed;
	
	public static void main(String[] args) {
		GameLogic gm = new GameLogic(4, 4);
		gm.printBoard();
		
		gm.makeMove(1);
		gm.makeMove(1);
		gm.makeMove(1);
		gm.makeMove(1);
		
		gm.makeMove(1);
		gm.makeMove(2);
		gm.makeMove(3);
		
		gm.printBoard();
	}
	
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
	
	public boolean makeMove(int column) {
		if (column < 0 || column > field.length) {
            return false;
        }
		else if (!putDisk(column - 1, isRed ? 'R' : 'Y')) {
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
    
    public static void printBoard() {
    	for (int y = 0; y < field.length; y++) {
    		for (int x = 0; x < field[y].length; x++) {
    			System.out.print(field[y][x] + " ");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
}
