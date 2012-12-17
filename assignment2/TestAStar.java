package assignment2;

import java.util.ArrayList;
import java.util.LinkedList;

public class TestAStar extends AStar{

	int[][] distanceArray = {
							{                0,                20,                50,               100, Integer.MAX_VALUE, Integer.MAX_VALUE},
							{               20,                 0, Integer.MAX_VALUE, Integer.MAX_VALUE,                 5, Integer.MAX_VALUE},
							{               50, Integer.MAX_VALUE,                 0, Integer.MAX_VALUE,                1 , Integer.MAX_VALUE},
							{              100, Integer.MAX_VALUE, Integer.MAX_VALUE,                 0, Integer.MAX_VALUE,               200},
							{Integer.MAX_VALUE,                 5,                 1, Integer.MAX_VALUE,                 0,                10},
							{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,               200,                10,                 0},
						   };

	public static void main (String [] args) {
		Node a = new Node("A");
		Node b = new Node("B");
		Node c = new Node("C");
		Node d = new Node("D");
		Node e = new Node("E");
		Node f = new Node("F");

		a.setNeighbour(b);
		a.setNeighbour(c);
		a.setNeighbour(d);
		b.setNeighbour(a);
		b.setNeighbour(e);
		c.setNeighbour(a);
		c.setNeighbour(e);
		d.setNeighbour(a);
		d.setNeighbour(f);
		e.setNeighbour(b);
		e.setNeighbour(c);
		e.setNeighbour(f);
		f.setNeighbour(e);
		f.setNeighbour(d);
		
		TestAStar anAStarTest = new TestAStar();		
		LinkedList<Node> resultpath = new LinkedList<Node>();
		resultpath = anAStarTest.AStarSearch(d,f);
		if (!resultpath.isEmpty()) {
			for (Node aNode : resultpath) {
				System.out.println(aNode.getState());
			}			
		}
		else {
			System.out.println("Empty result!");
		}
	}

	@Override
	public float getPathCost(Node startNode, Node endNode) {
		int startNodeIndex = 0;
		int endNodeIndex = 0;
		switch ((String)startNode.getState()) {
			case "A" : startNodeIndex = 0;
				break;
			case "B" : startNodeIndex = 1;
				break;
			case "C" : startNodeIndex = 2;
				break;
			case "D" : startNodeIndex = 3;
				break;
			case "E" : startNodeIndex = 4;
				break;
			case "F" : startNodeIndex = 5;
				break;
		}
		switch ((String)endNode.getState()) {
		case "A" : endNodeIndex = 0;
			break;
		case "B" : endNodeIndex = 1;
			break;
		case "C" : endNodeIndex = 2;
			break;
		case "D" : endNodeIndex = 3;
			break;
		case "E" : endNodeIndex = 4;
			break;
		case "F" : endNodeIndex = 5;
			break;
		}
		return distanceArray[startNodeIndex][endNodeIndex];
	}

	@Override
	public float getHeuristicValue(Node node, Node goal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Node> getNeighbours(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getState(Node node) {
		// TODO Auto-generated method stub
		return null;
	}
}
