package assignment3;

import java.util.ArrayList;

public class Node {
	
	private Object state;
	private ArrayList<Node> neighbours;
	
	public Node (Object state) {
		this.state = state;
		neighbours = new ArrayList<Node>();
	}
	
	public void setNeighbour (Node neighbour) {
		neighbours.add(neighbour);
	}
	
	public ArrayList<Node> getNeighbours() {
		return neighbours;
	}
	
	public Object getState() {
		return state;
	}

}
