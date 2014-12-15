package package1;


import java.awt.Graphics;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author ver
 */
public class Stream {

    protected Water water;
    protected double x, y;
    protected int width, height;
    boolean isStream = false;

    public Stream(double x, double y, int width, int height, Water water) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.water = water;
    }

    protected void paintStreamGlass(Graphics gr, double gamma, double x1,
            double x2, double y1, double y2) {
        if (isStream == true) {
            gr.setColor(water.color);
            width = 10;
            if (gamma < 0) {
                x = x1;
                y = y1;
            } else {
                if (gamma == 0) {
                    width = 5;
                    x = x1;
                    y = y1;
                    gr.fillPolygon(new int[]{(int) x, (int) (x + width),
                                (int) (x + width - width / 3), (int) x + width / 3},
                            new int[]{(int) y, (int) y, (int) (y + height), (int) (y + height)}, 4);
                }
                x = x2;
                y = y2;
            }
            gr.fillPolygon(new int[]{(int) x, (int) (x + width),
                        (int) (x + width - width / 3), (int) x + width / 3},
                    new int[]{(int) y, (int) y, (int) (y + height), (int) (y + height)}, 4);
        }
    }

    protected void paintStreamTap(Graphics gr, Tap tap) {
        gr.setColor(water.color);
        if (isStream == true) {
            if (tap.right) {
                gr.fillPolygon(new int[]{(int) x, (int) (x + width),
                            (int) (x + width - width / 3), (int) x + width / 3},
                        new int[]{(int) y, (int) y, (int) (y + height), (int) (y + height)}, 4);
            } else {
                gr.fillPolygon(new int[]{(int) x, (int) (x - width),
                            (int) (x - width + width / 3), (int) x - width / 3},
                        new int[]{(int) y, (int) y, (int) (y + height), (int) (y + height)}, 4);
            }
        }
    }

    protected void paintStreamPipe(Graphics gr, Pipe pipe) {
        gr.setColor(water.color);
        x = pipe.x + pipe.length - 3;
        y = pipe.y - pipe.height - pipe.width / 2;
        //pipe.stream.width = 10;
        if (isStream == true) {
            gr.fillPolygon(new int[]{(int) x, (int) (x + width),
                        (int) (x + width - width / 3), (int) x + width / 3},
                    new int[]{(int) y, (int) y, (int) (y + height), (int) (y + height)}, 4);
        }
    }
}
