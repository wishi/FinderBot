package FinderBots;

public class Vector2D {
    // cartesian coordinates
    private boolean cartesianUpdated = true;
    private double x = 0;
    private double y = 0;
    // polar coordinates
    private boolean polarUpdated = true;
    private double phiRad = 0;
    private double magnitude = 0;

    public Vector2D() {
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        polarUpdated = false;
    }

    public double getX() {
        if (!cartesianUpdated)
            updateCartesian();
        return x;
    }

    public void setX(double x) {
        if (!cartesianUpdated)
            updateCartesian();
        if (this.x != x) {
            this.x = x;
            polarUpdated = false;
        }
    }

    public double getY() {
        if (!cartesianUpdated)
            updateCartesian();
        return y;
    }

    public void setY(double y) {
        if (!cartesianUpdated)
            updateCartesian();
        if (this.y != y) {
            this.y = y;
            polarUpdated = false;
        }
    }

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    public void setLocation(Vector2D location) {
        setLocation(location.getX(), location.getY());
    }

    /**
     * Gets the angle of this vector relative to the POSITIVE Y-AXIS. This is
     * consistent with robocode's way of handling angles, but is inconsistent
     * with the "official" polar form, which uses the angle relative to the
     * positive x-axis.
     *
     * @return Angle of this vector relative to positive y-axis.
     */
    public double getPhiRad() {
        if (!polarUpdated)
            updatePolar();
        return phiRad;
    }

    /**
     * Sets the angle of this vector relative to the POSITIVE Y-AXIS. This is
     * consistent with robocode's way of handling angles, but is inconsistent
     * with the "official" polar form, which uses the angle relative to the
     * positive x-axis.
     *
     * @param phiRad New angle of this vector relative to positive y-axis.
     */
    public void setPhiRad(double phiRad) {
        if (!polarUpdated)
            updatePolar();
        if (this.phiRad != phiRad) {
            this.phiRad = phiRad;
            normalizePhi();
            cartesianUpdated = false;
        }
    }

    /**
     * Gets the angle of this vector relative to the POSITIVE Y-AXIS. This is
     * consistent with robocode's way of handling angles, but is inconsistent
     * with the "official" polar form, which uses the angle relative to the
     * positive x-axis.
     *
     * @return Angle of this vector relative to positive y-axis.
     */
    public double getPhiDegrees() {
        return Math.toDegrees(getPhiRad());
    }

    /**
     * Sets the angle of this vector relative to the POSITIVE Y-AXIS. This is
     * consistent with robocode's way of handling angles, but is inconsistent
     * with the "official" polar form, which uses the angle relative to the
     * positive x-axis.
     *
     * @param phiDegrees New angle of this vector relative to positive y-axis.
     */
    public void setPhiDegrees(double phiDegrees) {
        setPhiRad(Math.toRadians(phiDegrees));
    }

    public double getMagnitude() {
        if (!polarUpdated)
            updatePolar();
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        if (!polarUpdated)
            updatePolar();
        if (this.magnitude != magnitude) {
            this.magnitude = magnitude;
            normalizeMagnitude();
            cartesianUpdated = false;
        }
    }

    public Vector2D multiply(double factor) {
        setMagnitude(magnitude * factor);
        return this;
    }

    public Vector2D add(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
        return this;
    }

    public Vector2D add(Vector2D summand) {
        return add(summand.getX(), summand.getY());
    }

    public Vector2D subtract(double x, double y) {
        setX(getX() - x);
        setY(getY() - y);
        return this;
    }

    public Vector2D subtract(Vector2D subtrahend) {
        return subtract(subtrahend.getX(), subtrahend.getY());
    }

    public double distance(double x, double y) {
        return Math.sqrt(Math.pow(Math.abs(x - getX()), 2) + Math.pow(Math.abs(y - getY()), 2));
    }

    public double distance(Vector2D to) {
        return distance(to.getX(), to.getY());
    }

    public Vector2D copy() {
        Vector2D copy = new Vector2D();
        copy.cartesianUpdated = cartesianUpdated;
        copy.x = x;
        copy.y = y;
        copy.polarUpdated = polarUpdated;
        copy.phiRad = phiRad;
        copy.magnitude = magnitude;
        return copy;
    }

    @Override
    public String toString() {
        if (!cartesianUpdated) {
            updateCartesian();
        } else if (!polarUpdated) {
            updatePolar();
        }
        return "Cartesian: " + x + "," + y + "; Polar: " + phiRad + "rad " + magnitude + "px";
    }

    private void updateCartesian() {
        if (!polarUpdated) {
            throw new RuntimeException("Cannot update cartesian coordinates without valid polar form.");
        }
        x = magnitude * Math.sin(phiRad); // sin and cos are switched, because we calculate the angle to the y-axis,
        y = magnitude * Math.cos(phiRad); // not the x-axis.
        cartesianUpdated = true;
    }

    private void updatePolar() {
        if (!cartesianUpdated) {
            throw new RuntimeException("Cannot update polar form without valid cartesian coordinates.");
        }
        phiRad = Math.atan2(x, y); // x and y are switched, because we calculate
        // the angle to the y-axis.
        normalizePhi();
        magnitude = Math.sqrt((x * x) + (y * y));
        polarUpdated = true;
    }

    private void normalizeMagnitude() {
        if (magnitude < 0) {
            magnitude *= -1;
            phiRad += Math.PI;
            normalizePhi();
        }
    }

    private void normalizePhi() {
        if (phiRad < 0 || phiRad >= 2 * Math.PI) {
            phiRad %= 2 * Math.PI;
            if (phiRad < 0)
                phiRad += 2 * Math.PI;
        }
    }

    public static Vector2D createPolarVector(double phiRad, double magnitude) {
        Vector2D vector = new Vector2D();
        vector.setPhiRad(phiRad);
        vector.setMagnitude(magnitude);
        return vector;
    }
}
