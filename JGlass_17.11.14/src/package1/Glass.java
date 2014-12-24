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
public class Glass extends MaterialObject {

    private URL filename = null;
    BranchGroup branchGroup = null;
    public int widthBottom;
    //protected double gamma;

    public Glass(float mass, double x, double y, double z, int width, int height, int widthBottom,
            Point3d gamma, boolean movable) {
        super(mass, x, y, z, width, height, gamma, movable);
        this.widthBottom=(int)(width*0.74160895045285029302077783697389);
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
        //System.out.println(shape.getAllGeometries().nextElement() + " BOUNDS");
//        shape.setAppearanceOverrideEnable(true);
//        Appearance polyAppear = shape.getAppearance();
//        PolygonAttributes polyAttrib = new PolygonAttributes();
//        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
//        polyAppear.setPolygonAttributes(polyAttrib);
        matObjMoveTo(this.x, this.y, this.z);
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
        newCenter.x = newX;
        newCenter.y = newY;
        newCenter.z = newZ;
        if (JGlassApplet.pick.myColDet != null) {
            if (JGlassApplet.pick.myColDet.intrsctMatObj != null) {
                if (((this.newCenter.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center))
                        >= (this.center.distance(JGlassApplet.pick.myColDet.intrsctMatObj.center)))
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

//    protected void inclineGlass(double gamma, double cx, double cy) {
//        //tr.rotZ(gamma);
//    }
//    @Override
//    protected void paintSelf() {
//    }
    @Override
    public boolean isOverGlass(Glass glass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
