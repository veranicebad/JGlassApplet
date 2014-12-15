package package1;


import java.awt.Color;
import java.awt.Graphics;

/*
класс кубиков
 */
/**
 *
 * @author ver
 */
public class Cube extends MaterialObject {

    //стакан, в котором находится кубик, если кубик не погружен в какой-либо
    //стакан, это поле имеет значение null
    //public Glass outside = null;
    //isDiving == true если идёт погружение в какой-либо стакан
    //public boolean isDiving = false;
    //плотность, ускорение, скорость, глубина погружения(считается от уровня
    //воды в стакане, в который погружен кубик, до нижней точки кубика, если кубик
    //погружен полностью hDeep=высоте кубика)
    public double ro;
    //длинна стороны кубика по оси z
    public int d;
    //isInList == true если кубик уже есть в списке innerObjects стакана,
    //в котором он находится(outside)
    //public boolean isInList = false;
//    stopFalling;

    public Cube(float mass, double x, double y, int width, int height, int d, double ro, boolean movable) {
        super(mass, x, y, width, height, movable);
        this.ro = ro;
        this.d = d;
        setMass();
    }
//возвращает объем  погруженной части куба, по высоте, которая отсчитывается от
    //уровня воды в стакане, в который погружен кубик до нижней грани кубика
    @Override
    public double getVolume(double hDeep) {
        double v;
        v = d * hDeep * width;
        return v;
    }
//устанавливает массу кубика

    public void setMass() {
        mass = (float) (ro * getVolume(height));
    }
// возвращает площадь плоской фигуры, соответствующей кубику на плоскости,
    //с отсечённой частью плоскостью воды(прямоугольника)
@Override
    public double getVolumeS(double H) {
        double S = H * width;
        return S;
    }
//позволяет определить находится ли кубик над стаканом, который передается
//в метод параметром, точнее между прямыми верхних точек стакана
@Override
    public boolean isOverGlass(Glass glass) {
        if (x > glass.x1 && x + width < glass.x2) {// && y < glass.y1) {
            return true;
        } else {
            return false;
        }
    }
// переопределённый метод отрисовки кубика

    @Override
    protected void paintSelf(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillPolygon(new int[]{(int) x, (int) x + width, (int) x + width,
                    (int) x}, new int[]{(int) y, (int) y, (int) y + height,
                    (int) y + height}, 4);
        graphics.fillPolygon(new int[]{(int) x, (int) x + d, (int) x + width + d,
                    (int) x + width}, new int[]{(int) y, (int) y - d, (int) y - d,
                    (int) y}, 4);
        graphics.fillPolygon(new int[]{(int) x + width, (int) x + width + d, (int) x + width + d,
                    (int) x + width}, new int[]{(int) y, (int) y - d, (int) y + height - d,
                    (int) y + height}, 4);
        graphics.setColor(Color.BLACK);
        graphics.drawPolygon(new int[]{(int) x, (int) x + width, (int) x + width,
                    (int) x}, new int[]{(int) y, (int) y, (int) y + height,
                    (int) y + height}, 4);
        graphics.drawPolygon(new int[]{(int) x, (int) x + d, (int) x + width + d,
                    (int) x + width}, new int[]{(int) y, (int) y - d, (int) y - d,
                    (int) y}, 4);
        graphics.drawPolygon(new int[]{(int) x + width, (int) x + width + d, (int) x + width + d,
                    (int) x + width}, new int[]{(int) y, (int) y - d, (int) y + height - d,
                    (int) y + height}, 4);
    }
}
