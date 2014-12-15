package package1;


import static java.lang.Math.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ver
 */
public class Force extends PhysObject {

    protected double value, angle, x, y;

    public Force(double value, double angle) {
        this.value = value;
        this.angle = angle;
        this.x = getX();
        this.y = getY();
    }

    public double getX() {
        return x = value * cos(angle);
    }
    public double getY() {
        return y = value * sin(angle);
    }
}
