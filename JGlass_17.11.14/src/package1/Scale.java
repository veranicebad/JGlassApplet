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
        filename = JGlassApplet.class.getResource("lin.obj");
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
    public boolean isOverGlass(Glass glass) {
        if (this.x - this.width / 2 > glass.x - glass.width / 2 && x + width / 2 < glass.x + glass.width / 2) {// && y < glass.y1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUpperGlass(Glass glass) {
        if (glass.y - glass.height / 2 > this.y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void setMovable(MaterialObject matObjIntrsct, double newX, double newY, double newZ) {
        //пока линейку можно везде двигать
        movable = true;
//        Point nC = new Point(newX, newY);
//        Point oC = new Point(this.x, this.y);
//        if (matObjIntrsct != null) {
//            if (matObjIntrsct instanceof Glass) {
//                Glass glass = (Glass) matObjIntrsct;
//                if (this.isUpperGlass(glass)) {
//                    this.movable = true;
//                } else {
//                    if (nC.distance(oC.x, oC.y) > Math.min(glass.widthBottom, this.width / 2)) {
//                        this.movable = false;
//                    } else {
//                        Point p1 = new Point(glass.x - glass.width / 2, glass.y - glass.height / 2);
//                        Point p2 = new Point(glass.x + glass.width / 2, glass.y - glass.height / 2);
//                        Point p3 = new Point(p2.x - (glass.width - glass.widthBottom) / 2, p2.y + glass.height);
//                        Point p4 = new Point(p3.x - glass.widthBottom, p3.y);
//                        Line l2 = new Line(p2, p3);
//                        Line l3 = new Line(p3, p4);
//                        Line l4 = new Line(p4, p1);
//                        if (l2.calcDistance(newX, newY) >= this.width / 2
//                                && l3.calcDistance(newX, newY) >= this.height / 2
//                                && l4.calcDistance(newX, newY) >= this.width / 2) {
//                            this.movable = true;
//                        } else {
//                            this.movable = false;
//                        }
//                    }
//                }
//            } else {
//                if (nC.distance(oC.x, oC.y) > Math.min(matObjIntrsct.width/2, this.width / 2)) {
//                    this.movable = false;
//                } else {
//                    Point3d nC3d = new Point3d(newX, newY, newZ);
//                    if (nC3d.distance(matObjIntrsct.center) >= center.distance(matObjIntrsct.center)) {
//                        movable = true;
//                    } else {
//                        movable = false;
//                    }
//                }
//            }
//        } else {
//            movable = true;
//        }
    }
}
