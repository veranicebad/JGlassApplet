/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import javax.vecmath.Point3d;

/**
 *
 * @author Vera
 */
public class JGlassApplet extends Applet {

    public static MyScene scene = null;
    public static Table table = null;
    public static Ball ball = null;
    public static Crown crown = null;
    public static Scale scale = null;

    public static Baulk baulk = null;
    public static Cube cube = null;
    private SimpleUniverse simpleU;
    public static Pick pick = null;
    public static MouseMotion mouseMotion = null;
    public static MotionToAxisZ motionZ = null;
    public static CreaterBehaviourInteractors createrBehaviourInteractors = null;
    public static Glass glass = null;
    //private int width = 500, height = 500;

    @Override
    public void init() {
        super.init();
        setSize(600, 600);
        scene = new MyScene(getWidth(), getHeight());
        setLayout(new BorderLayout());
        add("Center", scene.canvas);
        pick = new Pick();
        mouseMotion = new MouseMotion();
        motionZ = new MotionToAxisZ();
        //размеры задавать пропорциональные размерам в Blender
        table = new Table(0, 250, 700, 0, 400, 400, new Point3d(0, 0, 0), false);
        ball = new Ball(0, 135, 480, 0, 40, 40, new Point3d(0, 0, 0), true);
        cube = new Cube(0, 100, 490, 0, 20, 20, new Point3d(0, 0, 0), true);
        baulk = new Baulk(0, 100, 380, 0, 20, 90, new Point3d(Math.PI / 2, 0, 0), false);
        glass = new Glass(0, 270, 420, 0, 80, 160, 55, new Point3d(0, 0, 0), true);
        scale = new Scale(0, 400, 500 - (int) (100), 0, 20, (int) (200), new Point3d(0, 0, 0), true);
        scene.addObject(scale);
        crown = new Crown(0, 200, 500 - (int) (40 * 0.734 / 4.0), 0, 40, (int) (20 * 0.734), new Point3d(0, 0, 0), true);
        scene.addObject(crown);
        scene.addObject(ball);
        scene.addObject(cube);
        scene.addObject(baulk);
        scene.addObject(glass);
        scene.addObject(table);
        createrBehaviourInteractors = new CreaterBehaviourInteractors(glass);
        pick.myColDet = new CollisionDetector(JGlassApplet.glass.shape, JGlassApplet.scene.bounds);
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
        scene.branchGroup.addChild(scene.background);

        scale.transformGroup.addChild(scale.branchGroup);
        scene.branchGroup.addChild(scale.transformGroup);
        crown.transformGroup.addChild(crown.branchGroup);
        scene.branchGroup.addChild(crown.transformGroup);
//        
        baulk.transformGroup.addChild(baulk.branchGroup);
        scene.branchGroup.addChild(baulk.transformGroup);
        table.transformGroup.addChild(table.branchGroup);
        scene.branchGroup.addChild(table.transformGroup);
        glass.transformGroup.addChild(glass.branchGroup);
        cube.transformGroup.addChild(cube.branchGroup);
        ball.transformGroup.addChild(ball.branchGroup);
        scene.branchGroup.addChild(glass.transformGroup);
        scene.branchGroup.addChild(ball.transformGroup);
        scene.branchGroup.addChild(cube.transformGroup);
        scene.branchGroup.addChild(JGlassApplet.createrBehaviourInteractors.keyInteractor);
        scene.branchGroup.addChild(JGlassApplet.createrBehaviourInteractors.behavior2);
        //scene.branchGroup.addChild(JGlassApplet.createrBehaviourInteractors.pickTranslate);       
        scene.simpleU.addBranchGraph(scene.branchGroup);
        scene.simpleU.addBranchGraph(pick.branchGroupCD);
        //scene.simpleU.addBranchGraph(JGlassApplet.baulk.branchGroup);
    }

    @Override
    public void stop() {
    }
}
