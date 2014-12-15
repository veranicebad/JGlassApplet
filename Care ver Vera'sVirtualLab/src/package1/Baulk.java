package package1;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Math.*;

/*
класс бревнышек(балок), на которых можно наклонять стаканы, опора для наклона
 */
/**
 *
 * @author Катя
 */
public class Baulk {

    //координата x нижней точки, координата y нижней точки,
    //координаты x и y верхней точки, угол наклона балки, отсчитывается противчасовой
    //стрелки от оси x
    protected double xb, yb, xb2, yb2, angle;
    //видна ли балка в данный момент
    volatile boolean isVisible;

    public Baulk(double xb, double yb, double xb2, double yb2, double angle) {
        this.xb = xb;
        this.xb2 = xb2;
        this.yb = yb;
        this.yb2 = yb2;
        this.isVisible = false;
        this.angle = angle;
    }
//устанавливает параметры балки, то есть перемещает балку в нужное место и наклоняет
//под нужным углом

    public void setVariableBaulk(double xb, double xb2,
            double yb, double yb2) {
        this.xb = xb;
        this.xb2 = xb2;
        this.yb = yb;
        this.yb2 = yb2;
    }
//устанавливает пересекается ли балка с отрезком прямой,координаты которого передаются параметрами
    boolean isCrossed(double x3, double y3, double x4, double y4) {
        if (x3 < x4) {
            double a = x3;
            x3 = x4;
            x4 = a;
            a = y3;
            y3 = y4;
            y4 = a;
        }
        if ((yb2 - yb) / (xb2 - xb) == (y4 - y3) / (x4 - x3)) {
            return false;
        } else {
            double x = (x3 * (xb2 - xb) * (y4 - y3) - xb * (yb2 - yb) * (x4 - x3)
                    + (yb - y3) * (x4 - x3) * (xb2 - xb))
                    / ((xb2 - xb) * (y4 - y3) - (yb2 - yb) * (x4 - x3));
            if ((angle >= 0) && (x > x4) && (x > xb) && (x < x3) && (x < xb2)) {
                return true;
            } else {
                if ((angle < 0) && (x > x4) && (x < xb) && (x < x3) && (x > xb2)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
//возвращает x координату пересечения
    double crossedX(double x3, double y3, double x4, double y4) {
        double x = 0;
        if (isCrossed(x3, y3, x4, y4)) {
            if (x3 < x4) {
                double a = x3;
                x3 = x4;
                x4 = a;
                a = y3;
                y3 = y4;
                y4 = a;
            }
            x = (x3 * (xb2 - xb) * (y4 - y3) - xb * (yb2 - yb) * (x4 - x3)
                    + (yb - y3) * (x4 - x3) * (xb2 - xb))
                    / ((xb2 - xb) * (y4 - y3) - (yb2 - yb) * (x4 - x3));
        }
        return x;
    }
// возвращает y координату пересечения
    double crossedY(double x3, double y3, double x4, double y4) {
        double y = 0;
        if (isCrossed(x3, y3, x4, y4)) {
            double x = crossedX(x3, y3, x4, y4);
            Point b, b2;
            b = new Point(xb, yb);
            b2 = new Point(xb2, yb2);
            Line l = new Line(b, b2);
            y = l.calcY(x);
        }
        return y;
    }
// отрисовка балки
    public void paintBaulk(Graphics gr) {
        if (isVisible) {
            //gr.setColor(Color.darkGray);
            Graphics2D gr2D = (Graphics2D) gr;
            gr2D.setColor(Color.decode("#8B4513"));
            BasicStroke pen1 = new BasicStroke(14, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER);
            gr2D.setStroke(pen1);
            gr2D.drawLine((int) xb, (int) yb, (int) xb2, (int) yb2);
            BasicStroke pen2 = new BasicStroke(1);
            gr2D.setStroke(pen2);
            gr2D.setColor(Color.BLACK);
            gr2D.drawOval((int) (xb - 7), (int) (yb - 7), 14, 14);
            gr2D.drawArc((int) (xb2 - 7), (int) (yb2 - 7), 14, 14,
                    (int) (-(angle)), (int) (180));
            gr2D.drawLine((int) (xb - 7 * cos(angle)), (int) (yb - 7 * sin(angle)),
                    (int) (xb2 - 7 * cos(angle)), (int) (yb2 - 7 * sin(angle)));
            gr2D.drawLine((int) (xb + 7 * cos(angle)), (int) (yb + 7 * sin(angle)),
                    (int) (xb2 + 7 * cos(angle)), (int) (yb2 + 7 * sin(angle)));
            BasicStroke pen3 = new BasicStroke();
            gr2D.setStroke(pen3);
        }
    }
}
