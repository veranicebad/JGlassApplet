package package1;


import java.awt.Graphics;
import static java.lang.Math.*;

/**
 *
 * @author ver
 */
public abstract class MaterialObject extends PhysObject {
//стакан, в котором находится материальное тело, если тело не погружено
    //в какой-либо стакан, это поле имеет значение null

    public Glass outside = null;
    //isDiving == true если идёт погружение в какой-либо стакан
    public boolean isDiving = false;
    //isInList == true если шарик уже есть в списке innerObjects стакана,
    //в котором он находится(outside)
    public boolean isInList = false;
    //ускорение, скорость, глубина погружения(считается от уровня

    //воды в стакане, в который погружено материальное тело, до нижней точки материального тела, если оно
    //погружено полностью hDeep=высоте тела)
    public double a = 0, v = 0;
    public double hDeep = 0;
    protected double x, y;
    protected int z;
    protected float mass=0;
    protected double defaultX, defaultY, defaultZ;
    protected int width, height;
    protected boolean movable = true;

    //volatile boolean isDragging;
    //volatile boolean isSloping;
    //volatile boolean isSlopingRight;
    //volatile boolean isSlopingLeft;
    public MaterialObject(float mass, double x, double y, int width, int height, boolean movable) {
        this.mass = mass;
        this.x = x;
        this.y = y;
        this.z = 0;
        this.width = width;
        this.height = height;
        this.movable = movable;
        this.defaultX = x;
        this.defaultY = y;
        this.defaultZ = z;
    }

    /*
     * public boolean isMovable() { return movable;
    }
     */
    public boolean isSelected(int x, int y) {
        if (x > this.x && x < this.x + width) {
            if (y > this.y && y < this.y + height) {
                return true;
            }
        }
        return false;
    }

    public void setX(int x) {
        if (movable) {
            for (PhysObject o : children) {
                if (o instanceof MaterialObject) {
                    MaterialObject mo = (MaterialObject) o;
                    mo.setX(x - (int) this.x + (int) mo.x);
                }
            }
            this.x = x;
        }
    }

    public void setY(int y) {
        if (movable) {
            for (PhysObject o : children) {
                if (o instanceof MaterialObject) {
                    MaterialObject mo = (MaterialObject) o;
                    mo.setY(y - (int) this.y + (int) mo.y);
                }
            }
            this.y = y;
        }
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setMovable(boolean Movable) {
        this.movable = Movable;
    }

    public int getDefaultZ() {
        return (int) defaultZ;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getZ() {
        return z;
    }

    public float getMass() {
        return mass;
    }

    /**
     * Возвращает объект на позицию по умолчанию.
     */
    public final void moveToDefaultPlace() {
        setX((int) defaultX);
        setY((int) defaultY);
    }

    //считает сумму двух сил, в декартовых координатах(y направленна наверх)
    public Force sumForce(Force force1, Force force2) {
        Force sumForce = new Force(0, 0);
        sumForce.x = force1.getX() + force2.getX();
        sumForce.y = force1.getY() + force2.getY();
        sumForce.value = sqrt(pow(sumForce.x, 2) + pow(sumForce.y, 2));
        if (sumForce.value != 0) {
            sumForce.angle = acos(sumForce.x / sumForce.value);
            if (sumForce.y < 0) {
                sumForce.angle = 2 * PI - sumForce.angle;
            }
        } else {
            sumForce.angle = 0;
        }
        return sumForce;
    }
//возвращает результирующую силу, действующую на тело, Y направленна наверх

    public Force getResultForce() {
        Force resultForce = new Force(0, 0);
        Force archimedesForce = new Force(0, PI / 2);
        Force gravitationalForce = new Force(0, 3 * PI / 2);
        Force fictilityForce = new Force(0, signum(-v) * PI / 2);
        double nu = 1.05;
        if (isDiving) {
            double g = 100;
            archimedesForce.value = outside.water.ro * g * getVolume(hDeep);
            gravitationalForce.value = mass * g;
            fictilityForce.value = abs(v * nu);
        }
        resultForce = sumForce(archimedesForce, gravitationalForce);
        resultForce = sumForce(resultForce, fictilityForce);
        return resultForce;
    }
//возвращает ускорение, компьютерные коорд, y направлена вниз.

    public double getAccelerationY() {
        a = -getResultForce().value * sin(getResultForce().angle) / mass;
        return a;
    }

    protected abstract void paintSelf(Graphics graphics);

    public abstract double getVolume(double hDeep);

    public abstract double getVolumeS(double hDeep);

    public abstract boolean isOverGlass(Glass glass);
}
