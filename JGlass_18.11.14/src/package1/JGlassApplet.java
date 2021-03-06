/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.io.FileReader;
import java.io.IOException;
import com.sun.j3d.loaders.Scene; // Contains the object loaded from disk.
import com.sun.j3d.loaders.objectfile.ObjectFile; // Loader of .obj models
import com.sun.j3d.utils.geometry.Primitive;
import java.io.ObjectInputStream;
import java.net.URL;
import javax.annotation.Resources;
import javax.media.j3d.NodeComponent;

/**
 *
 * @author ver
 */
public class JGlassApplet extends Applet {

    public static MyScene scene = null;
    public static Table table = null;
    public static Ball ball = null;
    //public static Ball ball2 = null;
    public static Baulk baulk=null;
    public static Cube cube = null;
    SimpleUniverse simpleU;
    public static Pick pick = null;
    public static MouseMotion mouseMotion = null;
    public static MotionToAxisZ motionZ = null;
    public static CreaterBehaviourInteractors createrBehaviourInteractors = null;
    //public static CollisionDetector myColDet = null;
    //public static CollisionDetector myColDet1 = null;
    public static Glass glass = null;
    //private int width = 500, height = 500;

    @Override
    public void init() {
        super.init();
        setSize(500, 500);
        scene = new MyScene(getWidth(), getHeight());
        setLayout(new BorderLayout());
        add("Center", scene.canvas);
        pick = new Pick();
        mouseMotion = new MouseMotion();
        motionZ = new MotionToAxisZ();
        table = new Table(0,200,500,0,0,0,false);
        ball = new Ball(0, 500, 350, 0, 50, 50, true);
        cube = new Cube(0, 100, 365, 0, 20, 20, true);
        baulk = new Baulk(0, 100, 260, 0, 0, 0, false);
        glass = new Glass(0, 250, 288, 0, 200, 175, true);
        scene.addObject(ball);
//        scene.addObject(ball2);
        scene.addObject(cube);
        scene.addObject(baulk);
        scene.addObject(glass);
        scene.addObject(table);
        createrBehaviourInteractors = new CreaterBehaviourInteractors(glass);
        pick.myColDet = new CollisionDetector(JGlassApplet.glass.shape, JGlassApplet.scene.bounds);
        //pick.myColDet1 = new CollisionDetector(JGlassApplet.ball.shape, JGlassApplet.scene.bounds);

//        createrBehaviourInteractors = new CreaterBehaviourInteractors(ball);
        //createrBehaviourInteractors = new CreaterBehaviourInteractors(cube);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        scene.paint();
//        //g.drawImage(buffer, 0, 0, this);
//    }
//    public static Ball getBall() {
//                System.out.println(ball+"");
//        return ball;
//    }
    @Override
    public void destroy() {	// this function will allow Java3D to clean up upon quiting
//        simpleU.removeAllLocales();
    }
//    public static void main(String[] args) {
//        // if called as an application, a 500x500 window will be opened
//        Frame frame = new MainFrame(new JGlassApplet(), 500, 500);
//    }

    @Override
    public void start() {
        //baulk.transformGroup.addChild(baulk.c);
        scene.branchGroup.addChild(scene.background);
        baulk.transformGroup.addChild(baulk.branchGroup);
        scene.branchGroup.addChild(baulk.transformGroup);
        table.transformGroup.addChild(table.branchGroup);
        scene.branchGroup.addChild(table.transformGroup);
        glass.transformGroup.addChild(glass.branchGroup);
        cube.transformGroup.addChild(cube.branchGroup);
        ball.transformGroup.addChild(ball.branchGroup);
//        ball2.transformGroup.addChild(ball2.branchGroup);
        scene.branchGroup.addChild(glass.transformGroup);
        scene.branchGroup.addChild(ball.transformGroup);
  //      scene.branchGroup.addChild(ball2.transformGroup);
        scene.branchGroup.addChild(cube.transformGroup);
        scene.branchGroup.addChild(JGlassApplet.createrBehaviourInteractors.keyInteractor);
        scene.branchGroup.addChild(JGlassApplet.createrBehaviourInteractors.behavior2);
        //scene.branchGroup.addChild(JGlassApplet.createrBehaviourInteractors.pickTranslate);       
        //scene.branchGroup.addChild(JGlassApplet.pick.myColDet);
        //scene.branchGroup.addChild(JGlassApplet.pick.myColDet1);
        scene.simpleU.addBranchGraph(scene.branchGroup);
        scene.simpleU.addBranchGraph(pick.branchGroupCD);
    }

    @Override
    public void stop() {
    }
}
