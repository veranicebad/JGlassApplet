package package1;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import jvlab.objects.MaterialObject;
//import jvlab.objects.PhysObject;

/**
 * Класс гирьки
 * @author ytsuken
 */
public final class Weight extends MaterialObject {
    // Текст отображаемый на гирьке
    private String text;
    protected int size;
    /*
     * Constructor with all params. Maybe replaced by Builder in future.
     */

    public Weight(float mass, int x, int y, int z, int size, String text, boolean movable) {
        super(mass, x, y, size, size, movable);
        this.size = size;
        this.text = text;
    }

    /*
     * Отрисовка гирьки
     * Код Жеки.
     */
    @Override
    public void paintSelf(Graphics graphics) {
        // TODO: 
        graphics.setColor(Color.GRAY);
        int halfsize = size / 2;
        graphics.fillPolygon(new int[]{(int) x - halfsize / 2, (int) x - halfsize / 2,
                    (int) x - halfsize, (int) x + halfsize, (int) x + halfsize / 2,
                    (int) x + halfsize / 2}, new int[]{(int) y - 2 * halfsize - halfsize,
                    (int) y - 2 * halfsize - halfsize / 4, (int) y - 2 * halfsize + 1,
                    (int) y - 2 * halfsize + 1, (int) y - 2 * halfsize - halfsize / 4,
                    (int) y - 2 * halfsize - halfsize}, 6);

        graphics.setColor(Color.black);
        graphics.drawPolyline(new int[]{(int) x - halfsize, (int) x - halfsize,
                    (int) x - halfsize / 2, (int) x - halfsize / 2},
                new int[]{(int) y + 0, (int) y - 2 * halfsize,
                    (int) y - 2 * halfsize - halfsize / 4,
                    (int) y - 2 * halfsize - halfsize}, 4);
        graphics.drawPolyline(new int[]{(int) x + halfsize, (int) x + halfsize,
                    (int) x + halfsize / 2, (int) x + halfsize / 2},
                new int[]{(int) y + 0, (int) y - 2 * halfsize,
                    (int) y - 2 * halfsize - halfsize / 4,
                    (int) y - 2 * halfsize - halfsize}, 4);

        graphics.setColor(Color.GRAY);
        graphics.fillOval((int) x - (halfsize), (int) y
                - 2 * halfsize - halfsize - (halfsize / 10), (2 * halfsize) + 1,
                (halfsize / 3) + 1);
        graphics.fillRect((int) x - halfsize + 1, (int) y - 2 * halfsize + 1,
                2 * halfsize - 1, 2 * halfsize);
        graphics.fillArc((int) x - (halfsize), (int) y - (halfsize / 5),
                (2 * halfsize) + 1, 2 * halfsize / 5 + 1, -180, 180);

        graphics.setColor(Color.black);
        graphics.drawArc((int) x - (halfsize), (int) y - (halfsize / 5),
                (2 * halfsize), 2 * halfsize / 5, -180, 180);

        graphics.drawOval((int) x - (halfsize), (int) y - 2 * halfsize - halfsize
                - (halfsize / 10), (2 * halfsize), (halfsize / 3));

        //Font font = new Font("Serif", Font.BOLD, (int) (7 + size / 4.5));
        //graphics.setFont(font);
        graphics.drawString(text, (int) Math.round(x
                - (graphics.getFontMetrics().getStringBounds(text,
                graphics).getWidth()) / 2), (int) y);
    }

    // Пустые методы - нельзя ничего приаттачить к гире
    @Override
    public boolean attach(PhysObject child) {
        return false;
    }

    @Override
    public boolean detach(PhysObject child) {
        return false;
    }
    @Override
      public boolean isSelected(int x, int y) {
        if (x > this.x-this.size/2 && x < this.x + this.size/2) {
            if (y > this.y-this.size && y < this.y) {
                return true;
            }
        }
        return false;
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
