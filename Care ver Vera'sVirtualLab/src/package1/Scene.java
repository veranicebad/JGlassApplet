package package1;


import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.ListIterator;
import static java.lang.Math.*;

/**
 *
 * @author ver
 */
public class Scene {

    private int grabShiftX;
    private int grabShiftY;
    // Константа z индекса выбранного объекта на переднем плане
    public static final int SELECTED_OBJECT_Z = 100;
    private int xMouse, yMouse;
    private int width;
    private int height;
    Glass movedGlass = null;
    private int xb = 400, yb = 100, xb2 = 450, yb2 = 50;
    // Список объектов на сцене
    private final LinkedList<MaterialObject> objects =
            new LinkedList<MaterialObject>();
    private MaterialObject selectedObject = null;
    private MaterialObject lastSelectedObject = null;
    public boolean mouseClicked = false, goRight, goUp;
    private Sink sink = null;
    private Sink sink2 = null;
    //right sink up baulk
    private Baulk baulk = null;
    //right sink left baulk
    private Baulk baulk2 = null;
    //right sink right baulk
    private Baulk baulk3 = null;
    //left sink right baulk
    private Baulk baulk4 = null;
    //left sink up baulk
    private Baulk baulk5 = null;
    public double dR = 0, dG = 0, dB = 0;

    public Scene(int width, int height) {
        this.width = width;
        this.height = height;
        sink = new Sink(610, 400, 150, 50, true, null, false);
        sink.isExisting = true;
        sink2 = new Sink(205, 400, 150, 50, true, null, true);
        baulk = new Baulk(400, 100, 450, 50, -45);
        baulk2 = new Baulk(xb, yb, xb2, yb2, -45);
        baulk3 = new Baulk(400, 100, 450, 50, 45);
        baulk4 = new Baulk(400, 100, 450, 50, 45);
        baulk5 = new Baulk(400, 100, 450, 50, 45);
    }

    // Сортирует список объектов по z индексу
    private void sortByZ(List<MaterialObject> objects) {
        Collections.sort(objects, new Comparator<MaterialObject>() {

            @Override
            public int compare(MaterialObject o1, MaterialObject o2) {
                return o1.getZ() < o2.getZ() ? -1 : 1;
            }
        });
    }

    public void addObject(MaterialObject obj) {
        objects.add(obj);
    }

    public double getDR() {
        return dR;
    }

    public double getDG() {
        return dG;
    }

    public double getDB() {
        return dB;
    }

    public void setVisibleBaulkRight(Baulk baulk, Glass movedGlass, double xb,
            double xb2, double yb, double yb2) {
        baulk.setVariableBaulk(xb, xb2, yb, yb2);
        if ((mouseClicked) && (movedGlass != null)
                && (sqrt(pow((movedGlass.x2 - baulk.xb), 2)
                + pow((movedGlass.y2 + movedGlass.height / 2 - baulk.yb), 2)) < 200)) {
            if ((goRight) || (movedGlass.gamma != 0)) {
                if (baulk.xb >= movedGlass.x4) {
                    baulk.isVisible = true;
                } else {
                    baulk.isVisible = false;
                }
            } else {
                baulk.isVisible = false;
            }
        } else {
            baulk.isVisible = false;
        }
    }

    public void setVisibleBaulkLeft(Baulk baulk, Glass movedGlass, double xb,
            double xb2, double yb, double yb2) {
        baulk.setVariableBaulk(xb, xb2, yb, yb2);
        if ((mouseClicked) && (movedGlass != null)
                && (sqrt(pow((movedGlass.x1 - baulk.xb), 2) + pow((movedGlass.y1
                + movedGlass.height / 2 - baulk.yb), 2)) < 200)) {
            if ((!goRight) || (movedGlass.gamma != 0)) {
                if (baulk.xb <= movedGlass.x3) {
                    baulk.isVisible = true;
                } else {
                    baulk.isVisible = false;
                }
            } else {
                baulk.isVisible = false;
            }
        } else {
            baulk.isVisible = false;
        }
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.decode("#80c080"));//("#7CCD7C"));
        graphics.fillPolygon(new int[]{0, 0, 10, width - 10, width, width},
                new int[]{height - 60, height / 2 + 10, height / 2, height / 2,
                    height / 2 + 10, height - 60}, 6);
        graphics.setColor(Color.decode("#60a060"));
        graphics.fillRect(0, height - 60, width, 60);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, height / 2 + 10, 10, height / 2);
        graphics.drawLine(10, height / 2, width - 10, height / 2);
        graphics.drawLine(width - 10, height / 2, width, height / 2 + 10);
        graphics.drawLine(0, height - 60, width, height - 60);
        graphics.setColor(Color.BLUE);
//        paintButton(graphics);
        sink.paintSink(graphics);
        sink2.paintSink(graphics);
        for (MaterialObject t : objects) {
            if ((t instanceof Balance)) {
                t.paintSelf(graphics);
            }
        }
        if (sink2.isExisting) {
            baulk.paintBaulk(graphics);
            baulk2.paintBaulk(graphics);
            baulk3.paintBaulk(graphics);
        }
        baulk4.paintBaulk(graphics);
        baulk5.paintBaulk(graphics);
        //Z = 0;
        if (sink.isEmpty && sink.isExisting) {
            setVisibleBaulkRight(baulk2, movedGlass, sink.x, sink.x - 30,
                    sink.y - 15, sink.y - 45);
        }
        if (sink.isEmpty && sink.isExisting) {
            setVisibleBaulkLeft(baulk3, movedGlass, sink.x + sink.width + sink.height - 18,
                    sink.x + sink.width + sink.height - 18 + 30, sink.y - 15, sink.y - 45);
        }
        if (sink2.isEmpty && sink2.isExisting) {
            setVisibleBaulkLeft(baulk4, movedGlass, sink2.x + sink2.width + sink2.height - 18,
                    sink2.x + sink2.width + sink2.height - 18 + 30, sink2.y - 15, sink2.y - 45);
        }
        if (!sink.isEmpty && sink.isExisting) {
            setVisibleBaulkRight(baulk, movedGlass, sink.glass.x,
                    sink.glass.x - 30, sink.glass.y - 10, sink.glass.y - 40);
        }
        if (!sink2.isEmpty && sink2.isExisting) {
            setVisibleBaulkLeft(baulk5, movedGlass, sink2.glass.x2,
                    sink2.glass.x2 + 30, sink2.glass.y2 - 10, sink2.glass.y2 - 40);
        }
        for (MaterialObject t : objects) {
            if (!(t instanceof Glass) && !(t instanceof Balance)) {
                t.paintSelf(graphics);
            } else if (t instanceof Glass) {
                Glass gl = (Glass) t;
                t.paintSelf(graphics);
                //sortByZ(gl.innerObjects);
                for (Object inObj : gl.innerObjects) {
                    MaterialObject inMatObj = (MaterialObject) inObj;
                    inMatObj.paintSelf(graphics);
                }
            }
        }
    }

    public Glass getGlass() {
        ListIterator li = objects.listIterator();
        MaterialObject t;
        Glass glass = null;
        while (li.hasNext()) {
            t = ((MaterialObject) li.next());
            if (t instanceof Glass) {
                glass = (Glass) t;
            }
        }
        return glass;
    }

    public Glass getBigGlass() {
        ListIterator li = objects.listIterator();
        MaterialObject t;
        Glass glass = null;
        Glass bigGlass = null;
        while (li.hasNext()) {
            t = ((MaterialObject) li.next());
            if (t instanceof Glass) {
                glass = (Glass) t;
                if (glass.existPipes) {
                    bigGlass = glass;
                }
            }
        }
        return bigGlass;
    }

    public Tap getTap() {
        Collections.sort(objects, new Comparator<MaterialObject>() {

            public int compare(MaterialObject o1, MaterialObject o2) {
                int res;
                return res = o1.z - o2.z;
            }
        });
        ListIterator li = objects.listIterator();
        MaterialObject t;
        Tap tap = null;
        while (li.hasNext()) {
            t = ((MaterialObject) li.next());
            if (t instanceof Tap) {
                tap = (Tap) t;
            }
        }
        return tap;
    }

    public Tap getTap2() {
        Collections.sort(objects, new Comparator<MaterialObject>() {

            public int compare(MaterialObject o1, MaterialObject o2) {
                int res;
                return res = o2.z - o1.z;
            }
        });
        ListIterator li = objects.listIterator();
        MaterialObject t;
        Tap tap = null;
        while (li.hasNext()) {
            t = ((MaterialObject) li.next());
            if (t instanceof Tap) {
                tap = (Tap) t;
            }
        }
        return tap;
    }

    public Ball getBall() {
        ListIterator li = objects.listIterator();
        MaterialObject t;
        Ball ball = null;
        while (li.hasNext()) {
            t = ((MaterialObject) li.next());
            if (t instanceof Ball) {
                ball = (Ball) t;
            }
        }
        return ball;
    }

    public Sink getSink() {
        return sink;
    }

    public Baulk getBaulk() {
        return baulk;
    }

    public Baulk getBaulk2() {
        return baulk2;
    }

    public Baulk getBaulk3() {
        return baulk3;
    }

    public Baulk getBaulk4() {
        return baulk4;
    }

    public Baulk getBaulk5() {
        return baulk5;
    }

    public Sink getSink2() {
        return sink2;
    }
    public boolean mouseDown(Event evt, int x, int y, Graphics gr) {
        mouseClicked = true;
        MaterialObject obj;
        Iterator<MaterialObject> li = objects.descendingIterator();
        while (li.hasNext()) {
            obj = li.next();
            if (obj.isSelected(x, y)) {
                selectedObject = obj;
                if (selectedObject.parent != null) {
                    selectedObject.parent.detach(selectedObject);
                    System.out.println(obj + " is detached"); //Debug output
                }
                if (selectedObject instanceof Glass) {
                    movedGlass = (Glass) selectedObject;
                    if (!movedGlass.innerObjects.isEmpty()) {//чтобы доставать объекты из стакана
                        ListIterator in = movedGlass.innerObjects.listIterator();
                        MaterialObject t = null;
                        while (in.hasNext()) {
                            t = ((MaterialObject) in.next());
                            if (t.isSelected(x, y)) {
                                selectedObject = t;
                            }
                        }
                    }
                    if (sink.glass == selectedObject) {
                        sink.emptyingSink();
                    }
                    if (sink2.glass == selectedObject) {
                        sink2.emptyingSink();
                    }
                }
                getTap().z = SELECTED_OBJECT_Z;
                getTap2().z = SELECTED_OBJECT_Z;
                if (selectedObject instanceof Tap) {
                    Tap tp = (Tap) selectedObject;
                    if (tp == getTap()) {
                        tp.shiftTap(getSink());

                    } else {
                        tp.shiftTap(getSink2());
                    }
                }
                if (selectedObject instanceof Pipe) {
                    Pipe pp = (Pipe) selectedObject;
                    pp.shiftPipe();
                }
                if (selectedObject instanceof Ball) {
                    Ball bl;
                    bl = (Ball) selectedObject;
                    bl.v = 0;
                    bl.a = 0;
                    bl.isDiving = false;
                }
                if (selectedObject instanceof Cube) {
                    Cube cb;
                    cb = (Cube) selectedObject;
                    cb.v = 0;
                    cb.a = 0;
                    cb.isDiving = false;
                }
                xMouse = x;
                yMouse = y;
                grabShiftX = (int) (x - selectedObject.x);
                grabShiftY = (int) (y - selectedObject.y);
                if (!(selectedObject instanceof Balance)) {
                    selectedObject.setZ(SELECTED_OBJECT_Z);
                }
                sortByZ(objects);
                return true;
            }
        }
        return true;
    }

    public boolean isInSink(Glass movedGlass, Sink sink) {
        if (movedGlass.x4 > sink.x && movedGlass.x3 < sink.x + sink.width
                && movedGlass.y + movedGlass.height < sink.y + sink.height
                && movedGlass.y + movedGlass.height > sink.y && sink.isExisting) {
            return true;
        } else {
            return false;
        }
    }

    public void putInSink(Glass movedGlass, Sink sink) {
        if (sink.isEmpty) {
            sink.color = Color.WHITE;
            dR = 0;
            dG = 0;
            dB = 0;
            glassMoveTo(movedGlass, movedGlass.x = sink.x + sink.width / 2
                    - movedGlass.width / 2, sink.y + sink.height / 2 - movedGlass.height);
            sink.isEmpty = false;
            sink.glass = movedGlass;
        } else {
            glassMoveTo(movedGlass, movedGlass.defaultX, movedGlass.defaultY);
        }
    }

    public boolean mouseUp(Event evt, int x, int y) {
        mouseClicked = false;
        //
        if (selectedObject != null) {
            //System.out.println(selectedObject+" "+selectedObject.z);
            selectedObject.setZ(selectedObject.getDefaultZ());
            //System.out.println(selectedObject+" "+selectedObject.z);
            if (!(selectedObject instanceof Glass)) {
                if (selectedObject instanceof Ball) {
                    Ball bl;
                    bl = (Ball) selectedObject;
                    bl.isDiving = true;
                    if ((bl.x < 0 || bl.x + bl.width > width
                            || bl.y + bl.height < height / 2
                            || bl.y + bl.height > height - 60)
                            && (bl.outside == null)) {
                        ballMoveTo(bl, bl.defaultX, bl.defaultY);
                    }
                }
                if (selectedObject instanceof Cube) {
                    Cube cb;
                    cb = (Cube) selectedObject;
                    cb.isDiving = true;
                    if ((cb.x < 0 || cb.x + cb.width + cb.d > width
                            || cb.y + cb.height < height / 2
                            || cb.y + cb.height > height - 60)
                            && (cb.outside == null)) {
                        cubeMoveTo(cb, cb.defaultX, cb.defaultY);
                    }
                }
            }
            if (selectedObject instanceof Glass) {
                movedGlass.gamma = 0;
                if (isInSink(movedGlass, sink)) {
                    putInSink(movedGlass, sink);
                }
                if (isInSink(movedGlass, sink2)) {
                    if (movedGlass.height < getBigGlass().pipe1.height) {
                        putInSink(movedGlass, sink2);
                    }
                }
                if (movedGlass.gamma == 0) {
                    if (movedGlass.x + movedGlass.dwidth / 2 < 0
                            || movedGlass.x + movedGlass.width - movedGlass.dwidth / 2 > width
                            || movedGlass.y + movedGlass.height < height / 2
                            || movedGlass.y + movedGlass.height > height - 60) {
                        glassMoveTo(movedGlass, movedGlass.defaultX, movedGlass.defaultY);
                    }
                }
            }
            for (MaterialObject obj : objects) {
                if (selectedObject != obj && obj.isSelected(x, y)) {
                    if (obj.attach(selectedObject)) {
                        selectedObject = null;
                        return true;
                    }
                }
            }
            //%%%%
            if (selectedObject instanceof Weight) {
                selectedObject.moveToDefaultPlace();
            }
        }
        movedGlass = null;
        selectedObject = null;
        return true;
    }

    void glassMoveTo(Glass gl, double newX, double newY) {
        if (gl.movable) {
            gl.isIncline = false;
            if (!movedGlass.innerObjects.isEmpty()) {
                ListIterator in = movedGlass.innerObjects.listIterator();
                MaterialObject t;
                while (in.hasNext()) {
                    t = ((MaterialObject) in.next());
                    //t.z = gl.z + 1;
                    if (t instanceof Ball) {
                        Ball bl = (Ball) t;
                        ballMoveTo(bl, bl.x + newX - gl.x, bl.y + newY - gl.y);
                    }
                    if (t instanceof Cube) {
                        Cube cb = (Cube) t;
                        cubeMoveTo(cb, cb.x + newX - gl.x, cb.y + newY - gl.y);
                    }
                }
            }
            gl.y = newY;
            gl.x = newX;
        }
    }

    void matObjMoveTo(MaterialObject matObj, double newX, double newY) {
        if (matObj instanceof Ball) {
            Ball bl = (Ball) matObj;
            ballMoveTo(bl, newX, newY);
        }
        if (matObj instanceof Cube) {
            Cube cb = (Cube) matObj;
            cubeMoveTo(cb, newX, newY);
        }
    }

    void ballMoveTo(Ball bl, double newX, double newY) {
        if (bl.outside == null) {
            bl.y = newY;
            bl.x = newX;
        } else {
            Point p1 = new Point(bl.outside.x1, bl.outside.y1);
            Point p2 = new Point(bl.outside.x2, bl.outside.y2);
            Point p3 = new Point(bl.outside.x3, bl.outside.y3);
            Point p4 = new Point(bl.outside.x4, bl.outside.y4);
            Line l2 = new Line(p2, p3);
            Line l3 = new Line(p3, p4);
            Line l4 = new Line(p4, p1);
            if (l2.calcDistance(newX + bl.width / 2, newY + bl.height / 2) >= bl.width / 2
                    && l3.calcDistance(newX + bl.width / 2, newY + bl.height / 2) >= bl.width / 2
                    && l4.calcDistance(newX + bl.width / 2, newY + bl.height / 2) >= bl.width / 2) {
                bl.y = newY;
                bl.x = newX;
            }
        }
    }

    void cubeMoveTo(Cube cb, double newX, double newY) {
        if (cb.outside == null) {
            cb.y = newY;
            cb.x = newX;
        } else {
            Point p1 = new Point(cb.outside.x1, cb.outside.y1);
            Point p2 = new Point(cb.outside.x2, cb.outside.y2);
            Point p3 = new Point(cb.outside.x3, cb.outside.y3);
            Point p4 = new Point(cb.outside.x4, cb.outside.y4);
            Line l2 = new Line(p2, p3);
            Line l3 = new Line(p3, p4);
            Line l4 = new Line(p4, p1);
            if (l2.calcDistance(newX, newY + cb.height) >= cb.width + cb.d
                    && l3.calcDistance(newX, newY) >= cb.height
                    && l4.calcDistance(newX + cb.width, newY + cb.height) >= cb.width) {
                cb.y = newY;
                cb.x = newX;
                
            } 
        }
    }

    void weightMoveTo(Weight weight, double newX, double newY) {
        weight.x = newX;
        weight.y = newY;
    }

    public boolean contactLeft(Glass gl) {
        if (gl.gamma <= 0) {
            if (baulk3.isVisible) {
                return baulk3.isCrossed(gl.x4, gl.y4, gl.x1, gl.y1);
            } else {
                if (baulk4.isVisible) {
                    return baulk4.isCrossed(gl.x4, gl.y4, gl.x1, gl.y1);
                } else {
                    if (baulk5.isVisible) {
                        return baulk5.isCrossed(gl.x4, gl.y4, gl.x1, gl.y1);
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    public boolean contactRight(Glass gl) {
        if (gl.gamma >= 0) {
            if (baulk2.isVisible) {
                return baulk2.isCrossed(gl.x3, gl.y3, gl.x2, gl.y2);
            } else {
                if (baulk.isVisible) {
                    return baulk.isCrossed(gl.x3, gl.y3, gl.x2, gl.y2);
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public void inclineGlassOnBaulkLeft(Glass gl) {
        if (baulk.isVisible) {
            gl.cx = baulk.crossedX(gl.x2, gl.y2, gl.x3, gl.y3);
            gl.cy = baulk.crossedY(gl.x2, gl.y2, gl.x3, gl.y3);
        } else {
            if (contactLeft(gl)) {
                if (baulk3.isVisible) {
                    gl.cx = baulk3.crossedX(gl.x1, gl.y1, gl.x4, gl.y4);
                    gl.cy = baulk3.crossedY(gl.x1, gl.y1, gl.x4, gl.y4);
                } else {
                    if (baulk4.isVisible) {
                        gl.cx = baulk4.crossedX(gl.x1, gl.y1, gl.x4, gl.y4);
                        gl.cy = baulk4.crossedY(gl.x1, gl.y1, gl.x4, gl.y4);
                    } else {
                        if (baulk5.isVisible) {
                            gl.cx = baulk5.crossedX(gl.x1, gl.y1, gl.x4, gl.y4);
                            gl.cy = baulk5.crossedY(gl.x1, gl.y1, gl.x4, gl.y4);
                        }
                    }
                }
            } else {
                gl.cx = baulk2.crossedX(gl.x2, gl.y2, gl.x3, gl.y3);
                gl.cy = baulk2.crossedY(gl.x2, gl.y2, gl.x3, gl.y3);
            }
        }
        if (baulk4.isVisible || baulk2.isVisible || baulk3.isVisible) {
            gl.isIncline = true;
            gl.gamma = gl.gamma - 0.04;
        } else {
            if (gl.water.isMixing) {
                if (getSink().glass != null) {
                    if (getSink().glass.water.isMixing && baulk.isVisible) {
                        gl.isIncline = true;
                        gl.gamma = gl.gamma - 0.04;
                    }
                } else {
                    if (getSink2().glass != null) {
                        if (getSink2().glass.water.isMixing && baulk5.isVisible) {
                            gl.isIncline = true;
                            gl.gamma = gl.gamma - 0.04;
                        }
                    } else {
                        gl.isIncline = true;
                        gl.gamma = gl.gamma - 0.04;
                    }
                }
            }
        }
    }

    public void inclineGlassOnBaulkRight(Glass gl) {
        if (baulk.isVisible) {
            gl.cx = baulk.crossedX(gl.x2, gl.y2, gl.x3, gl.y3);
            gl.cy = baulk.crossedY(gl.x2, gl.y2, gl.x3, gl.y3);
        } else {
            if (contactRight(gl)) {
                gl.cx = baulk2.crossedX(gl.x2, gl.y2, gl.x3, gl.y3);
                gl.cy = baulk2.crossedY(gl.x2, gl.y2, gl.x3, gl.y3);
            } else {
                if (baulk3.isVisible) {
                    gl.cx = baulk3.crossedX(gl.x1, gl.y1, gl.x4, gl.y4);
                    gl.cy = baulk3.crossedY(gl.x1, gl.y1, gl.x4, gl.y4);
                } else {
                    if (baulk4.isVisible) {
                        gl.cx = baulk4.crossedX(gl.x1, gl.y1, gl.x4, gl.y4);
                        gl.cy = baulk4.crossedY(gl.x1, gl.y1, gl.x4, gl.y4);
                    } else {
                        if (baulk5.isVisible) {
                            gl.cx = baulk5.crossedX(gl.x1, gl.y1, gl.x4, gl.y4);
                            gl.cy = baulk5.crossedY(gl.x1, gl.y1, gl.x4, gl.y4);
                        }
                    }
                }
            }
        }
        if (baulk4.isVisible || baulk2.isVisible || baulk3.isVisible) {
            gl.isIncline = true;
            gl.gamma = gl.gamma + 0.04;
        } else {
            if (gl.water.isMixing) {
                if (getSink().glass != null) {
                    if (getSink().glass.water.isMixing && baulk.isVisible) {
                        gl.isIncline = true;
                        gl.gamma = gl.gamma + 0.04;
                    }
                } else {
                    if (getSink2().glass != null) {
                        if (getSink2().glass.water.isMixing && baulk5.isVisible) {
                            gl.isIncline = true;
                            gl.gamma = gl.gamma + 0.04;
                        }
                    } else {
                        gl.isIncline = true;
                        gl.gamma = gl.gamma + 0.04;
                    }

                }
            }
        }
    }

    public boolean mouseDrag(Event evt, int x, int y, Graphics gr) {
        if (xMouse > x) {
            goRight = false;
        } else {
            goRight = true;
        }
        if (yMouse > y) {
            goUp = true;
        } else {
            goUp = false;
        }
        if (selectedObject instanceof Glass) {
            Glass gl;
            gl = movedGlass;
            if (abs(gl.gamma) < 1e-6) {
                gl.gamma = 0;
            }
            if (gl.gamma == 0) {
                if ((!contactLeft(gl)) && (!contactRight(gl))) {
                    glassMoveTo(gl, x - grabShiftX, y - grabShiftY);
                } else {
                    if ((xMouse < x) && (contactLeft(gl))) {
                        glassMoveTo(gl, x - grabShiftX, y - grabShiftY);
                    }
                    if ((xMouse > x) && (contactRight(gl))) {
                        glassMoveTo(gl, x - grabShiftX, y - grabShiftY);
                    }
                    if ((xMouse < x) && (contactRight(gl))) {
                        glassMoveTo(gl, (int) gl.x, y - grabShiftY);
                        inclineGlassOnBaulkRight(gl);
                    }
                    if ((xMouse > x) && (contactLeft(gl))) {
                        glassMoveTo(gl, (int) gl.x, y - grabShiftY);
                        inclineGlassOnBaulkLeft(gl);
                    }
                }
            } else {
                glassMoveTo(gl, (int) gl.x, y - grabShiftY);
                if ((!contactLeft(gl)) && (!contactRight(gl))) {
                    glassMoveTo(gl, x - grabShiftX, y - grabShiftY);
                    gl.gamma = 0;
                }
                if ((xMouse > x) && (contactLeft(gl))) {
                    inclineGlassOnBaulkLeft(gl);
                }
                if ((xMouse < x) && (contactLeft(gl))) {
                    inclineGlassOnBaulkRight(gl);
                }
                if ((xMouse > x) && (contactRight(gl))) {
                    inclineGlassOnBaulkLeft(gl);
                }
                if ((xMouse < x) && (contactRight(gl))) {
                    inclineGlassOnBaulkRight(gl);
                }
            }
        }

        if (selectedObject instanceof Ball) {
            Ball bl;
            bl = (Ball) selectedObject;
            if (bl.outside != null) {
                if (bl.y + bl.height < bl.outside.y && goUp) {
                    bl.outside.innerObjects.remove(bl);
                    bl.outside = null;
                }
            }
            ballMoveTo(bl, x - grabShiftX, y - grabShiftY);
        }
        if (selectedObject instanceof Cube) {
            Cube cb;
            cb = (Cube) selectedObject;
            if (cb.outside != null) {
                if (cb.y + cb.height < cb.outside.y && goUp) {
                    cb.outside.innerObjects.remove(cb);
                    cb.outside = null;
                }
            }
            cubeMoveTo(cb, x - grabShiftX, y - grabShiftY);
        }
        if (selectedObject instanceof Weight) {
            Weight wght;
            wght = (Weight) selectedObject;
            weightMoveTo(wght, x - grabShiftX, y - grabShiftY);
        }
        xMouse = x;
        yMouse = y;
        return true;
    }
}
