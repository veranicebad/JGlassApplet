/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.vp.WandViewBehavior;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.Locale;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author ver
 */
public class MyScene {

    public SimpleUniverse simpleU;
    public BranchGroup branchGroup;
    public Canvas3D canvas = null;
    private int width;
    private int height;
    private final LinkedList<MaterialObject> objects
            = new LinkedList<MaterialObject>();
    private MaterialObject selectedObject = null;
    public Map<Object, Object> hashmap = new HashMap<Object, Object>();
    public Background background = null;
    public BoundingSphere bounds = null;

    public MyScene(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        simpleU = new SimpleUniverse(canvas);
        branchGroup = createSceneGraph();
        branchGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        branchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
        background = new Background();
        background.setCapability(Background.ALLOW_IMAGE_WRITE);
        background.setCapability(Background.ALLOW_COLOR_WRITE);
        background.setApplicationBounds(bounds);
        background.setColor(new Color3f(Color.WHITE));
        enablePicking(branchGroup);
        //???
        simpleU.getViewingPlatform().setNominalViewingTransform();
        TransformGroup VpTG = simpleU.getViewingPlatform().getViewPlatformTransform();
        float Zcamera = 12; //put the camera 12 meters back
        Transform3D Trfcamera = new Transform3D();
        Trfcamera.setTranslation(new Vector3f(0.0f, 0.4f, 2.4f)); 
        //Transform3D tr_temp=new Transform3D();
        //tr_temp.setScale(3.5);
        //Trfcamera.mul(tr_temp);
        VpTG.setTransform( Trfcamera );
 
//        simpleU.addBranchGraph(branchGroup);
    }

    public BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        Color3f light1Color = new Color3f(Color.WHITE);
        bounds = new BoundingSphere(new Point3d(4.0, 0.0, 0.0), 100.0);
        Vector3f light1Direction = new Vector3f(0.0f, -7.0f, -12.0f);
        DirectionalLight light1
                = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);
        return objRoot;
    }
//???

    public void enablePicking(Node node) {
        node.setPickable(true);
        node.setCapability(Node.ENABLE_PICK_REPORTING);
        try {
            Group group = (Group) node;
            for (Enumeration e = group.getAllChildren(); e.hasMoreElements();) {
                enablePicking((Node) e.nextElement());
            }
        } catch (ClassCastException e) {
            // if not a group node, there are no children so ignore exception 
        }
        try {
            Shape3D shape = (Shape3D) node;
            PickTool.setCapabilities(node, PickTool.INTERSECT_FULL);
            for (Enumeration e = shape.getAllGeometries(); e.hasMoreElements();) {
                Geometry g = (Geometry) e.nextElement();
                g.setCapability(g.ALLOW_INTERSECT);
            }
        } catch (ClassCastException e) {
            // not a Shape3D node ignore exception 
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double scaleX(double x) {
        return (-1 + 2.0 * x / getWidth());
    }

    public double scaleY(double y) {
        return 1 - 2.0 * y / getWidth() - 1.0
                * (getWidth() - getHeight()) / getWidth();
    }

    //!!!!!!!!!!!!!!!
    public double scaleZ(double z) {
        return (z / (50 * getHeight()));
    }

    public void addObject(MaterialObject obj) {
        objects.add(obj);
    }
//
//    public void paint() {
//        for (MaterialObject t : objects) {
//            t.paintSelf();
//        }
//    }
}
