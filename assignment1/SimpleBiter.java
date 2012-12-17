package assignment1;

import robocode.*;

public class SimpleBiter extends AdvancedRobot {

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
		if (e.getDistance() > 200) {
			setTurnRight(e.getBearing() + 90 - 30 );
		} else {
			setTurnRight(e.getBearing() * -1 );
		}
		setAhead((e.getDistance() / 4 + 25));
		setTurnGunRight(99999);
		fire ( 2 ) ;

	}
}
