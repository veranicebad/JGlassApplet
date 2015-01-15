/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author ver
 */
public abstract class MaterialObject {

    public TransformGroup transformGroup;
    protected Transform3D tr = new Transform3D();
    protected double x, y, z;
    protected float mass = 0;
    protected double defaultX, defaultY, defaultZ;
    protected int width, height;
    protected double relationWidthHeight;
    protected boolean movable = true;
    protected boolean isRotate = false;
    //protected MouseRotate behavior = null;
    Shape3D shape = null;
    public Point3d center = new Point3d(0, 0, 0);
    public Point3d newCenter = new Point3d(0, 0, 0);
    public Point3d gamma = new Point3d(0, 0, 0);
    public Glass outside = null;

    public MaterialObject(float mass, double x, double y, double z, int width, int height, Point3d gamma,
            boolean movable) {
        this.mass = mass;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gamma = gamma;
        this.width = width;
        this.height = height;
        this.relationWidthHeight = 1.0 * this.width / this.height;
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
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        transformGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
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

    protected abstract void setMovable(MaterialObject matObjIntrsct, double newX, double newY, double newZ);

    protected Vector3d getScale(double relationWidthHeight) {
        Vector3d scale3d = new Vector3d(1, 1, 1);
        if (relationWidthHeight >= 1) {
            scale3d.x = 1.0 * width / JGlassApplet.scene.getWidth();
            scale3d.y = 1.0 * height * relationWidthHeight / (JGlassApplet.scene.getWidth());
            scale3d.z = 1.0 * width / JGlassApplet.scene.getWidth();
        } else {
            scale3d.x = 1.0 * width / (JGlassApplet.scene.getWidth() * relationWidthHeight);
            scale3d.y = 1.0 * height / JGlassApplet.scene.getHeight();
            scale3d.z = 1.0 * width / (JGlassApplet.scene.getWidth() * relationWidthHeight);
        }

        return scale3d;
    }

    protected void setTransform3D(Vector3d scale) {
        Transform3D trTemp = new Transform3D();
        Vector3d v = new Vector3d(JGlassApplet.scene.scaleX(x),
                JGlassApplet.scene.scaleY(y), JGlassApplet.scene.scaleZ(z));
        //trTemp.setTranslation(v);
        double[] matrix;
        matrix = new double[]{scale.x, 0, 0, v.x, 0, scale.y, 0, v.y, 0, 0, scale.z, v.z, 0, 0, 0, 1};
        tr.set(matrix);
        //trTemp.set(matrix);
        //tr.mul(trTemp);
        trTemp.rotX(gamma.x);
        tr.mul(trTemp);
        trTemp.rotY(gamma.y);
        tr.mul(trTemp);
        trTemp.rotZ(gamma.z);
        tr.mul(trTemp);
        transformGroup.setTransform(tr);
    }

    public abstract boolean isOverGlass(Glass glass);
    public abstract boolean isUpperGlass(Glass glass);

    public void setOutsideGlassForMatObj() {
        if (outside == null) {
            if (isOverGlass(JGlassApplet.glass)) {
                if (this.y + this.height / 2 > JGlassApplet.glass.y - JGlassApplet.glass.height / 2) {
                    outside = JGlassApplet.glass;
                } else {
                    outside = null;
                }
            } else {
                outside = null;
            }
        } else {
            if (y + height / 2 < outside.y - outside.height / 2) {
                outside = null;
            }
        }
    }

//    protected abstract void paintSelf();
}
