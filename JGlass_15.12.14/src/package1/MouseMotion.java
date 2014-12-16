/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import static java.lang.Math.abs;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

/**
 *
 * @author ver
 */
public class MouseMotion extends MouseMotionAdapter {

    public boolean mouseClicked = false, goRight, goUp;
    //public static BranchGroup branchGroupBaulk = null;
    private boolean BGisAttach = true;

    public MouseMotion() {
        JGlassApplet.scene.canvas.addMouseMotionListener(this);
        //branchGroupBaulk = new BranchGroup();
        //branchGroupBaulk.addChild(JGlassApplet.baulk.transformGroup);
    }

    public void setMovableForCollision(MaterialObject matObj, MouseEvent e) {
        //            Bounds[] b = {JGlassApplet.pick.selectedObject.c.getBounds()};
//            Bounds intesect = JGlassApplet.pick.myColDet.intrsctMatObj.c.getBounds().closestIntersection(b);
//            if (intesect != null) {
        //System.out.println("!#" + intesect);
        // Action to do if Intersect is true 
        //            }
        //if (JGlassApplet.pick.myColDet.intrsctMatObj != null) {
//            if (((e.getX() - JGlassApplet.pick.grabShiftX - JGlassApplet.pick.myColDet.intrsctMatObj.x
//                    <= matObj.width / 2 + JGlassApplet.pick.myColDet.intrsctMatObj.width / 2) && (!goRight))
//                    || ((JGlassApplet.pick.myColDet.intrsctMatObj.x - e.getX() + JGlassApplet.pick.grabShiftX
//                    <= matObj.width / 2 + JGlassApplet.pick.myColDet.intrsctMatObj.width / 2)&&(goRight))
//                    || ((e.getY() - JGlassApplet.pick.grabShiftY - JGlassApplet.pick.myColDet.intrsctMatObj.y
//                    <= matObj.height / 2 + JGlassApplet.pick.myColDet.intrsctMatObj.height / 2)&&(!goUp))
//                    || ((e.getY() - JGlassApplet.pick.grabShiftY - JGlassApplet.pick.myColDet.intrsctMatObj.y
//                    >= matObj.height / 2 + JGlassApplet.pick.myColDet.intrsctMatObj.height / 2)&&(goUp)))
//             if(JGlassApplet.pick.temp<JGlassApplet.pick.myColDet.flagColl) {
//                matObj.movable = false;
//            } else {
//                matObj.movable = true;
//            }
//            if(JGlassApplet.pick.temp<JGlassApplet.pick.myColDet.flagColl)
//            System.out.println(JGlassApplet.pick.temp+"!!!!!"+JGlassApplet.pick.myColDet.flagColl);
        // }
        //ecли нет столкновения, то оставляем как было
    }

    public boolean contactLeft(Baulk blk) {
        if (blk.isVisible) {
            if ((JGlassApplet.pick.myColDet.intrsctMatObj == blk) && (blk.x < JGlassApplet.pick.movedGlass.x)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean contactRight(Baulk blk) {
        if (blk.isVisible) {
            if ((JGlassApplet.pick.myColDet.intrsctMatObj == blk) && (blk.x > JGlassApplet.pick.movedGlass.x)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void inclineGlassOnBaulkLeft(Glass gl) {
        gl.gamma.z = gl.gamma.z + 0.02;
        //gl.inclineGlass(gl.gamma,0, 0);
    }

    public void inclineGlassOnBaulkRight(Glass gl) {
        gl.gamma.z = gl.gamma.z - 0.02;
        //gl.inclineGlass(gl.gamma,0, 0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (JGlassApplet.pick.movedGlass != null) {
            JGlassApplet.baulk.setVisibleBaulkLeft(JGlassApplet.pick.movedGlass);
            if (JGlassApplet.baulk.isVisible) {
                if (BGisAttach == false) {
                    JGlassApplet.baulk.transformGroup.addChild(JGlassApplet.baulk.branchGroup);
                    BGisAttach = true;
                }
            } else {
                BGisAttach = false;
                JGlassApplet.baulk.branchGroup.detach();
            }
        }
        if (JGlassApplet.pick.selectedObject instanceof Ball) {
            JGlassApplet.pick.selectedObject.setOutsideGlassForMatObj();
        }
        if (JGlassApplet.pick.xMouse > e.getX()) {
            goRight = false;
        } else {
            goRight = true;
        }
        if (JGlassApplet.pick.yMouse > e.getY()) {
            goUp = true;
        } else {
            goUp = false;
        }
        //14.08.14
        if (JGlassApplet.pick.selectedObject != null && !JGlassApplet.motionZ.shiftPressed) {
            if (JGlassApplet.pick.selectedObject.movable) {
                if (JGlassApplet.pick.selectedObject instanceof Glass) {
                    Glass gl;
                    gl = JGlassApplet.pick.movedGlass;
                    if (abs(gl.gamma.z) < 1e-6) {
                        gl.gamma.z = 0;
//                        gl.inclineGlass(gl.gamma, 0, 0);
                    }
                    if (gl.gamma.z == 0) {
                        if ((!(contactLeft(JGlassApplet.baulk))) && (!(contactRight(JGlassApplet.baulk)))) {
                            gl.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                        } else {
                            if ((JGlassApplet.pick.xMouse < e.getX()) && (contactLeft(JGlassApplet.baulk))) {
                                gl.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                            }
                            if ((JGlassApplet.pick.xMouse > e.getX()) && (contactRight(JGlassApplet.baulk))) {
                                gl.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                            }
                            if ((JGlassApplet.pick.xMouse < e.getX()) && (contactRight(JGlassApplet.baulk))) {
                                gl.matObjMoveTo((int) gl.x, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                                inclineGlassOnBaulkRight(gl);
                            }
                            if ((JGlassApplet.pick.xMouse > e.getX()) && (contactLeft(JGlassApplet.baulk))) {
                                gl.matObjMoveTo((int) gl.x, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                                inclineGlassOnBaulkLeft(gl);
                            }
                        }
                    } else {
                        gl.matObjMoveTo((int) gl.x, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                        if ((!contactLeft(JGlassApplet.baulk)) && (!contactRight(JGlassApplet.baulk))) {
                            gl.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX, e.getY() - JGlassApplet.pick.grabShiftY, gl.z);
                            gl.gamma.z = 0;
//                            gl.inclineGlass(gl.gamma, 0, 0);
                        }
                        if ((JGlassApplet.pick.xMouse > e.getX()) && (contactLeft(JGlassApplet.baulk))) {
                            inclineGlassOnBaulkLeft(gl);
                        }
                        if ((JGlassApplet.pick.xMouse < e.getX()) && (contactLeft(JGlassApplet.baulk))) {
                            inclineGlassOnBaulkRight(gl);
                        }
                        if ((JGlassApplet.pick.xMouse > e.getX()) && (contactRight(JGlassApplet.baulk))) {
                            inclineGlassOnBaulkLeft(gl);
                        }
                        if ((JGlassApplet.pick.xMouse < e.getX()) && (contactRight(JGlassApplet.baulk))) {
                            inclineGlassOnBaulkRight(gl);
                        }
                    }
                } else {
                    JGlassApplet.pick.selectedObject.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX,
                            e.getY() - JGlassApplet.pick.grabShiftY,
                            JGlassApplet.pick.selectedObject.z);
                }
            }
        } //14.08.14
//нужный кусок, до добавления наклона стакана, потом рассмотреть
//        if (JGlassApplet.pick.selectedObject != null && !JGlassApplet.motionZ.shiftPressed) {
//            setMovableForCollision(JGlassApplet.pick.selectedObject, e);
//            if (JGlassApplet.pick.selectedObject.movable) {
//                JGlassApplet.pick.selectedObject.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX,
//                        e.getY() - JGlassApplet.pick.grabShiftY,
//                        JGlassApplet.pick.selectedObject.z);
//            }
//        }
//        if (JGlassApplet.pick.selectedObject != null && JGlassApplet.motionZ.shiftPressed) {
//            setMovableForCollision(JGlassApplet.pick.selectedObject, e);
//            if (JGlassApplet.pick.selectedObject.movable) {
//                JGlassApplet.pick.selectedObject.matObjMoveTo(
//                        //JGlassApplet.pick.selectedObject.x,
//                        e.getX() - JGlassApplet.pick.grabShiftX,
//                        JGlassApplet.pick.selectedObject.y,
//                        JGlassApplet.pick.selectedObject.z - JGlassApplet.pick.selectedObject.y
//                        + e.getY() - JGlassApplet.pick.grabShiftY);
//            }
//        }
        JGlassApplet.pick.xMouse = e.getX();
        JGlassApplet.pick.yMouse = e.getY();
    }
}
