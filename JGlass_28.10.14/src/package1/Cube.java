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
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;

/**
 *
 * @author ver
 */
public class Cube extends MaterialObject {

    private URL filename = null;
    BranchGroup branchGroup = null;
    //Shape3D shape = null;

    public Cube(float mass, double x, double y, double z, int width, int height,
            boolean movable) {
        super(mass, x, y, z, width, height, movable);
        filename = JGlassApplet.class.getResource("cube.obj");
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

    protected void matObjMoveTo(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
        this.center.x = x;
        this.center.y = y;
        this.center.z = z;
        Transform3D tr = new Transform3D();
        Vector3d v = new Vector3d(JGlassApplet.scene.scaleX(x),
                JGlassApplet.scene.scaleY(y), JGlassApplet.scene.scaleZ(z));
        //JGlassApplet.scene.scaleZ(z));
        //, 0);
        tr.setTranslation(v);
        transformGroup.setTransform(tr);

        System.out.println("coord" + x + " " + y + " " + z);
    }

    protected void paintSelf() {
    }

    @Override
    protected void matObjSetSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
