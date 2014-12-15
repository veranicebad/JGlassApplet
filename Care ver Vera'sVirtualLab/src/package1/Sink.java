package package1;


import static java.lang.Math.*;
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Катя
 */
public class Sink {

    int x, y, width, height;
    boolean isEmpty;
    Glass glass;
    Color color;
    boolean isExisting;

    public Sink(int x, int y, int width, int height, boolean isVoid, Glass glass,
            boolean isExist) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.isEmpty = isVoid;
        this.glass = glass;
        this.isExisting = isExist;
    }

    public void emptyingSink() {
        glass = null;
        isEmpty = true;
    }

    protected void paintSink(Graphics gr) {
        if (isExisting) {
            gr.setColor(Color.WHITE);
            gr.fillPolygon(new int[]{x, x + width + height, x + width, x - height},
                    new int[]{y, y, y + height, y + height}, 4);
            gr.setColor(color);
            gr.fillPolygon(new int[]{x, x + width + height - 18, x + width, x - height + 18},
                    new int[]{y + 18, y + 18, y + height, y + height}, 4);
            gr.setColor(Color.BLACK);
            gr.drawPolygon(new int[]{x, x + width + height, x + width, x - height},
                    new int[]{y, y, y + height, y + height}, 4);
            gr.drawLine(x, y, x, y + 18);
            gr.drawLine(x, y + 18, x + width + height - 18, y + 18);
            gr.drawLine(x, y + 18, x - height + 18, y + height);
            gr.setColor(Color.BLACK);
            gr.fillOval(x, y + 20, 15, 7);
        }
    }
}
