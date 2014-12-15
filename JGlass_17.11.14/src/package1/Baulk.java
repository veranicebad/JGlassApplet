/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Alex
 */
public class Baulk extends MaterialObject {

    //координата x нижней точки, координата y нижней точки,
    //координаты x и y верхней точки, угол наклона балки, отсчитывается против часовой
    //стрелки от оси x
    protected double xb, yb, xb2, yb2, angle;
    //видна ли балка в данный момент
    volatile boolean isVisible;
    private URL filename = null;
    public BranchGroup branchGroup = null;

    public Baulk(float mass, double x, double y, double z, int width, int height, Point3d gamma, boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        filename = JGlassApplet.class.getResource("brus1.obj");
        ObjectFile f = new ObjectFile();
        f.setFlags(ObjectFile.RESIZE);
        Scene s = null;
        try {
            s = f.load(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JGlassApplet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IncorrectFormatException ex) {
            Logger.getLogger(JGlassApplet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParsingErrorException ex) {
            Logger.getLogger(JGlassApplet.class.getName()).log(Level.SEVERE, null, ex);
        }
        branchGroup = s.getSceneGroup();
        shape = (Shape3D) branchGroup.getChild(0);
        matObjMoveTo(this.x, this.y, this.z);
        JGlassApplet.scene.hashmap.put(shape, this);
        this.isVisible = false;
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
    }

    public void setVisibleBaulkLeft(Glass movedGlass) {
        //matObjMoveTo(x, y, z);
        if ((JGlassApplet.pick.mouseClicked) && (movedGlass != null)
                && (movedGlass.center.distance(this.center) < 200)) {
            isVisible = (!JGlassApplet.mouseMotion.goRight) || (movedGlass.gamma.z != 0);
        } else {
            isVisible = false;
        }
    }

    @Override
    protected void matObjMoveTo(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
        Transform3D trTmp = new Transform3D();
        //Vector3f r = new Vector3f(-100f, 10f, 0f);
        //trTmp.setRotation(new AxisAngle4f(r, 1f));
        //tr.mul(trTmp);
        setTransform3D(getScale(relationWidthHeight));
    }

    @Override
    public boolean isOverGlass(Glass glass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
