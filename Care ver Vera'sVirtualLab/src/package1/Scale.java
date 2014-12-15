package package1;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static java.lang.Math.*;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author ver
 */
public class Scale {

    protected double vmin;
    protected int width, height, dwidth, mark, legend;
    protected double length;
    protected Water water;

    public Scale(int width, int height, int dwidth, int mark, int legend, int length) {
        this.width = width;
        this.height = height;
        this.dwidth = dwidth;
        this.mark = mark;
        this.legend = legend;
        //this.water = water;
        this.length = length;
        if (dwidth == 0) {
            this.vmin = 100000;
        } else {
            this.vmin = 100000;
        }
    }

    protected double calcV(double y, double y1, double y2) {
        double tg = dwidth / (height * 2.0);
        double v;
        if (dwidth == 0) {
            v = PI * pow(width / 2, 2) * (y2 - y1);
        } else {
            v = PI * pow(tg, 2.0) * (pow(y + (width) / (tg) - y1, 3)
                    - pow(y + (width) / (tg) - y2, 3))/3;
        }
        return v;
    }

    public void paintLabel(Graphics gr, double x, double y, double xs1,
            double ys1, int length, double cx, double cy, double gamma) {
        gr.setColor(Color.BLACK);
        Graphics2D gr2D = (Graphics2D) gr;
        AffineTransform at = new AffineTransform();
        at.setToRotation(gamma, cx, cy);
        gr2D.setTransform(at);
        double dl = (height - length) / 2;
        ys1 = (ys1 - dl);
        double xs2 = x + width / 2;
        double ys2 = ys1 - length + dl;
        double V = calcV(y, y + ((height - length) / 2), y + height);
        mark = (int) (V / vmin);
        double tg = 0, hdna = 0;
        if (dwidth != 0) {
            tg = dwidth / (height * 2.0);
            hdna = width / tg - height;
        }
        double ysum = ys1;
        double dh;
        for (int i = 0; i < mark; i++) {
            if (dwidth != 0) {
                dh = pow(3*vmin / (PI * pow(tg, 2.0))
                        + pow(hdna, 3), 1.0 / 3) - hdna;
            } else {
                dh = vmin / (PI * pow(width / 2, 2));
            }
            if (i % 10 == 0) {
                if ((ysum <= ys1) && (ysum >= ys2)) {
                    //double tempV=calcV(y, ysum, y+height);
                    gr.drawString(5 * i + "", (int) (xs1 + 10) + 1,
                            (int) (ysum) + 3);
                }
            }
            hdna = hdna + dh;
            ysum = ysum - dh;
        }
        gr.drawString("мл", (int) (xs2 + (width / 2) - 20), (int) (ys2 + 13));
        at.setToRotation(0, cx, cy);
        gr2D.setTransform(at);
    }

    public void paintScale(Graphics gr, double x, double y, double xs1,
            double ys1, int length, double cx, double cy, double gamma) {
        gr.setColor(Color.BLACK);
        double tg = 0, hdna = 0;
        if (dwidth != 0) {
            tg = dwidth / (height * 2.0);
            hdna = width / tg - height;
        }
        double dl = (height - length) / 2;
        ys1 = (ys1 - dl);
        double xs2 = x + width / 2;
        double ys2 = ys1 - length + dl;
        Point scalestart = new Point(xs2, ys2);
        Point scalefinish = new Point(xs1, ys1);
        scalefinish.inclinePoint(cx, cy, gamma);
        scalestart.inclinePoint(cx, cy, gamma);
        //gr.drawLine((int) scalefinish.x, (int) scalefinish.y,
        //        (int) scalestart.x, (int) scalestart.y);
        double V;
//        if (dwidth != 0) {
//            V = PI * height * (width * width + width * (width - dwidth) + pow((width - dwidth), 2)) / 3;
//        } else {
            V = calcV(y, y + (height - length) / 2, y + height);
            //        }
        mark = (int) (V / vmin);
        //System.out.println(vmin + " ");
//        if (dwidth != 0) {
//            System.out.println(V + "!!" + mark);
//        }
        double ysum = ys1;
        for (int i = 0; i < mark; i++) {
            double dh;
            if (dwidth == 0) {
                dh = vmin / (PI * pow(width / 2, 2));
                //System.out.println(dh + "");
            } else {
                dh = pow(3*vmin / (PI * pow(tg, 2.0))
                        + pow(hdna, 3), 1.0 / 3) - hdna;
        //        System.out.println(dh + " ");
            }
            if ((ysum <= ys1) && (ysum >= ys2)) {
                Point p1 = new Point(xs1, ysum);
                Point p2 = new Point(xs1 + 5, ysum);
                p1.inclinePoint(cx, cy, gamma);
                p2.inclinePoint(cx, cy, gamma);
                gr.drawLine((int) (p1.x), (int) (p1.y),
                        (int) (p2.x), (int) (p2.y));
            }
            if (i % 10 == 0) {
                if ((ysum <= ys1) && (ysum >= ys2)) {
                    Point p4 = new Point(xs1, ysum);
                    Point p3 = new Point(xs1 + 10, ysum);
                    p4.inclinePoint(cx, cy, gamma);
                    p3.inclinePoint(cx, cy, gamma);
                    gr.drawLine((int) (p4.x), (int) (p4.y),
                            (int) (p3.x), (int) (p3.y));
                    //gr.drawString(10 * i + "", (int)(p3.x) + 1, (int)(p3.y) + 3);
                }
            }
            hdna = hdna + dh;
            ysum = ysum - dh;
        }
    }
}
