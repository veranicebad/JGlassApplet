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
public class Sink extends MaterialObject {

    private URL filename = null;
    public BranchGroup branchGroup = null;

    public Sink(float mass, double x, double y, double z, int width, int height, Point3d gamma,
            boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        filename = JGlassApplet.class.getResource("sink.obj");
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
        boolean movableT = this.movable;
        this.movable = true;
        matObjMoveTo(this.x, this.y, this.z);
        this.movable = movableT;
        JGlassApplet.scene.hashmap.put(shape, this);
    }

    @Override
    protected void matObjMoveTo(double newX, double newY, double newZ) {
        if (this.movable) {
            x = newX;
            y = newY;
            z = newZ;
            this.center.x = x;
            this.center.y = y;
            this.center.z = z;
        }
        setTransform3D(getScale(relationWidthHeight));
    }

    @Override
    protected void setMovable(MaterialObject matObjIntrsct, double newX, double newY, double newZ) {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isOverGlass(Glass glass) {
        return false;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isUpperGlass(Glass glass) {
        return false;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
