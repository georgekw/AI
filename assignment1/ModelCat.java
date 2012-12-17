package assignment1;

import robocode.*;

public class ModelCat extends Robot{
	
	private double initialEnergy = 100;
	private int turnDirection = 1;
	
	public void run() {
		while (true) {
			turnGunRight(360);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		// fire strong if the enemy has stopped or if you still have at least level 50 energy
		if (e.getVelocity() == 0 || getEnergy() > 50) {
			fire(3);
		}
		else {
			fire(1);			
		}
		double differentialEnergy = initialEnergy - e.getEnergy();
		// A energy drop might indicate shots fired. Run!
		if (e.getDistance() < 150 || differentialEnergy <= 3) {
			if (e.getBearing() < 0) {
				turnDirection = -1;
			}
			turnRight(30*turnDirection);
			ahead(70*turnDirection);
		}
		initialEnergy = e.getEnergy();
	}
	
	public void onHitWall(HitWallEvent e) {
		turnRight(100);
		ahead(400);
	}
	
	public void onHitRobot(HitRobotEvent e) {
		turnLeft(90 - e.getBearing());
		ahead(400);
	}

}
