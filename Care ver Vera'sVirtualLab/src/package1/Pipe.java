package package1;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import static java.lang.Math.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ver
 */
public class Pipe extends MaterialObject {

    protected int length;
    protected boolean isTurnedOn, isFill = false, isHalfFill = false;
    protected Stream stream;

    public Pipe(float mass,double x, double y, int width, int height,
            boolean movable, int length, boolean isInclude, Stream stream) {
        super(mass, x, y, width, height, movable);
        this.length = length;
        this.isTurnedOn = isInclude;
        this.stream = stream;
        this.z = 0;
    }

    public void shiftPipe() {
        if (isTurnedOn == false) {
            isTurnedOn = true;
        } else {
            isTurnedOn = false;
        }
    }

    protected void paintSelf(Graphics gr) {
        Graphics2D gr2D = (Graphics2D) gr;
        AffineTransform at = new AffineTransform();
        if (isFill) {
            gr.setColor(stream.water.color);
        } else {
            gr.setColor(Color.decode("#E0FFFF"));
        }
        gr.fillPolygon(new int[]{(int) x, (int) x + length,
                    (int) x + length, (int) x},
                new int[]{(int) y - height - width,
                    (int) y - height - width, (int) y - height,
                    (int) y - height}, 4);
        if (isHalfFill) {
            gr.setColor(stream.water.color);
            gr.fillPolygon(new int[]{(int) x, (int) x + length,
                        (int) x + length, (int) x},
                    new int[]{(int) y - height - 2 * width / 5,
                        (int) y - height - 2 * width / 5, (int) y - height,
                        (int) y - height}, 4);
        }
        gr.setColor(Color.black);
        gr.drawPolygon(new int[]{(int) x, (int) x + length,
                    (int) x + length, (int) x},
                new int[]{(int) y - height - width,
                    (int) y - height - width, (int) y - height,
                    (int) y - height}, 4);
        if (isFill) {
            gr.setColor(stream.water.color);
        } else {
            gr.setColor(Color.decode("#E0FFFF"));
        }
        gr.fillOval((int) x - 3, (int) y - height - width, 6, width);
        gr.setColor(Color.decode("#E0FFFF"));
        gr.fillOval((int) x + length - 3, (int) y - height - width, 6, width);
        if (isTurnedOn) {
            at.setToRotation(-45, x + 10 + 2, y - height - width - 2);
            gr2D.setTransform(at);
        }
        gr2D.fillRect((int) x + 11, (int) y - height - width - 3, 3, width + 3);
        gr2D.fillRect((int) x + 11, (int) y - height - width - 4, width + 3, 3);
        if (isTurnedOn) {
            at.setToRotation(0, x + 10 + 2, y - height - width - 2);
            gr2D.setTransform(at);
        }
        gr.setColor(Color.black);
        gr.drawOval((int) x + length - 3, (int) y - height - width, 6, width);
        gr.drawArc((int) x - 3, (int) y - height - width, 6, width, 90, 180);
        if (isTurnedOn) {
            at.setToRotation(-45, x + 10 + 2, y - height - width - 2);
            gr2D.setTransform(at);
        }
        gr2D.drawRect((int) x + 11, (int) y - height - width - 3, 3, width + 3);
        gr2D.drawRect((int) x + 11, (int) y - height - width - 4, width + 3, 3);
        if (isTurnedOn) {
            at.setToRotation(0, x + 10 + 2, y - height - width - 2);
            gr2D.setTransform(at);
        }
        gr.setColor(Color.decode("#E0FFFF"));
        gr.fillOval((int) x + 10, (int) y - height - width - 4, 4, 4);
        gr.setColor(Color.black);
        gr.drawOval((int) x + 10, (int) y - height - width - 4, 4, 4);
    }

    @Override
    public boolean isSelected(int x, int y) {
        if (sqrt(pow(x - (this.x + 10), 2) + pow(y - (this.y - this.height
                - this.width - 2), 2)) <= 10) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getVolume(double hDeep) {
        return 0;
    }

    @Override
    public double getVolumeS(double hDeep) {
        return 0;
    }

    @Override
    public boolean isOverGlass(Glass glass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
