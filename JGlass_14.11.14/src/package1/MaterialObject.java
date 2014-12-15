/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Primitive;
import java.awt.Graphics;
import java.security.acl.Group;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphObject;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

/**
 *
 * @author ver
 */
public abstract class MaterialObject {

    public TransformGroup transformGroup;
    protected double x, y, z;
    protected float mass = 0;
    protected double defaultX, defaultY, defaultZ;
    protected int width, height;
    protected boolean movable = true;
    protected MouseRotate behavior = null;
    Shape3D shape = null;
    public Point3d center = new Point3d(0, 0, 0);
    public Point3d newCenter = new Point3d(0, 0, 0);

    public MaterialObject(float mass, double x, double y, double z, int width, int height,
            boolean movable) {
        this.mass = mass;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.movable = movable;
        this.center.x = x;
        this.center.y = y;
        this.center.z = z;
        this.defaultX = x;
        this.defaultY = y;
        this.defaultZ = z;
        transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    }

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
//            for (PhysObject o : children) {
//                if (o instanceof MaterialObject) {
//                    MaterialObject mo = (MaterialObject) o;
//                    mo.setX(x - (int) this.x + (int) mo.x);
//                }
//            }
            this.x = x;
        }
    }

    public void setY(int y) {
        if (movable) {
//            for (PhysObject o : children) {
//                if (o instanceof MaterialObject) {
//                    MaterialObject mo = (MaterialObject) o;
//                    mo.setY(y - (int) this.y + (int) mo.y);
//                }
//            }
            this.y = y;
        }
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setMovable(boolean Movable) {
        this.movable = Movable;
    }

    public final void moveToDefaultPlace() {
        matObjMoveTo(defaultX, defaultY, defaultZ);

    }

    protected abstract void matObjMoveTo(double newX, double newY, double newZ);

    protected abstract void matObjSetSize();

    protected abstract void paintSelf();
}
