package assignment3;

public class EggCartonPuzzle extends SimulatedAnnealing {
	
	private int board [][];//A board where eggs are represented as 1.
	private int k,m,n; //Colliding egg limit and dimensions of board.
	private int duration = 10;
	
	public EggCartonPuzzle(int m, int n, int k) {
		board = new int [m][n];
		this.k = k;
		this.m = m;
		this.n = n;
	}
	
	//Returns a valid board by removing violating eggs.
	public int [][] getValidBoard() {
		for(int row=0; row<m; row++)
			for(int col=0; col<n; col++)
				if(board[row][col] == 1)
					if(!isFull(row, col))
						board[row][col] = 0;
		return board;
	}
	
	//Randomly place an egg for each row.
	public int [][] getRandomBoard() {
		int[][] board = new int[m][n];
		
		for(int row=0; row<m ; row++) {
			int index = (int)(Math.random()*n);
			board[row][index] = 1;
		}
		return board;
	}
	
	//Checks whether an egg can be placed in this position.
	public boolean isLegalSlot(int row, int col) {
		return (getHorizontalEggCount(row) < k) && (getVerticalEggCount(col) < k) && (getDiagonalEggCount(row, col) < k);
	}
	
	//Checks if any constraints are violated for this position.
	public boolean isFull(int row, int col) {
		return (getHorizontalEggCount(row) <= k) && (getVerticalEggCount(col) <= k) && (getDiagonalEggCount(row, col) <= k);
	}

	//Place eggs randomly on the board and returns it as a neighbor.
	@Override
	public Node getRandomNode(Node aNode) {
		EggCartonPuzzle aRandomPuzzle = (EggCartonPuzzle) aNode.getState();
		int [][] board = aRandomPuzzle.board;
		int eggsAdded = 0;
		
		for (int row = 0; row < aRandomPuzzle.m; row++) {
			for (int col = 0; col < aRandomPuzzle.n; col++) {
				if (isLegalSlot(row,col)) {
					if ((int)(Math.random()*2) == 1)
						board[row][col] = 1;
						eggsAdded++;
				}
			}
		}
		if (eggsAdded == 0) {
			aRandomPuzzle.board = aRandomPuzzle.getRandomBoard();
			aRandomPuzzle.board = aRandomPuzzle.getValidBoard();
		}
		Node newRandomNode = new Node(aRandomPuzzle);
		return newRandomNode;			
	}

	//Maps time to temperature.
	@Override
	public int schedule(int time) {
		return duration-time;
	}

	//Returns the number of eggs on the entire board.
	@Override
	public double getEnergyValue(Node aNode) {
		EggCartonPuzzle puzzle = (EggCartonPuzzle) aNode.getState();
		int [][] board = puzzle.board;
		int eggs = 0;
		for(int i = 0; i<board.length; i++) 
			for(int j = 0; j<board[i].length; j++) 
				if(board[i][j] == 1) 
					eggs++;
		return eggs;
	}
	
	//Returns the number of eggs for a row.
	public int getHorizontalEggCount(int row) {
		int count = 0;
		for(int i=0;i<board.length;i++) {
			if(board[row][i] == 1) {
				count++;
			}
		}
		return count;
	}
	
	//Returns the number of eggs for a column.
	public int getVerticalEggCount(int col) {
		int count = 0;
		for(int i=0;i<board.length;i++) {
			if(board[i][col] == 1) {
				count++;
			}
		}
		return count;
	}
	
	//Returns the number of eggs diagonally in any direction.
	public int getDiagonalEggCount(int row, int col) {
		return countNorthEast(row, col) + countSouthEast(row, col) + countSouthWest(row, col) + countNorthWest(row, col);
	}
	
	private int countNorthWest(int row, int col) {
		int count = 0;
		int x = col;
		int y = row;
		while(x >=0 && x < board.length && y >= 0 && y < board[x].length) {
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
		while(x >=0 && x < board.length && y >= 0 && y < board[x].length) {
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
		while(x >=0 && x < board.length && y >= 0 && y < board[x].length) {
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
		while(x >=0 && x < board.length && y >= 0 && y < board[x].length) {
			if(board[y][x] == 1) {
				count++;
			}
			x++;
			y--;
		}
		return count;
	}

	public static void main(String[] args) {
		boolean undone = true;
		int iterations = 0;
		
		//Running the SA-algorithm in a while-loop in an attempt to get optimal solution.
		while(undone) {
			
			//Creating a puzzle variant with M = N = 10 and K = 3
			EggCartonPuzzle aGame = new EggCartonPuzzle(10, 10, 3);
			aGame.board = aGame.getRandomBoard();
			aGame.board = aGame.getValidBoard();
			System.out.println("####### Initial State #######");
			for (int i = 0; i < aGame.m ; i++) {
				for (int j = 0; j < aGame.n ; j++) {
					if (aGame.board[i][j] == 1) {
						System.out.print(" 0 ");
					} else {
						System.out.print(" . ");
					}
				}
				System.out.println("");
			}
			System.out.println("");
			Node boardNode = new Node (aGame);
			Node resultNode = aGame.SimulatedAnnealingSearch(boardNode, aGame.duration);
			EggCartonPuzzle result = (EggCartonPuzzle) resultNode.getState();
			int [][] resultBoard = result.board;
			int numberofEggs = 0;
			System.out.println("      Solution State");
			for (int i = 0; i < result.m ; i++) {
				for (int j = 0; j < result.n ; j++) {
					if (resultBoard[i][j] == 1) {
						System.out.print(" 0 ");
						numberofEggs++;
					} else {
						System.out.print(" . ");
					}
				}
				System.out.println("");
			}
			System.out.println(numberofEggs + " eggs");
			System.out.println("");
			
			//Adjust the number of eggs till a solution is considered as an optimal solution. 
			if(numberofEggs>29 || iterations > 5000) {
				undone = false;
			}
			iterations++;
		}
		System.out.println("Iterations: " + iterations);
	}

}
