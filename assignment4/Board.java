package assignment4;

import java.util.ArrayList;

public class Board extends MinimalConflicts{

	private int board [][];//Represents a board with integers.
	private int size, randomRow, column;//Dimension of the board, the randomly selected row, the column for the queen in randomRow.
	private ArrayList<Integer> conflicts;//The list of numbers of conflicts for the randomRow. 
	
	public Board(int size) {
		this.size = size;
		board = new int [size][size];
		//Randomly place a queen for each row.
		for(int row = 0; row<size; row++) {
			int randomCol = (int)(Math.random()*size);
			board[row][randomCol] = 1;
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public int [][] getBoard() {
		return board;
	}
	
	public void printBoard() {
		for(int row = 0; row<size; row++) {
			for(int col = 0; col<size; col++) {
				if (board[row][col] == 1) {
					System.out.print(" 1 ");
				}
				else {
					System.out.print(" 0 ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	//Checks whether the board is a solution.
	public boolean isSolution() {
		for(int row = 0; row<size; row++) {
			for(int col = 0; col<size; col++) {
				if (board[row][col] == 1) {
					if (!isLegalSlot(row, col)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//Get the number of violations
	public int violations() {
		int violations = 0;
		for (int row = 0; row<size; row++) {
			for (int col = 0; col<size; col++) {
				if (board[row][col] == 1) {
					if (!isLegalSlot(row, col)) {
						violations++;
					}
				}
			}
		}
		return violations;
	}
	
	//Checks whether there is exactly one variable in all directions.
	public boolean isLegalSlot(int row, int col) {
		return (getHorizontalQueenCount(row) == 1) && (getVerticalQueenCount(col) == 1) && (getDiagonalQueenCount(row, col) == 1);
	}
	
	//Return the number of variables.
	public int conflictingQueens(int row, int col) {
		return getHorizontalQueenCount(row) + getVerticalQueenCount(col) + getDiagonalQueenCount(row, col);
	}
	
	//Move the variable to the new value.
	@Override
	public void setNewValue(Object value, Object variable) {
		board[randomRow][(int)((ArrayList<Integer>) variable).get(1)] = 0;
		board[randomRow][(int) value] = 1;
	}
	
	//Randomly select a variable, ie. a queen.
	@Override
	public Object getRandomConflictedVariable() {
		randomRow = (int)(Math.random()*size);
		for (int col = 0; col<size; col++) {
			if (board[randomRow][col] == 1) {
				column = col;
				break;
			}
		}
		ArrayList<Integer> queenCoordinate = new ArrayList<Integer>();
		queenCoordinate.add(randomRow);
		queenCoordinate.add(column);
		return queenCoordinate;
	}
	
	//Returns the column for the queen that minimizes conflicts.
	@Override
	public Object minimumConflicts() {
		conflicts = new ArrayList<Integer>();
		conflicts = getConflictingQueensList();
		ArrayList<Integer> minimaIndices = new ArrayList<Integer>();
		minimaIndices = getSetOfMinimal(conflicts);
		int randomIndex = (int)(Math.random()*minimaIndices.size());
		return minimaIndices.get(randomIndex);
	}
	
	//Return a list of minimal.
	public ArrayList<Integer> getSetOfMinimal(ArrayList<Integer> list) {
		int minVal = Integer.MAX_VALUE;
		ArrayList<Integer> setOfMinimals = new ArrayList<Integer>();
		for (int element : list) {
			if (element < minVal) {
				minVal = element;
			}
		}
		for (int i = 0; i < size; i++) {
			if (list.get(i) == minVal) {
				setOfMinimals.add(i);
			}
		}
		return setOfMinimals;
	}
	
	//Return a list of numbers of conflicting queens if moved.
	public ArrayList<Integer> getConflictingQueensList() {
		board[randomRow][column] = 0;
		for (int col = 0; col<size; col++) {
			if (board[randomRow][col] == 1) {
				conflicts.add(conflictingQueens(randomRow, col)-3);
			} else {
				conflicts.add(conflictingQueens(randomRow, col));
			}
		}
		board[randomRow][column] = 1;
		return conflicts;
	}

	//Returns the number of queens for a row.
	public int getHorizontalQueenCount(int row) {
		int count = 0;
		for(int i=0;i<size;i++) {
			if(board[row][i] == 1) {
				count++;
			}
		}
		return count;
	}
	
	//Returns the number of queens for a column.
	public int getVerticalQueenCount(int col) {
		int count = 0;
		for(int i=0;i<size;i++) {
			if(board[i][col] == 1) {
				count++;
			}
		}
		return count;
	}
	
	public int getDiagonalQueenCount(int row, int col) {
		return countNorthEast(row, col) + countSouthEast(row, col) + countSouthWest(row, col) + countNorthWest(row, col);
	}
	
	private int countNorthWest(int row, int col) {
		int count = 0;
		int x = col;
		int y = row;
		while(x >=0 && x < size && y >= 0 && y < size) {
			if(board[y][x] == 1) {
				count++;
			}
			x--;
			y--;
		}
		return count;
	}

	private int countSouthWest(int row, int col) {
		int count = 0;
		int x = col - 1;
		int y = row + 1;
		while(x >=0 && x < size && y >= 0 && y < size) {
			if(board[y][x] == 1) {
				count++;
			}
			x--;
			y++;
		}
		return count;
	}

	private int countSouthEast(int row, int col) {
		int count = 0;
		int x = col + 1;
		int y = row + 1;
		while(x >=0 && x < size && y >= 0 && y < size) {
			if(board[y][x] == 1) {
				count++;
			}
			x++;
			y++;
		}
		return count;
	}

	private int countNorthEast(int row, int col) {
		int count = 0;
		int x = col + 1;
		int y = row - 1;
		while(x >=0 && x < size && y >= 0 && y < size) {
			if(board[y][x] == 1) {
				count++;
			}
			x++;
			y--;
		}
		return count;
	}
	
	public static void main(String[] args) {
		int boardSize = 1200;
		int max_steps = 10000;
		Board board = new Board(boardSize);
		//board.printBoard();
		long startTime = System.currentTimeMillis();
		board.minConflicts(board, max_steps);
		long endTime = System.currentTimeMillis();
		long run_time = endTime - startTime;
		//board.printBoard();
		System.out.println("Max steps: " + max_steps);
		System.out.println("Run-time: " + run_time);
		System.out.println("Found a solution: " + board.isSolution());
		System.out.println("Conflicting queens: " + board.violations());
	}






	
}
