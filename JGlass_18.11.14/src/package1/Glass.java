/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.javafx.geom.BaseBounds;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Sphere;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Node;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author ver
 */
public class Glass extends MaterialObject {

    private URL filename = null;
    BranchGroup branchGroup = null;
    //protected double gamma;

    public Glass(float mass, double x, double y, double z, int width, int height,
            boolean movable) {
        super(mass, x, y, z, width, height, movable);
        filename = JGlassApplet.class.getResource("glass.obj");
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
        shape = new Shape3D();

        shape = (Shape3D) branchGroup.getChild(0);
        System.out.println(shape.getAllGeometries().nextElement() + " BOUNDS");
//        shape.setAppearanceOverrideEnable(true);
//        Appearance polyAppear = shape.getAppearance();
//        PolygonAttributes polyAttrib = new PolygonAttributes();
//        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
//        polyAppear.setPolygonAttributes(polyAttrib);
        matObjMoveTo(this.x, this.y, this.z);
        setTransform3dScale(getScale());
        setTransform3d();
//        matObjSetSize();
        JGlassApplet.scene.hashmap.put(shape, this);
    }
    
//    @Override
//    protected void matObjSetSize() {
//        Vector3d v = new Vector3d(JGlassApplet.scene.getWidth()/JGlassApplet.WIDTH,
//                JGlassApplet.scene.getHeight()/JGlassApplet.HEIGHT, 1);
//        Transform3D scale=new Transform3D();
//        scale.setScale(v);
//        trTmp.add(scale);
//        transformGroup.setTransform(trTmp);
//        System.out.println("size" + JGlassApplet.scene.getWidth()+' ');
//    }
//
    @Override
    protected void matObjMoveTo(double newX, double newY, double newZ) {
        x = newX;
        y = newY;
        z = newZ;
        //setTransform3D(getScale());
        System.out.println("coord" + x + " " + y + " " + z);
    }

    protected void inclineGlass(double gamma, double cx, double cy) {
        Transform3D trTmp=new Transform3D();
        trTmp.rotZ(gamma);
        tr.mul(trTmp);
        //transformGroup.setTransform(tr);
        //System.out.println(tr.get"");
    }

    @Override
    protected void paintSelf() {
    }
}
