package package1;


import static java.lang.Math.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/*
Класс шариков
 */
/**
 *
 * @author Катя
 */
public class Ball extends MaterialObject {
    //стакан, в котором находится шарик, если шарик не погружен в какой-либо
    //стакан, это поле имеет значение null

    //public Glass outside = null;
    //isDiving == true если идёт погружение в какой-либо стакан
   // public boolean isDiving = false;
    //isInList == true если шарик уже есть в списке innerObjects стакана,
    //в котором он находится(outside)
    //public boolean isInList = false;
    //плотность, ускорение, скорость, глубина погружения(считается от уровня
    //воды в стакане, в который погружен шарик, до нижней точки шарика, если шар
    //погружен полностью hDeep=диаметру шарика)
    public double ro;

    public Ball(float mass, double x, double y, int width, int height, double ro, boolean movable) {
        super(mass, x, y, width, height, movable);
        this.ro = ro;
        //setMass();
    }

    @Override
    public boolean isSelected(int x, int y) {
        if (sqrt(pow(this.x + this.width / 2 - x, 2) + pow(this.y + this.height / 2 - y, 2)) <= this.width / 2) {
            return true;
        } else {
            return false;
        }
    }
//возвращает объем фигуры, отсеченной плоскостью от шара, по высоте, отсчитываемой
//от точки на сфере до плоскости
    @Override
    public double getVolume(double hDeep) {
        double v;
        v = PI * hDeep * hDeep * (width / 2 - hDeep / 3);
        return v;
    }
// устанавливает массу

    public void setMass() {
        mass = (float) (ro * getVolume(width));
    }
//возвращает площадь кусочка круга, отсечённого прямой, на высоте H от точки на окружности
@Override
    public double getVolumeS(double H) {
        double r = width / 2;
        double teta = acos(1 - H / r) * 2;
        double S = r * r * (teta - sin(teta)) / 2;
        return S;
    }
//позволяет определить находится ли шарик над стаканом, который передается
//в метод параметром, точнее между вертикалями верхних точек стакана, то что он выше стакана
    //проверяется отдельно
@Override
    public boolean isOverGlass(Glass glass) {
        if (x > glass.x1 && x + width < glass.x2) {// && y < glass.y1) {
            return true;
        } else {
            return false;
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
// переопределённый метод отрисовки шарика

    @Override
    protected void paintSelf(Graphics graphics) {
        Graphics2D gr2D = (Graphics2D) graphics;
        GradientPaint gp = new GradientPaint((int) x, (int) y + width / 2, Color.decode("#8B475D"),
                (int) x + width, (int) y + height / 2, Color.decode("#CD6889"), true);
        gr2D.setPaint(gp);
        //graphics.setColor(Color.MAGENTA);
        graphics.fillOval((int) x, (int) y, width, height);
        graphics.setColor(Color.BLACK);
        graphics.drawOval((int) x, (int) y, width, height);
    }
}
