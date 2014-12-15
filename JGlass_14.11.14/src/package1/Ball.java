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
import static java.lang.Math.abs;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
//import javax.media.j3d.*;

/**
 *
 * @author ver
 */
public class Ball extends MaterialObject {

    private URL filename = null;
    public BranchGroup branchGroup = null;
    //Shape3D shape = null;

    public Ball(float mass, double x, double y, double z, int width, int height,
            boolean movable) {
        super(mass, x, y, z, width, height, movable);
        filename = JGlassApplet.class.getResource("ball_19.07.14.obj");
        ObjectFile f = new ObjectFile();
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
        JGlassApplet.scene.hashmap.put(shape, this);
    }

//    @Override
//    public boolean isSelected(int x, int y) {
//        if (sqrt(pow(this.x - x, 2)
//                + pow(this.y - y, 2)) <= this.width / 2) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    protected void matObjMoveTo(double newX, double newY, double newZ) {
        newCenter.x = newX;
        newCenter.y = newY;
        newCenter.z = newZ;
        if (JGlassApplet.pick.myColDet.intrsctMatObj != null) {
            System.out.print("DIST = "
                    +(newCenter.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)) + " w = " + (JGlassApplet.pick.myColDet.intrsctMatObj.width / 2 + width / 2)+" ");
            System.out.println("intrsctMatObj"+JGlassApplet.pick.myColDet.intrsctMatObj);
            System.out.println("coord1" + x + " " + y + " " + z+"coord2"+JGlassApplet.pick.myColDet.intrsctMatObj.x+" " + JGlassApplet.pick.myColDet.intrsctMatObj.y + " " + JGlassApplet.pick.myColDet.intrsctMatObj.z);
            if ((this.newCenter.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)) >= (JGlassApplet.pick.myColDet.intrsctMatObj.width / 2 + width / 2)) {
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
        Transform3D tr = new Transform3D();
        Vector3d v = new Vector3d(JGlassApplet.scene.scaleX(x),
                JGlassApplet.scene.scaleY(y), JGlassApplet.scene.scaleZ(z));

        //System.out.println("coord1" + x + " " + y + " " + z+"coord2"+JGlassApplet.myColDet.intrsctMatObj.x+" " + JGlassApplet.myColDet.intrsctMatObj.y + " " + JGlassApplet.myColDet.intrsctMatObj.z);
//JGlassApplet.scene.scaleZ(z));
        //, 0);
        //
        //System.out.println(x+" "+y );
        tr.setTranslation(v);
        transformGroup.setTransform(tr);
    }

    protected void paintSelf() {
    }

    @Override
    protected void matObjSetSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
