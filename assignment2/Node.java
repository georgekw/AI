package assignment2;

import java.util.ArrayList;

public class Node implements Comparable<Node>{
	
	private Object state;
	private ArrayList<Node> neighbours;
	private float pathCost, heuristicFunctionValue;
	private Node previousNode;
	
	public Node (Object state) {
		this.state = state;
		neighbours = new ArrayList<Node>();
	}
	
	public void setNeighbour (Node neighbour) {
		neighbours.add(neighbour);
	}
	
	public void setHeuristicFunctionValue(float value) {
		heuristicFunctionValue = value;
	}
	
	public void setPreviousNode(Node aNode) {
		previousNode = aNode;
	}
	
	public void setPathCost(float value) {
		pathCost = value;
	}
	
	public Node getPreviousNode() {
		return previousNode;
	}
	
	public ArrayList<Node> getNeighbours() {
		return neighbours;
	}
	
	public Object getState() {
		return state;
	}
	
	public float getHeuristicFunctionValue() {
		return heuristicFunctionValue;
	}
	
	public float getPathCost() {
		return pathCost;
	}

	@Override
	public int compareTo(Node anotherNode) {
		float evaluationFunction = pathCost + heuristicFunctionValue;
		float anotherNodeEvaluationFunction = anotherNode.getPathCost() + anotherNode.getHeuristicFunctionValue();
		
		if (evaluationFunction < anotherNodeEvaluationFunction) {
			return -1;
		} else if (evaluationFunction > anotherNodeEvaluationFunction) {
			return 1;
		}
		return 0;
	}
}
