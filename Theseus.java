package FinderBots;

import robocode.AdvancedRobot;
import robocode.BattlefieldMap;

import java.awt.*;

/**
 * @author Marius
 */
public class Theseus extends AdvancedRobot {
    private Vector2D startPoint;
    private Vector2D[] waypoints;

    @Override
    public void run() {
        setColors(Color.BLACK, Color.BLACK, Color.WHITE, Color.YELLOW, new Color(255, 255, 255, 0));
        BattlefieldMap map = getBattlefieldMap();
        startPoint = new Vector2D(getX(), getY());
        waypoints = AStarMazeSolver.getWaypoints(map);
        for (Vector2D waypoint : waypoints) {
            Vector2D ownPosition = new Vector2D(getX(), getY());
            Vector2D headingToWaypoint = waypoint.copy().subtract(ownPosition);
            double toRight = Coordinates.getShortestMovementRight(getHeadingRadians(), headingToWaypoint.getPhiRad());
            turnRightRadians(toRight);
            ahead(ownPosition.distance(waypoint));
        }
    }


    @Override
    public void onPaint(Graphics2D g) {
        g.setColor(Color.WHITE);
        Vector2D last = startPoint;
        for (Vector2D waypoint : waypoints) {
            g.drawLine((int) last.getX(), (int) last.getY(), (int) waypoint.getX(), (int) waypoint.getY());
            last = waypoint;
        }
    }
}
