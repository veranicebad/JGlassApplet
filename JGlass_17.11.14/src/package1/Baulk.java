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
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;

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
    private boolean BGisAttach = true;
    public BranchGroup branchGroup = null;

    public Baulk(float mass, double x, double y, double z, int width, int height, Point3d gamma, boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        filename = JGlassApplet.class.getResource("brus.obj");
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
        boolean movableT=this.movable;
        this.movable=true;
        matObjMoveTo(this.x, this.y, this.z);
        this.movable=movableT;
        JGlassApplet.scene.hashmap.put(shape, this);
        this.isVisible = false;
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
    }

    public void setBGBaulkIsAttach() {
        if (JGlassApplet.pick.movedGlass != null) {
            setVisibleBaulkLeft(JGlassApplet.pick.movedGlass);
            if (isVisible) {
                if (BGisAttach == false) {
                    transformGroup.addChild(branchGroup);
                    BGisAttach = true;
                }
            } else {
                BGisAttach = false;
                branchGroup.detach();
            }
        }
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
        if (this.movable) {
            x = newX;
            y = newY;
            z = newZ;
            Transform3D trTmp = new Transform3D();
        //Vector3f r = new Vector3f(-100f, 10f, 0f);
            //trTmp.setRotation(new AxisAngle4f(r, 1f));
            //tr.mul(trTmp);
            setTransform3D(getScale(relationWidthHeight));
        }
    }

    @Override
    public boolean isOverGlass(Glass glass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
