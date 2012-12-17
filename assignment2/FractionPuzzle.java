package assignment2;

import java.util.ArrayList;

public class FractionPuzzle extends AStar{
	
	private Node trackFractionNode, goalFractionNode; //A node representing a fraction.
	private Fraction trackFraction, goalFraction; //A fraction representing the initial fraction as well as the solution fraction.
	
	public FractionPuzzle(int [] denominator) {
		goalFraction = new Fraction (new int [] {1}, denominator);
		trackFraction = new Fraction (new int [] {1,2,3,4}, new int [] {5,6,7,8,9});
		goalFractionNode = new Node (goalFraction);
		trackFractionNode = new Node (trackFraction);
	}
	
	public Node getTrackFractionNode() {
		return trackFractionNode;
	}
	
	public Node getGoalFractionNode() {
		return goalFractionNode;
	}
	
	@Override
	public float getPathCost(Node startNode, Node endNode) {
		return 0;
	}

	//Calculate the difference between a search fraction and the goal fraction.
	@Override
	public float getHeuristicValue(Node node, Node goal) {
		Fraction fractionNode = (Fraction) node.getState();
		Fraction fractionGoalNode = (Fraction) goal.getState();
		float fractionDecimalValue = Math.abs(fractionNode.getDecimalFraction());
		float goalDecimalValue = fractionGoalNode.getDecimalFraction();
		
		return Math.abs(fractionDecimalValue - goalDecimalValue);
	}

	//Instead of returning the neighbors of a node, return a randomly swapped fraction as its neighbor.
	@Override
	public ArrayList<Node> getNeighbours(Node node) {
		Fraction fractionNode = (Fraction) trackFractionNode.getState();
		int [] numerator = fractionNode.getNumerator();
		int [] denominator = fractionNode.getDenominator();
		int numeratorIndex = (int)(Math.random()*4);
		int denominatorIndex = (int)(Math.random()*5);
		int temp = numerator[numeratorIndex];
		numerator[numeratorIndex] = denominator[denominatorIndex];
		denominator[denominatorIndex] = temp;
		
		Node newFractionNode = new Node(new Fraction(numerator, denominator));
		ArrayList<Node> newRandomFractionNode = new ArrayList<Node>();
		newRandomFractionNode.add(newFractionNode);
		
		return newRandomFractionNode;
	}
	
	// Returns the state, in which this case is a decimal value of the fraction.
	@Override
	public Object getState(Node node) {
		Fraction fractionNode = (Fraction) node.getState();
		System.out.println("Decimal value: " + fractionNode.getDecimalFraction());
		return fractionNode.getDecimalFraction();
	}
	
	public static void main(String[] args) {
		FractionPuzzle aGame = new FractionPuzzle(new int [] {7}); //Set a denominator from 2 to 9.
		aGame.AStarSearch(aGame.getTrackFractionNode(), aGame.goalFractionNode);
		if (aGame.resultNode() != null) {
			Fraction resultNode = (Fraction) aGame.resultNode().getState();
			System.out.println((int)resultNode.concatenateIntegers(resultNode.getNumerator()) + "/" + (int)resultNode.concatenateIntegers(resultNode.getDenominator()));
		}
		else {
			System.out.println("No results");
		}
	}
	
	public class Fraction {

		private int [] numerator, denominator;
		
		public Fraction(int [] n, int [] d) {
			numerator = n;
			denominator = d;
		}
		
		//Concatenate a list of single digits into an integer.
		public float concatenateIntegers(int [] intList) {
			String intString = "";
			for (int digit : intList) {
				intString = intString + Integer.toString(digit);
			}
			return Float.parseFloat(intString);
		}
		
		public float getDecimalFraction() {
			return concatenateIntegers(numerator)/concatenateIntegers(denominator);
		}
		
		public int [] getNumerator() {
			return numerator;
		}
		
		public int [] getDenominator () {
			return denominator;
		}
		
	}

}
