package FinderBots;

import robocode.ScannedRobotEvent;

public abstract class Coordinates {
    public static final double PI2 = 2 * Math.PI;

    public static double getShortestMovementRight(double angleCurrent, double angletarget) {
        double div = angletarget - angleCurrent;
        if (div >= PI2 || div <= -PI2) div %= PI2;
        if (div >= Math.PI) {
            div -= PI2;
        } else if (div < -Math.PI) {
            div += PI2;
        }
        return div;

    }

    public static double getShortestMovementRightDegrees(double angleCurrentDegrees, double angleTargetDegrees) {
        double div = angleTargetDegrees - angleCurrentDegrees;
        if (div >= 360 || div <= -360) div %= 360;
        if (div >= 180) {
            div -= 360;
        } else if (div < -180) {
            div += 360;
        }
        return div;
    }

    public static void updateScannedRobotPosition(Vector2D current, Vector2D scanningRobotPosition, double scanningRobotHeadingRad, ScannedRobotEvent e) {
        current.setPhiRad(scanningRobotHeadingRad + e.getBearingRadians());
        current.setMagnitude(e.getDistance());
        current.add(scanningRobotPosition);
    }

    public static Vector2D getScannedRobotPosition(Vector2D scanningRobotPosition, double scanningRobotHeadingRad, ScannedRobotEvent e) {
        Vector2D position = new Vector2D();
        updateScannedRobotPosition(position, scanningRobotPosition, scanningRobotHeadingRad, e);
        return position;
    }
}
