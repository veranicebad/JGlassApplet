package package1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vera
 */
import static java.lang.Math.*;

public class Point {

    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected double distance(double x2, double y2) {
        double d = sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
        return d;
    }

    protected void inclinePoint(double cx, double cy, double gamma) {
        double beta;
        double r, dx, dy;
        if (gamma >= 0) {
            beta = atan((cy - y) / (cx - x));
            r = (cx - x) / cos(beta);
            dx = r * cos(gamma + beta);
            dy = r * sin(gamma + beta);
        } else {
            beta = atan((cx - x) / (cy - y));
            r = (cx - x) / sin(beta);
            dx = r * sin(abs(gamma) + beta);
            dy = r * cos(abs(gamma) + beta);
        }
        x = cx - dx;
        y = cy - dy;

    }
}
