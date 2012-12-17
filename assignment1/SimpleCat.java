package assignment1;

import robocode.*;

public class SimpleCat extends Robot{
	
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
		// run if enemy is too close
		if (e.getDistance() < 150) {
			ahead(300);
		}
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
