package package1;


import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import static java.lang.Math.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import sun.java2d.loops.FillRect;

/**
 *класс стаканов
 * @author ver
 */
public class Glass extends MaterialObject {

    protected int dwidth, mark, legend;
    protected Water water;
    protected Scale scale;
    protected double gamma, cx, cy, coeff;
    protected double x1, x2, x3, x4, y4, y1, y2, y3, flag;
    protected Stream stream;
    protected Point point1, point2, point3, point4;
    protected Line line1, line2, line3, line4;
    protected boolean isIncline = false;
    protected boolean existScale;
    protected boolean existLabels;
    protected boolean isFill;
    protected boolean isPrime = false;
    protected Water sourceDopant = null;
    public List innerObjects = new LinkedList();
    protected boolean existPipes;
    protected Pipe pipe1, pipe2;
    protected float massGlass;

    public Glass(float mass, int x, int y, int width, int height, int dwidth, int legend,
            Water water, double gamma, Scale scale, Stream stream,
            boolean isScale, boolean isLabel, boolean movable,
            boolean existencePipe, Pipe pipe1, Pipe pipe2, boolean isFill) {
        super(mass, x, y, width, height, movable);
        this.legend = legend;
        this.dwidth = dwidth;
        this.water = water;
        this.gamma = gamma;
        this.scale = scale;
        this.stream = stream;
        this.defaultX = x;
        this.defaultY = y;
        this.existLabels = isLabel;
        this.existScale = isScale;
        this.isFill = isFill;
        this.z = legend;
        this.defaultZ = z;
        this.existPipes = existencePipe;
        this.pipe1 = pipe1;
        this.pipe2 = pipe2;
        this.massGlass = mass;
        this.mass = getMass();
        this.coeff=calcCoefficientTransformation();
    }

    protected double calcCoefficientTransformation(){
        double k;
        double S0;
        double v = 0;
        double v1, v2;
        double gamma1 = abs(gamma);
        double h1 = 0;
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        while (v < water.Vw) {
            v1 = (width - dwidth) * h1*cos(alfa) + (h1) * (h1 * cos(alfa)) *sin(alfa);
            v = v1;
            h1 = h1 + 1;
        }
        S0=v;
        k=water.Vw/S0;
        return k;
    }

    protected double calcwaterh1orl1() {
        if (water.Vw == 0) {
            return 0;
        }
        double gamma1 = abs(gamma);
        double dgamma = 0;
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        if ((cos(gamma1) == 0) || (sin(PI / 2 - (gamma1 + alfa)) == 0)
                || (sin(PI / 2 - (gamma1 + alfa) + 1e-6) == 0)
                || (cos(PI / 2 - (gamma1 + alfa)) == 0)) {
            dgamma = 1e-6;
        }
        gamma1 = gamma1 + dgamma;
        double v = 0;
        double v1, v2, l1 = 0;
        double h1 = 0;
        flag = 0;
        while (v < water.Vw) {
            flag++;
            v1 = coeff*((width - dwidth) * h1*cos(alfa)
                    + (h1) * (h1 * cos(alfa)) * sin(alfa));
            v2 = coeff*((sin(gamma1) * (width - dwidth + 2 * h1 * sin(alfa))
                    * ((width - dwidth + 2 * h1 * sin(alfa)) / cos(gamma1 + dgamma)
                    + (width - dwidth + 2 * h1 * sin(alfa)) * tan(gamma1 + dgamma)
                    * sin(alfa) / (sin(PI / 2 - (gamma1 + alfa) + dgamma)))) / 2);
            v = v1 + v2;
            h1 = h1 + 1;
        }
        if (flag == 1) {
            l1 = sqrt(2 * water.Vw / (sin(PI / 2 + alfa) * tan(gamma1 + dgamma)
                    * (sin(alfa) / (tan(PI / 2 - (gamma1 + alfa) + dgamma) + dgamma) + cos(alfa))));
            return l1;
        } else {
            return h1;
        }
    }

    protected void inclineGlass(Graphics gr, double gamma, double cx, double cy) {
        point1.inclinePoint(cx, cy, gamma);
        point2.inclinePoint(cx, cy, gamma);
        point3.inclinePoint(cx, cy, gamma);
        point4.inclinePoint(cx, cy, gamma);
        x1 = point1.x;
        x2 = point2.x;
        x3 = point3.x;
        x4 = point4.x;
        y1 = point1.y;
        y2 = point2.y;
        y3 = point3.y;
        y4 = point4.y;
    }

    protected void paintGlass(Graphics gr) {
        x1 = x;
        x2 = x + width;
        x3 = x + width - dwidth / 2;
        x4 = x + dwidth / 2;
        y1 = y;
        y2 = y;
        y3 = y + height;
        y4 = y + height;
        point1 = new Point(x1, y1);
        point2 = new Point(x2, y2);
        point3 = new Point(x3, y3);
        point4 = new Point(x4, y4);
        inclineGlass(gr, gamma, cx, cy);
        gr.setColor(Color.decode("#E0FFFF"));
        gr.fillPolygon(new int[]{(int) point1.x, (int) point2.x, (int) point3.x,
                    (int) point4.x},
                new int[]{(int) point1.y, (int) point2.y, (int) point3.y,
                    (int) point4.y}, 4);
        gr.setColor(Color.black);
        gr.drawPolygon(new int[]{(int) point1.x, (int) point2.x, (int) point3.x,
                    (int) point4.x},
                new int[]{(int) point1.y, (int) point2.y, (int) point3.y,
                    (int) point4.y}, 4);
    }

    public boolean outpouring() {
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        double gamma1 = abs(gamma);
        if (flag == 1) {
            double l1 = sqrt(2 * water.Vw / (sin(PI / 2 + alfa)
                    * tan(gamma1) * (sin(alfa)
                    / tan(PI / 2 - (gamma1 + alfa)) + cos(alfa))));
            double l2 = l1 * tan(gamma1) * (sin(alfa)
                    / tan(PI / 2 - (gamma1 + alfa)) + cos(alfa));
            if (l2 > height / cos(alfa)) {
                return true;
            } else {
                return false;
            }
        } else {
            double h1 = calcwaterh1orl1();
            double h2 = h1 + (width - dwidth + 2 * h1 * sin(alfa))
                    * tan(gamma1) * (sin(alfa)
                    / tan(PI / 2 - (gamma1 + alfa)) + cos(alfa));
            if (flag > 1 && (h2 > (height / cos(alfa))) || (h1 > (height / cos(alfa)))) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected void paintWaterTriangle(Graphics gr, double x3, double y3,
            double gamma) {
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        double k = 1;
        double l1, l2;
        if (gamma < 0) {
            k = -1;
        }
        gamma = abs(gamma);
        if (PI / 2 - gamma - alfa <= 0) {
            water.Vw = 0;
            water.originalV = 0;
        }
        double dgamma = 0;
        if ((cos(gamma) == 0) || (cos(PI / 2 - (gamma + alfa)) == 0)
                || (sin(PI / 2 - (gamma + alfa) + 1e-6) == 0)
                || (sin(PI / 2 - (gamma + alfa)) == 0)) {
            dgamma = 1e-6;
        }
        l1 = calcwaterh1orl1();
        l2 = l1 * (tan(gamma + dgamma)) * (sin(alfa)
                / (tan(PI / 2 - (gamma + alfa) + dgamma) + dgamma) + cos(alfa));
        if (outpouring()) {
            l2 = height / cos(alfa);
        }
        gr.fillPolygon(new int[]{(int) (x3 - k * l1 * (cos(gamma))),
                    (int) (x3 + k * l2 * (sin(gamma + alfa))), (int) x3},
                new int[]{(int) (y3 - l1 * (sin(gamma))),
                    (int) (y3 - l2 * (cos(gamma + alfa))), (int) y3}, 3);
        gr.setColor(Color.black);
        gr.drawPolygon(new int[]{(int) (x3 - k * l1 * (cos(gamma))),
                    (int) (x3 + k * l2 * (sin(gamma + alfa))), (int) x3},
                new int[]{(int) (y3 - l1 * (sin(gamma))),
                    (int) (y3 - l2 * (cos(gamma + alfa))), (int) y3}, 3);
    }

    protected void paintWaterTetragon(Graphics gr, double x3, double y3,
            double x4, double y4, double gamma) {
        double k = 1;
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        if (PI / 2 - gamma - alfa <= 0) {
            water.Vw = 0;
            water.originalV = 0;
        }
        if (gamma < 0) {
            k = -1;
        }
        gamma = abs(gamma);
        double h1, h2;
        double dgamma = 0;
        h1 = calcwaterh1orl1();
        if ((cos(gamma) == 0) || (cos(PI / 2 - (gamma + alfa)) == 0)
                || (sin(PI / 2 - (gamma + alfa) + 1e-6) == 0)
                || (sin(PI / 2 - (gamma + alfa)) == 0)) {
            dgamma = 1e-6;
        }
        h2 = h1 + (width - dwidth + 2 * h1 * sin(alfa))
                * tan(gamma + dgamma) * (sin(alfa) / (tan(PI / 2 - (gamma + alfa)
                + dgamma) + dgamma) + cos(alfa));
        if (outpouring()) {
            h2 = height / cos(alfa);
            if (h1 > h2) {
                h1 = h2;
            }
        }
        gr.fillPolygon(new int[]{(int) (x4 + k * h1 * sin(gamma - alfa)),
                    (int) (x3 + k * h2 * sin(gamma + alfa)), (int) x3, (int) x4},
                new int[]{(int) (y4 - h1 * cos(gamma - alfa)),
                    (int) (y3 - h2 * cos(gamma + alfa)), (int) y3, (int) y4}, 4);
        gr.setColor(Color.black);
        gr.drawPolygon(new int[]{(int) (x4 + k * h1 * sin(gamma - alfa)),
                    (int) (x3 + k * h2 * sin(gamma + alfa)), (int) x3, (int) x4},
                new int[]{(int) (y4 - h1 * cos(gamma - alfa)),
                    (int) (y3 - h2 * cos(gamma + alfa)),
                    (int) y3, (int) y4}, 4);
    }

    protected void setVolumeWaterWithInnerObjects() {
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        ListIterator in = innerObjects.listIterator();
        MaterialObject t;
        double dV;
        dV = 0;
        while (in.hasNext()) {
            t = ((MaterialObject) in.next());
            dV = dV + t.getVolumeS(t.hDeep);
        }
        water.Vw = water.originalV + dV;
        if (PI / 2 - gamma - alfa <= 0) {
            water.Vw = 0;
            water.originalV = 0;
        }
    }

    protected void setStream() {
        if (outpouring()) {
            stream.isStream = true;
        } else {
            stream.isStream = false;
        }
    }

    protected void paintWater(Graphics gr) {
        setVolumeWaterWithInnerObjects();
        gr.setColor(water.color);
        setStream();
        if (gamma < 0) {//если наклон обратно против часовой стрелки
            if (flag == 1) {//если уже достаточный наклон
                paintWaterTriangle(gr, x4, y4, gamma);
            } else {
                paintWaterTetragon(gr, x4, y4, x3, y3, gamma);
            }
        } else//ecли наклон по часовой стрелке
        {
            if (flag == 1) {//если уже достаточный наклон
                paintWaterTriangle(gr, x3, y3, gamma);
            } else {
                paintWaterTetragon(gr, x3, y3, x4, y4, gamma);
            }
        }
    }

    protected void paintOval(Graphics gr) {
        double tg = dwidth / (height * 2.0);
        double alfa = atan(tg);
        gr.setColor(Color.decode("#F0FFFF"));
        gr.fillOval((int) (x), (int) (y - 5), width, 10);
        if (water.Vw != 0) {
            gr.setColor(water.color);
        } else {
            gr.setColor(Color.decode("#E0FFFF"));
        }
        gr.fillOval((int) (x + dwidth / 2),
                (int) (y + height - 5), width - dwidth, 10);
        if (!outpouring()) {
            gr.fillOval((int) (x + dwidth / 2 - calcwaterh1orl1() * sin(alfa)),
                    (int) (y + height - calcwaterh1orl1() * cos(alfa) - 5),
                    (int) (width - 2 * sin(alfa) * (height / cos(alfa)
                    - calcwaterh1orl1())), 10);
        } else {
            gr.fillOval((int) (x), (int) (y - 5), width, 10);
        }
        gr.setColor(Color.BLACK);
        gr.drawOval((int) (x + dwidth / 2),
                (int) (y + height - 5), width - dwidth, 10);
        gr.drawOval((int) (x), (int) (y - 5), width, 10);
        if (!outpouring()) {
            gr.drawOval((int) (x + dwidth / 2 - calcwaterh1orl1() * sin(alfa)),
                    (int) (y + height - calcwaterh1orl1() * cos(alfa) - 5),
                    (int) (width - 2 * sin(alfa) * (height / cos(alfa)
                    - calcwaterh1orl1())), 10);
        }
    }

    //в случае если стакан с трубками, следит за заполненностью трубок, они м б
    //или наполовину заполнены или не заполнены или полностью заполнены
    protected void SetFullnessPiping() {
        if (calcwaterh1orl1() > pipe1.height) {
            pipe1.isFill = true;
        } else {
            pipe1.isFill = false;
        }
        if (calcwaterh1orl1() > pipe2.height) {
            pipe2.isFill = true;
        } else {
            pipe2.isFill = false;
        }
        if (calcwaterh1orl1() == pipe1.height) {
            pipe1.isHalfFill = true;
        } else {
            pipe1.isHalfFill = false;
        }
        if (calcwaterh1orl1() == pipe2.height) {
            pipe2.isHalfFill = true;
        } else {
            pipe2.isHalfFill = false;
        }

    }

    @Override
    public float getMass() {
        mass = massGlass + (float) (water.Vw * water.ro);
        return mass;
    }

    public void swapWater() {
        if (water.Vw == 0) {
            if (isPrime) {
                sourceDopant.Vw = 0;
                water = sourceDopant;
            }
        }
    }

    @Override
    public void paintSelf(Graphics gr) {
        paintGlass(gr);
        if (existPipes) {
            SetFullnessPiping();
            pipe1.paintSelf(gr);
            pipe2.paintSelf(gr);
            if (pipe1.isTurnedOn && calcwaterh1orl1() > pipe1.height) {
                pipe1.stream.isStream = true;
                pipe1.stream.paintStreamPipe(gr, pipe1);
            } else {
                pipe1.stream.isStream = false;
            }
            if (pipe2.isTurnedOn && calcwaterh1orl1() > pipe2.height) {
                pipe2.stream.isStream = true;
                pipe2.stream.paintStreamPipe(gr, pipe2);
            } else {
                pipe2.stream.isStream = false;
            }
        }
        swapWater();
        if (gamma > PI / 2) {
            water.Vw = 0;
            water.originalV = 0;
        }
        if (water.Vw != 0) {
            paintWater(gr);
        } else {
            water.color = Color.WHITE;
        }
        if (!isIncline && gamma == 0) {
            paintOval(gr);
        }
        double xs1 = x + width / 2;
        double ys1 = y + height;
        if (existScale) {
            scale.paintScale(gr, (int) x, (int) y, (int) xs1, (int) ys1,
                    (int) scale.length, cx, cy, gamma);
        }
        stream.paintStreamGlass(gr, gamma, x1, x2, y1, y2);
        xs1 = x + width / 2;
        ys1 = y + height;
        if (existLabels) {
            scale.paintLabel(gr, (int) x, (int) y, (int) xs1, (int) ys1,
                    (int) scale.length, cx, cy, gamma);
        }
    }

    @Override
    public double getVolume(double hDeep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getVolumeS(double hDeep) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isOverGlass(Glass glass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
