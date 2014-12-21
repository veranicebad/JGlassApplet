/*
 * To change this template, choose Tools | Templates
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
import javax.vecmath.Point3d;

/**
 *
 * @author ver
 */
public class Cube extends MaterialObject {

    private URL filename = null;
    BranchGroup branchGroup = null;
    //Shape3D shape = null;

    public Cube(float mass, double x, double y, double z, int width, int height,
            Point3d gamma, boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        filename = JGlassApplet.class.getResource("cube.obj");
        ObjectFile f = new ObjectFile();
        f.setFlags(ObjectFile.RESIZE);
        Scene s = null;
        try {
            s = f.load(filename);
            //System.out.println(s.getSceneGroup() + "");
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
        //setTransform3D(x, y, z, getScale());
        JGlassApplet.scene.hashmap.put(shape, this);
    }

    protected void matObjMoveTo(double newX, double newY, double newZ) {
        newCenter.x = newX;
        newCenter.y = newY;
        newCenter.z = newZ;
        if (JGlassApplet.pick.myColDet != null) {
            if (JGlassApplet.pick.myColDet.intrsctMatObj != null) {
                if ((outside == JGlassApplet.pick.myColDet.intrsctMatObj)
                        && ((this.newCenter.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)) <= (this.center.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)))) {
                    x = newX;
                    y = newY;
                    z = newZ;
                    this.center.x = x;
                    this.center.y = y;
                    this.center.z = z;
                }
                if ((outside != JGlassApplet.pick.myColDet.intrsctMatObj)
                        && ((this.newCenter.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)) >= (this.center.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)))
                        && (!(this.newCenter.distance(this.center) > JGlassApplet.pick.myColDet.intrsctMatObj.width))) {
                    x = newX;
                    y = newY;
                    z = newZ;
                    this.center.x = x;
                    this.center.y = y;
                    this.center.z = z;
                }
            } else {
                x = newX;
                y = newY;
                z = newZ;
                this.center.x = x;
                this.center.y = y;
                this.center.z = z;
            }
        }
//        System.out.println("coord" + x + " " + y + " " + z);
        setTransform3D(getScale(relationWidthHeight));
    }

//    protected void paintSelf() {
//    }
    @Override
    public boolean isOverGlass(Glass glass) {
        if (this.x - this.width / 2 > glass.x - glass.width / 2 && x + width / 2 < glass.x + glass.width / 2) {// && y < glass.y1) {
            return true;
        } else {
            return false;
        }
    }
}
