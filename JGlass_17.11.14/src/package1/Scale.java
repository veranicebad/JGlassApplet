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
import javax.vecmath.Point3d;

/**
 *
 * @author Vera
 */
public class Scale extends MaterialObject {

    private URL filename = null;
    public BranchGroup branchGroup = null;

    public Scale(float mass, double x, double y, double z, int width, int height, Point3d gamma,
            boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        filename = JGlassApplet.class.getResource("linejka.obj");
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
        shape = (Shape3D) s.getSceneGroup().getChild(0);
        branchGroup = s.getSceneGroup();
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        matObjMoveTo(this.x, this.y, this.z);
        JGlassApplet.scene.hashmap.put(shape, this);
    }

    @Override
    protected void matObjMoveTo(double newX, double newY, double newZ) {
        newCenter.x = newX;
        newCenter.y = newY;
        newCenter.z = newZ;
        x=newX;
        y=newY;
        z=newZ;
        setTransform3D(getScale(relationWidthHeight));
    }

    @Override
    public boolean isOverGlass(Glass glass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
