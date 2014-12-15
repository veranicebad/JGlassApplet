/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.scenegraph.io.retained.Controller;
import com.sun.j3d.utils.scenegraph.io.retained.SymbolTableData;
import com.sun.j3d.utils.scenegraph.io.state.javax.media.j3d.BehaviorState;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

/**
 *
 * @author Alex
 */
public class CreaterBehaviourInteractors extends MouseAdapter {

    KeyNavigatorBehavior keyInteractor = null;
    MouseTranslate behavior2 = null;
    PickTranslateBehavior pickTranslate = null;
    MaterialObject matObj = null;

    public CreaterBehaviourInteractors(MaterialObject matObj) {
        JGlassApplet.scene.canvas.addMouseListener(this);
        //if (matObj != null) {
            this.matObj = matObj;
            TransformGroup viewTransformGroup = matObj.transformGroup;
            keyInteractor = new KeyNavigatorBehavior(viewTransformGroup);
            BoundingSphere movingBounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
            keyInteractor.setSchedulingBounds(movingBounds);
            behavior2 = new MouseTranslate();
            behavior2.setFactor(0.01, 0.01);
            behavior2.setTransformGroup(viewTransformGroup);
            behavior2.setSchedulingBounds(JGlassApplet.scene.bounds);
        //}
        //JGlassApplet.scene.canvas.addMouseListener(this.behavior2);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
}
