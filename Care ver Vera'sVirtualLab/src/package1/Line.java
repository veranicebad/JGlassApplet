package package1;


import static java.lang.Math.*;
/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/**
 *
 * @author Катя
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
//    protected boolean Right(double x, double y) {
//        if (end.y == start.y) {
//            if (end.x > start.x) {
//                if (y >= end.y) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                if (y <= end.y) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } else {
//            if (y < calcY(x)) {//((y - calcB()) / calcK() <= x) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }

