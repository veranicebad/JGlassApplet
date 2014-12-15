package package1;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.management.MBeanAttributeInfo;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Катя
 */
public class Tap extends MaterialObject {

    boolean isTurnedOn = false;
    Stream stream;
    boolean right, isExisting;

    public Tap(float mass, double x, double y, int width, int height, Stream stream,
            boolean movable, boolean right, boolean isExist) {
        super(mass, x, y, width, height, movable);
        this.stream = stream;
        this.right = right;
        this.z = 400;
        this.defaultZ = 400;
        this.isExisting = isExist;
    }

        public void shiftTap(Sink snk) {
        if (isTurnedOn == false) {
            if (snk.glass != null) {
                if (snk.glass.water.isMixing) {
                    isTurnedOn = true;
                }
            } else {
                isTurnedOn = true;
            }
        } else {
            isTurnedOn = false;
        }
    }


    @Override
    public boolean isSelected(int x, int y) {
        if (isExisting) {
            if (right) {
                if ((x > this.x + 40 && x < this.x + 60 && y > this.y - 20 && y < this.y)
                        || (x > this.x + 15 && x < this.x + 85 && y > this.y - 27 && y < this.y - 20)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if ((x > this.x - 60 && x < this.x - 40 && y > this.y - 20 && y < this.y)
                        || (x > this.x - 85 && x < this.x - 15 && y > this.y - 27 && y < this.y - 20)) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    @Override
    public boolean detach(PhysObject child) {
        return false;
    }

    @Override
    public boolean attach(PhysObject child) {
        return false;
    }

    @Override
    protected void paintSelf(Graphics graphics) {
        if (isExisting) {
            if (isTurnedOn == true) {
                stream.isStream = true;
            } else {
                stream.isStream = false;
            }
            stream.paintStreamTap(graphics, this);
            Graphics2D gr2D = (Graphics2D) graphics;
            BasicStroke pen1 = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
            gr2D.setColor(Color.BLACK);
            BasicStroke pen3 = new BasicStroke(1);
            gr2D.setStroke(pen3);
//        if (right) {
//            gr2D.drawOval((int) x, (int) y + height - 5, width, 10);
//        } else {
//            gr2D.drawOval((int) x - width, (int) y + height - 5, width, 10);
//        }
            graphics.setColor(Color.decode("#BDB76B"));
            gr2D.setStroke(pen1);
            if (right) {
                gr2D.drawLine((int) x + width / 2, (int) y + width / 2, (int) x + width / 2, (int) y + height);
            } else {
                gr2D.drawLine((int) x - width / 2, (int) y + width / 2, (int) x - width / 2, (int) y + height);
            }
            BasicStroke pen2 = new BasicStroke(width, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER);
            gr2D.setStroke(pen2);
            if (right) {
                gr2D.drawLine((int) x + width / 2, (int) y + width / 2, (int) x + width / 2 + 250, (int) y + width / 2);
            } else {
                gr2D.drawLine((int) x - width / 2, (int) y + width / 2, (int) x - width / 2 - 250, (int) y + width / 2);
            }
            gr2D.setStroke(pen3);
            gr2D.setColor(Color.BLACK);
            if (right) {
                gr2D.drawArc((int) x, (int) y, width, width,
                        (int) (90), (int) (90));
                gr2D.drawLine((int) x, (int) y + width / 2, (int) x, (int) y + height);
                gr2D.drawLine((int) x + width / 2, (int) y, (int) x + 250, (int) y);
                gr2D.drawLine((int) x + width, (int) y + width, (int) x + width, (int) y + height);
                gr2D.drawLine((int) x + width, (int) y + width, (int) x + 250, (int) y + width);
            } else {
                gr2D.drawArc((int) x - width, (int) y, width, width,
                        (int) (0), (int) (90));
                gr2D.drawLine((int) x, (int) y + width / 2, (int) x, (int) y + height);
                gr2D.drawLine((int) x - width / 2, (int) y, (int) x - 250, (int) y);
                gr2D.drawLine((int) x - width, (int) y + width, (int) x - width, (int) y + height);
                gr2D.drawLine((int) x - width, (int) y + width, (int) x - 250, (int) y + width);
            }
            gr2D.setColor(Color.decode("#CDC775"));
            if (right) {
                gr2D.fillPolygon(new int[]{(int) x + 40, (int) x + 40, (int) x + 15, (int) x + 15,
                            (int) x + 85, (int) x + 85, (int) x + 60, (int) x + 60},
                        new int[]{(int) y, (int) y - 20, (int) y - 20, (int) y - 27, (int) y - 27,
                            (int) y - 20, (int) y - 20, (int) y}, 8);
            } else {
                gr2D.fillPolygon(new int[]{(int) x - 40, (int) x - 40, (int) x - 15, (int) x - 15,
                            (int) x - 85, (int) x - 85, (int) x - 60, (int) x - 60},
                        new int[]{(int) y, (int) y - 20, (int) y - 20, (int) y - 27, (int) y - 27,
                            (int) y - 20, (int) y - 20, (int) y}, 8);
            }
            graphics.setColor(Color.decode("#BDB76B"));
            if (right) {
                gr2D.fillOval((int) x, (int) y + height - 5, width, 8);
            } else {
                gr2D.fillOval((int) x - width, (int) y + height - 5, width, 8);
            }
            graphics.setColor(Color.BLACK);
            if (right) {
                gr2D.drawPolygon(new int[]{(int) x + 40, (int) x + 40, (int) x + 15, (int) x + 15,
                            (int) x + 85, (int) x + 85, (int) x + 60, (int) x + 60},
                        new int[]{(int) y, (int) y - 20, (int) y - 20, (int) y - 27, (int) y - 27,
                            (int) y - 20, (int) y - 20, (int) y}, 8);
            } else {
                gr2D.drawPolygon(new int[]{(int) x - 40, (int) x - 40, (int) x - 15, (int) x - 15,
                            (int) x - 85, (int) x - 85, (int) x - 60, (int) x - 60},
                        new int[]{(int) y, (int) y - 20, (int) y - 20, (int) y - 27, (int) y - 27,
                            (int) y - 20, (int) y - 20, (int) y}, 8);
            }
            if (right) {
                gr2D.drawArc((int) x, (int) y + height - 5, width, 8, 180, 180);
            } else {
                gr2D.drawArc((int) x - width, (int) y + height - 5, width, 8, 180, 180);
            }
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
