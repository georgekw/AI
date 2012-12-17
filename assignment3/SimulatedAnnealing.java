package assignment3;

import java.util.Random;

public abstract class SimulatedAnnealing {
	
	public abstract Node getRandomNode(Node aNode); //A random successor of the current state.
	public abstract int schedule(int time); //A mapping from time to temperature.
	public abstract double getEnergyValue(Node aNode); //A quantitative estimate of how good a state is, ie. the objective function.
	
	//Returns a solution state.
	public Node SimulatedAnnealingSearch(Node start, int duration) {
		Node current = start;
		int temperature;
		for(int time = 0; time <= duration ; time++) {
			temperature = schedule(time);
			if (temperature == 0) {
				return current;
			}
			Node next = getRandomNode(current);
			double energyDifference = getEnergyValue(next) - getEnergyValue(current);
			if(accepted(temperature, energyDifference)) {
					current = next;
			}
		}
		return current;
	}
	
	//Acceptance condition for u succeeding state.
	public boolean accepted(double temperature, double energyDifference) {
		return (energyDifference > 0) || (new Random().nextDouble() <= probabilityOfAcceptance(temperature, energyDifference));
	}
	
	//Acceptance condition for a state with less energy.
	public double probabilityOfAcceptance(double temperature, double energyDifference) {
		return Math.exp(energyDifference/temperature);
	}

}
