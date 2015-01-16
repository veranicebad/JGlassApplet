/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.utils.picking.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import java.awt.event.*;

public class Pick extends MouseAdapter {

    private final PickCanvas pickCanvas;
    private static Object pickObject = null;
    Glass movedGlass = null;
    public static MaterialObject selectedObject = null;
    MaterialObject pressedObject = null;
    Shape3D s = null;
    public static double grabShiftX;
    public static double grabShiftY;
    public static CollisionDetector myColDet = null;
    public static BranchGroup branchGroupCD = null;
    public static int xMouse, yMouse;
    public static boolean mouseClicked = false;

    public Pick() {
        pickCanvas = new PickCanvas(JGlassApplet.scene.canvas, JGlassApplet.scene.branchGroup);
        pickCanvas.setMode(PickCanvas.GEOMETRY);
        JGlassApplet.scene.canvas.addMouseListener(this);
        branchGroupCD = new BranchGroup();
        branchGroupCD.addChild(myColDet);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println(e.getY()+"!!!");
        mouseClicked = true;
        pickCanvas.setShapeLocation(e);
        PickResult result = pickCanvas.pickClosest();
        if (result == null) {
            // System.out.println("Nothing picked");
        } else {
            //System.out.println(result.getObject() + "res");
            pickObject = result.getObject();
            //System.out.println(pickObject + "=pickobject");
            selectedObject = (MaterialObject) (JGlassApplet.scene.hashmap.get(pickObject));
            pressedObject = (MaterialObject) (JGlassApplet.scene.hashmap.get(pickObject));
            //selectedObject.behavior.setEnable(true);
            //JGlassApplet.scene.createBehaviourInteractors(selectedObject);
            //System.out.println(selectedObject + "=selObj");
            xMouse = e.getX();

            yMouse = e.getY();
            grabShiftX = (e.getX() - selectedObject.x);
            grabShiftY = (e.getY() - selectedObject.y);
            Primitive p = (Primitive) result.getNode(PickResult.PRIMITIVE);
            s = (Shape3D) result.getNode(PickResult.SHAPE3D);
            branchGroupCD = new BranchGroup();
            myColDet = new CollisionDetector(selectedObject.shape, JGlassApplet.scene.bounds);
            branchGroupCD.addChild(JGlassApplet.pick.myColDet);
            JGlassApplet.scene.simpleU.addBranchGraph(branchGroupCD);
            if (p != null) {
                //System.out.println(p.getClass().getName() + "1");
            } else if (s != null) {
                //System.out.println(s.getClass().getName() + "2");
            } else {
                //System.out.println("null");
            }
        }
        if (selectedObject instanceof Glass) {
            movedGlass = (Glass) selectedObject;
        }
        if (selectedObject instanceof Scale) {
            if ((e.getY() - (JGlassApplet.pick.selectedObject.y - JGlassApplet.pick.selectedObject.height / 2) < 10)
                    && (e.getY() - (JGlassApplet.pick.selectedObject.y - JGlassApplet.pick.selectedObject.height / 2) > 0)) {
                    selectedObject.isRotate=true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
        if (selectedObject != null) {
            if(!(selectedObject instanceof Scale)){
            selectedObject.gamma.z = 0;
            if (selectedObject.y + selectedObject.height / 2 < JGlassApplet.table.y - JGlassApplet.table.height / 2) {
                selectedObject.moveToDefaultPlace();
            }}
            if (selectedObject instanceof Glass) {
                movedGlass = null;
            }
            selectedObject.isRotate=false;
            selectedObject = null;
        }
        JGlassApplet.pick.branchGroupCD = null;
        if (myColDet != null) {
            myColDet.collidingShape = null;
            myColDet.intrsctMatObj = null;
            myColDet = null;
        }
    }
}
// end of class Pick 

