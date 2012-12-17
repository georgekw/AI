package assignment1;

import robocode.*;

public class Biter extends AdvancedRobot {
	double previousEnergy = 100;
	int movementDirection = 1;
	int gunDirection = 1;

	public void run() {
		setTurnGunRight(99999);
	}

	public void onHitRobot(HitRobotEvent e) {
		turnRight(e.getBearing() + 90);
		ahead(200);
	}

	public void onHitWall(HitWallEvent e) {
		turnLeft(180);
		ahead(200);
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		// Fire directly at target
		fire ( 2 ) ;
		if (e.getDistance() > 150) {
			setTurnRight(e.getBearing() + 90 - 30 * movementDirection);
		} else {
			setTurnRight(e.getBearing() * -1 * movementDirection);
		}

		double changeInEnergy = previousEnergy - e.getEnergy();
		if (changeInEnergy > 0 && changeInEnergy <= 3) {
			movementDirection = -movementDirection;
		}
		setAhead((e.getDistance() / 4 + 25) * movementDirection);

		// When a bot is spotted,
		// sweep the gun and radar
		gunDirection = -gunDirection;
		setTurnGunRight(99999 * gunDirection);


		// Track the energy level
		previousEnergy = e.getEnergy();
	}
}
