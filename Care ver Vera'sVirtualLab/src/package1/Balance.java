package package1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ver
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Timer;
//import jvlab.objects.MaterialObject;
//import jvlab.objects.PhysObject;

/**
 * Класс весов. Являются материальным объектом. Можно добавлять другие объекты
 * (например гирьки) на правую и левую чаши весов. По умолчанию весы неподвижны.
 * @author ytsuken
 * @author xes
 * @version 1.0
 * @see MaterialObject
 */
public final class Balance extends MaterialObject {

    // Позиция весов: -1..1, где -1 соответствует перевесу левой чаши,
    // 1 - перевесу правой.
    private double position = 0;
    // Вес левой чаши
    private int leftWeight = 0;
    // Вес правой чаши
    private int rightWeight = 0;
    // Текущие объекты на левой чаше
    private List<MaterialObject> atLeft = new LinkedList<MaterialObject>();
    // Текущие объекты на правой чаше
    private List<MaterialObject> atRight = new LinkedList<MaterialObject>();
    // Таймер для анимации
    private Timer timer;
    // Шаг изменения position при анимации
    private final float ANIMATION_STEP = 0.05f;
    // Промежуток между кадрами анимации (задает частоту анимации)
    private final int TIMER_DELAY = 50;
    private int lastChildZ = 0;

    /**
     * Конструктор весов.
     * @param graphics Холст, где происходит отображение весов.
    //* @param mass Масса весов
     * @param x Х координата весов
     * @param y У координата весов
     * @param width Ширина весов
     * @param height Высота весов
     */
    public Balance(float mass, double x, double y, int z, int width, int height, boolean movable) {
        super(mass, x, y, width, height, movable);
        //setMovable(false);
        setZ(-1);
        this.defaultZ=-1;
        timer = new Timer(TIMER_DELAY, new TimerListener());
        // Запуск анимации
        timer.start();
    }

    /**
     * Код отрисовки весов от Жеки.
     */
    @Override
    public void paintSelf(Graphics graphics) {
        int dx = 8;
        int d = 2;
        int l = width / 4;//плечо
        //pozition=-1..1
        int x1 = (int) Math.round(l * Math.cos(position / 10));
        int y1 = (int) (Math.round(l * Math.sin(position / 10)));

        graphics.setColor(Color.BLACK);
        graphics.fillPolygon(new int[]{(int) (x - width / 2 - 2 * dx),
                    (int) (x - width / 2 + 2 * dx), (int) (x + width / 2 - 2 * dx),
                    (int) (x + width / 2 + 2 * dx)},
                new int[]{(int) (y + 2 * dx), (int) (y - 2 * dx),
                    (int) (y - 2 * dx), (int) (y + 2 * dx)}, 4);
        graphics.setColor(Color.GRAY);
        graphics.fillRect((int) (x - dx), (int) (y - height), (int) (dx * 2), height);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillPolygon(new int[]{(int) (x - dx), (int) x, (int) (x + dx)},
                new int[]{(int) (y + 2 * dx - height), (int) (y + dx - height),
                    (int) (y + 2 * dx - height)}, 3);
        graphics.setColor(Color.GRAY);
        graphics.fillPolygon(new int[]{(int) x - x1, (int) x - x1, (int) x + x1, (int) x + x1},
                new int[]{(int) y - height + y1 + d + dx, (int) y - height + y1 - d + dx,
                    (int) y - height - y1 - d + dx, (int) y - height - y1 + d + dx}, 4);

        int lp = l - dx * 2;
        graphics.setColor(Color.gray);
        graphics.fillOval((int) x - x1 - lp - 2 * d, (int) y + y1 - dx - 2 * d,
                lp * 2 + 4 * d, 2 * dx + 4 * d);
        graphics.fillOval((int) x + x1 - lp - 2 * d, (int) y - y1 - dx - 2 * d, lp * 2
                + 4 * d, 2 * dx + 4 * d);
        graphics.setColor(Color.black);
        graphics.fillOval((int) x - x1 - lp, (int) y + y1 - dx, lp * 2, 2 * dx);
        graphics.fillOval((int) x + x1 - lp, (int) y - y1 - dx, lp * 2, 2 * dx);
        graphics.setColor(Color.gray);
        graphics.fillRect((int) x + x1, (int) y - y1 - height + dx, d * 2, l / 2);
        graphics.fillRect((int) x - x1, (int) y + y1 - height + dx, d * 2, l / 2);

        graphics.fillRect((int) x + x1 + lp + 1 - d, (int) y - y1 - height + dx + l * 3 / 4,
                d * 2, height - dx - l * 3 / 4);
        graphics.fillRect((int) x - x1 + lp + 1 - d, (int) y + y1 - height + dx + l * 3 / 4,
                d * 2, height - dx - l * 3 / 4);
        graphics.fillRect((int) x + x1 - lp - 1 - d, (int) y - y1 - height + dx + l * 3 / 4,
                d * 2, height - dx - l * 3 / 4);
        graphics.fillRect((int) x - x1 - lp - 1 - d, (int) y + y1 - height + dx + l * 3 / 4,
                d * 2, height - dx - l * 3 / 4);

        int y2 = (int) y - height + dx + l / 2;
        graphics.fillPolygon(new int[]{(int) x - x1 + d, (int) x - x1 + d, (int) x - x1 - lp - d,
                    (int) x - x1 - lp - d}, new int[]{y2 + d + y1, y2 - d + y1, y2 + l / 4 - d + y1,
                    y2 + l / 4 + d + y1}, 4);
        graphics.fillPolygon(new int[]{(int) x - x1 + d, (int) x - x1 + d, (int) x - x1 + lp + d,
                    (int) x - x1 + lp + d}, new int[]{y2 + d + y1, y2 - d + y1, y2 + l / 4 - d + y1,
                    y2 + l / 4 + d + y1}, 4);
        graphics.fillPolygon(new int[]{(int) x + x1 + d, (int) x + x1 + d, (int) x + x1 - lp - d,
                    (int) x + x1 - lp - d}, new int[]{y2 + d - y1, y2 - d - y1, y2 + l / 4 - d - y1,
                    y2 + l / 4 + d - y1}, 4);
        graphics.fillPolygon(new int[]{(int) x + x1 + d, (int) x + x1 + d, (int) x + x1 + lp + d,
                    (int) x + x1 + lp + d}, new int[]{y2 + d - y1, y2 - d - y1, y2 + l / 4 - d - y1,
                    y2 + l / 4 + d - y1}, 4);

    }

    @Override
    public boolean isSelected(int x, int y) {
        float k = 0.1f;
        int dx = (int) (width * (0.5 + k));
        int dy = (int) (height * k);
        if (x > this.x - dx && x < this.x + dx) {
            if (y < this.y + dy && y > this.y - (height + dy)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean attach(PhysObject child) {
        if (child instanceof MaterialObject) {
            MaterialObject matChild = (MaterialObject) child;
            int k1 = 4;
            float k2 = 0.1f;
            int dx = (int) (width * k2);
            if (matChild.getX() < x - dx && matChild.getX() > x - k1 * dx) {
                // Объект идет на левую чашу
                leftWeight += matChild.getMass();
                atLeft.add(matChild);
            } else if (matChild.getX() > x + dx && matChild.getX() < x + k1 * dx) {
                // Объект идет на правую чашу
                rightWeight += matChild.getMass();
                atRight.add(matChild);
            } else {
                // Промах
                return false;
            }
            //System.out.println("Selected object z now is " + lastChildZ);
            super.attach(child); // Вызывать только при действительном аттаче
            matChild.setZ(lastChildZ++);
            if (matChild instanceof Weight) {
                matChild.setY((int) y);
            } else {
                matChild.setY((int) y-matChild.height);
            }
        }
        System.out.println("Balance position is " + position);
        System.out.printf("And weights are: %d %d %n", leftWeight, rightWeight);
        return true;
    }

    @Override
    public boolean detach(PhysObject child) {
        super.detach(child);
        if (child instanceof MaterialObject) {
            MaterialObject matChild = (MaterialObject) child;
            matChild.setZ(matChild.getDefaultZ());
            //lastChildZ--;
            if (matChild.getX() < x) {
                leftWeight -= matChild.getMass();
                atLeft.remove(matChild);
            } else {
                rightWeight -= matChild.getMass();
                atRight.remove(matChild);
            }
        }
        System.out.println("Balance position is " + position);
        System.out.printf("And weights are: %d %d %n", leftWeight, rightWeight);
        return true;
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

    // Класс-слушатель событий таймера
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (leftWeight < rightWeight) {
                if (position > -1.0f) {
                    position = position - ANIMATION_STEP;
                }
            } else if (leftWeight == rightWeight) {
                if (Math.abs(position) > 2 * ANIMATION_STEP) {

                    if (position > 0) {
                        position = position - ANIMATION_STEP;
                    } else {
                        position = position + ANIMATION_STEP;
                    }
                }
            } else {
                if (position < 1.0f) {
                    position = position + ANIMATION_STEP;
                }
            }
            // Теперь нужно сдвинуть координаты дочерних объектов.
            // Они должны двигаться вместе с чашами.
            int l = width / 4;//плечо
            int dy = (int) (l * Math.sin(position / 10));
            for (MaterialObject mo : atLeft) {
                if (mo instanceof Weight) {
                    mo.setY((int) y + dy);
                } else {
                    mo.setY((int) y + dy - mo.height);
                }
            }
            for (MaterialObject mo : atRight) {
                if (mo instanceof Weight) {
                    mo.setY((int) y - dy);
                } else {
                    mo.setY((int) y - dy - mo.height);
                }
            }
        }
    }
}
