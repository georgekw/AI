package assignment2;

import java.util.ArrayList;
import java.util.Collections;

public class LinearCheckersPuzzle extends AStar {

	private ArrayList<CheckerPiece> checkerBoard, goalBoard;//List representing the initial board and the solution board.
	private int emptyIndex, moves;//Keep track of the empty slot in the board as well as the number of pieces that have been moved.
	private Node trackCheckersNode, goalCheckersNode; //The state of a node is a board.
	
	public enum CheckerPiece {
		red, black, nil
	}
	
	public LinearCheckersPuzzle(int boardSize) {
		checkerBoard = new ArrayList<CheckerPiece>();
		goalBoard = new ArrayList<CheckerPiece>();
		generateNewBoard(boardSize);
		moves = 0;
		trackCheckersNode = new Node(checkerBoard);
		goalCheckersNode = new Node(goalBoard);
	}
	
	//Create a new checkers board.
	public void generateNewBoard(int pieceSize) {
		int boardSize = pieceSize + 1;
		for (int i = 0 ; i <= boardSize ; i++) {
			if (i<boardSize/2) {
				checkerBoard.add(CheckerPiece.black);
				goalBoard.add(CheckerPiece.black);
			} else if (i == boardSize/2+1) {
				emptyIndex = i-1;
				checkerBoard.add(CheckerPiece.nil);
				goalBoard.add(CheckerPiece.nil);
			} else if (i > boardSize/2 + 1) {
				checkerBoard.add(CheckerPiece.red);
				goalBoard.add(CheckerPiece.red);
			}
		}
		Collections.reverse(goalBoard);
	}
	
	//Returns a list of movable pieces' indices.
	public ArrayList<Integer> moveableCheckerPieceIndex() {
		int [] indexList = {emptyIndex-2, emptyIndex-1, emptyIndex+1, emptyIndex+2};
		ArrayList<Integer> validIndexList = new ArrayList<Integer>();
		for (int index : indexList) {
			if (index >= 0 && index < checkerBoard.size()) {
				validIndexList.add(index);
			}
		}
		
		return validIndexList;
	}
	
	//Move a checker piece.
	public void swapPieces(ArrayList<CheckerPiece> board) {
		ArrayList<Integer> moveablePieces = moveableCheckerPieceIndex();
		int randomIndex = (int)(Math.random()*moveablePieces.size());
		CheckerPiece temp = board.get(emptyIndex);
		board.set(emptyIndex, board.get(moveablePieces.get(randomIndex)));
		board.set(moveablePieces.get(randomIndex), temp);
		emptyIndex = moveablePieces.get(randomIndex);
		moves++;
	}
	
	public ArrayList<CheckerPiece> getCheckerBoard() {
		return checkerBoard;
	}

	@Override
	public float getPathCost(Node startNode, Node endNode) {
		return moves;
	}

	@Override
	public float getHeuristicValue(Node node, Node goal) {
		ArrayList<CheckerPiece> board = (ArrayList<CheckerPiece>) node.getState();
		int redIndices = 0;
		int blackIndices = 0;
		for (int i = 0; i<board.size(); i++) {
			if (board.get(i).equals(CheckerPiece.red)) {
				redIndices = redIndices + i;
			} else if (board.get(i).equals(CheckerPiece.black)) {
				blackIndices = blackIndices + (board.size()-1) + i;
			}
		}
		return redIndices + blackIndices;
	}

	//Instead of returning the neighbors of a node, return a board with a piece that has moved.
	@Override
	public ArrayList<Node> getNeighbours(Node node) {
		ArrayList<Node> swappedBoard = new ArrayList<Node>();
		ArrayList<CheckerPiece> board = (ArrayList<CheckerPiece>) node.getState();
		swapPieces(board);
		Node swappedNode = new Node(board);
		swappedBoard.add(swappedNode);
		return swappedBoard;
	}

	//Return the state of a node, in which this case is an list representing a board.
	@Override
	public Object getState(Node node) {
		ArrayList<CheckerPiece> board = (ArrayList<CheckerPiece>) node.getState();
		return board;
	}
	
	public static void main(String[] args) {
		LinearCheckersPuzzle cb = new LinearCheckersPuzzle(6);
		cb.AStarSearch(cb.trackCheckersNode, cb.goalCheckersNode);
		ArrayList<CheckerPiece> resultList = (ArrayList<CheckerPiece>)cb.resultNode().getState();
		for (CheckerPiece cp : resultList) {
			System.out.println(cp.toString());
		}
		System.out.println("Moves: " + cb.moves);
	}

}
