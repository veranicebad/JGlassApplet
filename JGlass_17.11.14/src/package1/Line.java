/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.sqrt;

/**
 *
 * @author Vera
 */
public class Line {

    protected Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    protected double calcAngle() {
        double angle;
        angle = atan((start.y - end.y) / (end.x - start.x));
        return angle;
    }

    protected double calcY(double x) {
        double y;
        y = ((x - start.x) * (end.y - start.y) + start.y * (end.x - start.x))
                / (end.x - start.x);
        return y;
    }

    protected double calcX(double y) {
        double x;
        x = ((y - start.y) * (end.x - start.x) + start.x * (end.y - start.y))
                / (end.y - start.y);
        return x;
    }

    protected double calcB() {
        double b;
        b = (start.x * end.y - start.y * end.x) / (start.x - end.x);
        return b;
    }

    protected double calcK() {
        double k;
        k = (start.y - calcB()) / start.x;
        return k;
    }

    protected double calcDistance(double x, double y) {
        double d, A, B, C;
        if (end.x == start.x) {
            d = abs(end.x - x);
        } else {
            if (end.y == start.y) {
                d = abs(end.y - y);
            } else {
                A = calcK();
                B = -1;
                C = calcB();
                d = abs(A * x + B * y + C) / sqrt(A * A + B * B);
            }
        }
        return d;
    }
}
