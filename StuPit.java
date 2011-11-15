package FinderBots;

import robocode.AdvancedRobot;
import robocode.BattlefieldMap;

public class StuPit extends AdvancedRobot {

    public void run () {

        BattlefieldMap bfm = getBattlefieldMap(); // BUG: geht nur bei Import von AdvancedRobot!!

        Coordinate whereAmI = new Coordinate(getX(), getY());
        AStarSearch mySeeker = new AStarSearch(whereAmI, bfm);




    }
}
