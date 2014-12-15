/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.awt.Point;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.Behavior;
import javax.media.j3d.Bounds;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnCollisionMovement;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;

class CollisionDetector extends Behavior {

    /**
     * The separate criteria used to wake up this beahvior.
     */
    protected WakeupCriterion[] theCriteria = null;
    /**
     * The OR of the separate criteria.
     */
    protected WakeupOr oredCriteria = null;
    //public static 
    /**
     * The shape that is watched for collision.
     */
    public static Shape3D collidingShape = null;
    public static MaterialObject collMatObj = null;
    public static MaterialObject intrsctMatObj = null;
    // public static double[] doubles;
    //Point3d p;

    /**
     * @param theShape Shape3D that is to be watched for collisions.
     * @param theBounds Bounds that define the active region for this behaviour
     */
    public CollisionDetector(Shape3D theShape, Bounds theBounds) {
        collidingShape = theShape;
        collMatObj = null;
        //setSchedulingBounds(null);
        setSchedulingBounds(theBounds);
        intrsctMatObj = null;
        theCriteria = null;
        oredCriteria = null;
    }

    /**
     * This creates an entry, exit and movement collision criteria. These are
     * then OR'ed together, and the wake up condition set to the result.
     */
    public void initialize() {
        //System.out.println("INITIALIZE");
        if (collidingShape != null) {
            theCriteria = new WakeupCriterion[3];
            theCriteria[0] = new WakeupOnCollisionEntry(collidingShape,
                    WakeupOnCollisionEntry.USE_GEOMETRY);
            theCriteria[1] = new WakeupOnCollisionExit(collidingShape,
                    WakeupOnCollisionExit.USE_GEOMETRY);
            theCriteria[2] = new WakeupOnCollisionMovement(collidingShape,
                    WakeupOnCollisionMovement.USE_GEOMETRY);
            oredCriteria = new WakeupOr(theCriteria);
            wakeupOn(oredCriteria);
        }
    }

    /**
     * Where the work is done in this class. A message is printed out using the
     * userData of the object collided with. The wake up condition is then set
     * to the OR'ed criterion again.
     */
    public void processStimulus(Enumeration criteria) {
        //System.out.println("PROCESSSTIMULUS");
        if (JGlassApplet.pick.selectedObject != null) {
            collidingShape = JGlassApplet.pick.selectedObject.shape;
            //intrsctMatObj = null;
            WakeupCriterion theCriterion = (WakeupCriterion) criteria.nextElement();
            collMatObj = (MaterialObject) (JGlassApplet.scene.hashmap.get(collidingShape));
            if (theCriterion instanceof WakeupOnCollisionEntry) {
                Node theLeaf = ((WakeupOnCollisionEntry) theCriterion).getTriggeringPath().getObject();
                intrsctMatObj = ((MaterialObject) (JGlassApplet.scene.hashmap.get((theLeaf))));
                if (collMatObj.equals(intrsctMatObj)) {
                    return;
                }
                //collMatObj.movable=false;
                System.out.println(collMatObj + " Collided with " + intrsctMatObj);
            } else if (theCriterion instanceof WakeupOnCollisionExit) {
                Node theLeaf = ((WakeupOnCollisionExit) theCriterion).getTriggeringPath().getObject();
                //System.out.println("Stopped colliding with  "
                //        + theLeaf.getUserData());
                intrsctMatObj = ((MaterialObject) (JGlassApplet.scene.hashmap.get((theLeaf))));
                System.out.println(collMatObj + " Stopped colliding with  " + intrsctMatObj);
                // collMatObj.movable=true;
                intrsctMatObj = null;
            } else {
                Node theLeaf = ((WakeupOnCollisionMovement) theCriterion).getTriggeringPath().getObject();
//            System.out.println("Moved whilst colliding with "
//                    + theLeaf.getUserData());
                intrsctMatObj = ((MaterialObject) (JGlassApplet.scene.hashmap.get((theLeaf))));
                if (intrsctMatObj instanceof Cube) {
                    System.out.println("@@@@@@@@@@@@@@");
                }
                System.out.println(collMatObj + " Moved whilst colliding with " + intrsctMatObj);
            }
            wakeupOn(oredCriteria);
        }
    }
}
