package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public abstract class AStar {

	private ArrayList<Node> closedNodes; //The set of nodes already evaluated.
	private LinkedList<Node> openNodes; // The set of nodes to be evaluated, initially the start node.
	private LinkedList<Node> shortestPath; //The shortest path given by the A* search.
	
	public abstract float getPathCost (Node startNode, Node endNode); // Cost from a node to another.
	public abstract float getHeuristicValue (Node node, Node goal); //Heuristic cost from a node to the goal.
	public abstract ArrayList<Node> getNeighbours(Node node); //Returns neighboring nodes if any.
	public abstract Object getState(Node node); //Returns the state.
	
	public AStar() {
		openNodes = new LinkedList<Node>();
		closedNodes = new ArrayList<Node>();
	}
	
	public LinkedList<Node> AStarSearch(Node start, Node goal) {
		start.setPathCost(0); //The starting node is free of cost.
		openNodes.add(start);
		while (!openNodes.isEmpty()) { //As long as the goal has not been found.
			Collections.sort(openNodes); //Sort the list s.t. the node with lowest evaluation function value is first.
			Node cheapestNode = openNodes.getFirst(); //The node with the lowest evaluation value.
			
			if (getState(cheapestNode).equals(getState(goal))) { //If the node is the goal node, reconstruct the shortest path.
				reconstructPath(cheapestNode, start);
				return shortestPath;
			}
			//Moves the evaluated node to the closed list.
			openNodes.remove(cheapestNode);
			closedNodes.add(cheapestNode);
			//Evaluate each of its neighboring nodes.
			for (Node nextNode : getNeighbours(cheapestNode)) {
				if (closedNodes.contains(nextNode)) { //Skip nodes that have already have been evaluated.
					continue;				}
				float nextNodePathCost = cheapestNode.getPathCost() + getPathCost(cheapestNode, nextNode); //The cost from the start to the neighbor node.
				//Evaluate new neighboring nodes that have not yet been evaluated or nodes that are cheaper than first assumed.
				if (!openNodes.contains(nextNode) || nextNodePathCost < nextNode.getPathCost()) {
					if (!openNodes.contains(nextNode)) {
						openNodes.add(nextNode);						
					}
					//Update the neighboring node.
					nextNode.setPreviousNode(cheapestNode);
					nextNode.setPathCost(nextNodePathCost);
					nextNode.setHeuristicFunctionValue(getHeuristicValue(nextNode, goal));
				}
			}
		}
		return null;
	}
	
	//Constructs the path from a node to the starting node, in such order.
	public void reconstructPath(Node node, Node start) {
		LinkedList<Node> path = new LinkedList<Node>();
		while (node.getPreviousNode() != null) {
			path.add(node);
			node = node.getPreviousNode();
		}
		path.add(start);
		shortestPath = path;
	}
	
	//Returns the last, resulting node.
	public Node resultNode() {
		return !shortestPath.isEmpty() ? shortestPath.getLast() : null;
	}
		
}
