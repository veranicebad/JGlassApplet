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
//import javax.media.j3d.*;

/**
 *
 * @author ver
 */
public class Ball extends MaterialObject {

    private URL filename = null;
    public BranchGroup branchGroup = null;
    //Shape3D shape = null;

    public Ball(float mass, double x, double y, double z, int width, int height, Point3d gamma,
            boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        filename = JGlassApplet.class.getResource("ball.obj");
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
        shape = (Shape3D) s.getSceneGroup().getChild(0);
        branchGroup = s.getSceneGroup();
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        matObjMoveTo(this.x, this.y, this.z);
        //setTransform3D(x, y, z, getScale());
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
    protected void setMovable(Glass glass, double newX, double newY) {
        if (this.isUpperGlass(glass)) {
            this.movable = true;
        } else {
            Point nC = new Point(newX, newY);
            Point oC = new Point(this.x, this.y);
            if (nC.distance(oC.x, oC.y) > Math.min(glass.widthBottom, this.width / 2)) {
                this.movable = false;
            } else {
                Point p1 = new Point(glass.x - glass.width / 2, glass.y - glass.height / 2);
                Point p2 = new Point(glass.x + glass.width / 2, glass.y - glass.height / 2);
                Point p3 = new Point(p2.x - (glass.width - glass.widthBottom) / 2, p2.y + glass.height);
                Point p4 = new Point(p3.x - glass.widthBottom, p3.y);
                Line l2 = new Line(p2, p3);
                Line l3 = new Line(p3, p4);
                Line l4 = new Line(p4, p1);
                if (l2.calcDistance(newX, newY) >= this.width / 2
                        && l3.calcDistance(newX, newY) >= this.height / 2
                        && l4.calcDistance(newX, newY) >= this.width / 2) {
                    this.movable = true;
                } else {
                    this.movable = false;
                }
            }
        }
    }

    protected void matObjMoveTo(double newX, double newY, double newZ) {
        if (this.movable) {
            x = newX;
            y = newY;
            z = newZ;
            this.center.x = x;
            this.center.y = y;
            this.center.z = z;
        }
//        System.out.println("coord" + x + " " + y + " " + z);

        setTransform3D(getScale(relationWidthHeight));
    }

//    protected void paintSelf() {
//    }
//    @Override
//    protected void matObjSetSize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public boolean isUpperGlass(Glass glass) {
        if (glass.y - glass.height / 2 > this.y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOverGlass(Glass glass) {
        if (this.x - this.width / 2 > glass.x - glass.width / 2 && x + width / 2 < glass.x + glass.width / 2) {// && y < glass.y1) {
            return true;
        } else {
            return false;
        }
    }
}
