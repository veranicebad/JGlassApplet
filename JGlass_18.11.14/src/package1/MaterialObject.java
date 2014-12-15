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
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Geometry;
import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphObject;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author ver
 */
public abstract class MaterialObject {

    public TransformGroup transformGroup;
    protected Transform3D tr = new Transform3D();
    protected double x, y, z, gamma;
    protected float mass = 0;
    protected double defaultX, defaultY, defaultZ;
    protected int width, height;
    protected boolean movable = true;
    //protected MouseRotate behavior = null;
    Shape3D shape = null;
    public Point3d center = new Point3d(0, 0, 0);
    public Point3d newCenter = new Point3d(0, 0, 0);

    public MaterialObject(float mass, double x, double y, double z, int width, int height,
            boolean movable) {
        this.mass = mass;
        this.x = x;
        this.y = y;
        this.z = z;
        this.gamma=0;
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

    public Vector3d[] getLimits() {
        Point3d currentVertex = new Point3d();
        Vector3d[] limit = new Vector3d[2];
        limit[0] = new Vector3d(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        limit[1] = new Vector3d(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

        Enumeration num = shape.getAllGeometries();
        while (num.hasMoreElements()) {
            TriangleArray ta = (TriangleArray) num.nextElement();
            int i = 0;
            ta.getCoordinate(i, currentVertex);
            while (currentVertex != null) {
                if (currentVertex.getX() < limit[0].getX()) {
                    limit[0].setX(currentVertex.getX());
                }
                if (currentVertex.getX() > limit[1].getX()) {
                    limit[1].setX(currentVertex.getX());
                }
                if (currentVertex.getY() < limit[0].getY()) {
                    limit[0].setY(currentVertex.getY());
                }
                if (currentVertex.getY() > limit[1].getY()) {
                    limit[1].setY(currentVertex.getY());
                }
                if (currentVertex.getZ() < limit[0].getZ()) {
                    limit[0].setZ(currentVertex.getZ());
                }
                if (currentVertex.getZ() > limit[1].getZ()) {
                    limit[1].setZ(currentVertex.getZ());
                }
                i++;
                ta.getCoordinate(i, currentVertex);
            }
        }
        // Find the limits of the model
    /*Vector3f[] limit = new Vector3f[2];
         limit[0] = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
         limit[1] = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
         for (int i = 0; i < positions.size(); i++) {

         currentVertex = positions.get(i);

         // Keep track of limits for normalization
         if (currentVertex.getX() < limit[0].getX())
         limit[0].setX(currentVertex.getX());
         if (currentVertex.getX() > limit[1].getX())
         limit[1].setX(currentVertex.getX());
         if (currentVertex.getY() < limit[0].getY())
         limit[0].setY(currentVertex.getY());
         if (currentVertex.getY() > limit[1].getY())
         limit[1].setY(currentVertex.getY());
         if (currentVertex.getZ() < limit[0].getZ())
         limit[0].setZ(currentVertex.getZ());
         if (currentVertex.getZ() > limit[1].getZ())
         limit[1].setZ(currentVertex.getZ());
         }*/
        return limit;
    } // End of getLimits

    protected void normalize() {
        Vector3d[] limits = getLimits();
        Enumeration num = shape.getAllGeometries();
        Point3d currentVertex = new Point3d();
        while (num.hasMoreElements()) {

            TriangleArray ta = (TriangleArray) num.nextElement();
            //ta.setCapability(64);
            float[] floats = ta.getCoordRefFloat();
            for (int i = 0; i < floats.length; i += 3) {
                ta.getCoordinate(i, currentVertex);
                if (currentVertex.getX() >= 0) {
                    currentVertex.setX(currentVertex.getX() / limits[1].getX());
                } else {
                    currentVertex.setX(Math.abs(currentVertex.getX()) / limits[0].getX());
                }

                if (currentVertex.getY() >= 0) {
                    currentVertex.setY(currentVertex.getY() / limits[1].getY());
                } else {
                    currentVertex.setY(Math.abs(currentVertex.getY()) / limits[0].getY());
                }

                if (currentVertex.getZ() >= 0) {
                    currentVertex.setZ(currentVertex.getZ() / limits[1].getZ());
                } else {
                    currentVertex.setZ(Math.abs(currentVertex.getZ()) / limits[0].getZ());
                }
                ta.setCoordinate(i, currentVertex);
            }
        }
    }

    protected Vector3d getScale() {
        Vector3d scale3d = new Vector3d(1, 1, 1);
        scale3d.x = 1.0 * width / JGlassApplet.scene.getWidth();
        scale3d.y = 1.0 * height / JGlassApplet.scene.getHeight();
        scale3d.z = 1.0 * width / JGlassApplet.scene.getWidth();
        return scale3d;
    }

    protected void setTransform3dScale(Vector3d scale) {
        Transform3D trTmp = new Transform3D();
        double[] matrix;
        matrix = new double[]{scale.x, 0, 0, 0, 0, scale.y, 0, 0, 0, 0, scale.z, 0, 0, 0, 0, 1};
        //trTmp.set(matrix);
        trTmp.setScale(0.1);
        tr.mul(trTmp);
    }

    protected void setTransform3dTranslation() {
        Vector3d v = new Vector3d(JGlassApplet.scene.scaleX(x)/getScale().x,
                JGlassApplet.scene.scaleY(y)/getScale().y, JGlassApplet.scene.scaleZ(z)/getScale().z);
        double[] matrix;
        Transform3D trTmp = new Transform3D();
        matrix = new double[]{1, 0, 0, v.x, 0, 1, 0, v.y, 0, 0, 1, v.z, 0, 0, 0, 1};
        trTmp.setTranslation(v);
        //trTmp.set(matrix);
        tr.mul(trTmp);
        System.out.println("TRANSLATION"+v.x);
        //transformGroup.setTransform(tr);
    }

    protected void setTransform3dRotate(double gamma) {
        Transform3D trTmp = new Transform3D();
        trTmp.rotZ(gamma);
        tr.mul(trTmp);
    }

    protected void setTransform3d() {
        //setTransform3dScale(getScale());
        setTransform3dTranslation();
        //setTransform3dRotate(gamma);
        transformGroup.setTransform(tr);
    }

    protected abstract void paintSelf();
}
