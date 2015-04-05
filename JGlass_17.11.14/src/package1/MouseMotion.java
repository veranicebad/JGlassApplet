/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import static java.lang.Math.abs;

/**
 *
 * @author ver
 */
public class MouseMotion extends MouseMotionAdapter {

    public boolean mouseClicked = false, goRight, goUp;
    //public static BranchGroup branchGroupBaulk = null;

    public MouseMotion() {
        JGlassApplet.scene.canvas.addMouseMotionListener(this);
        //branchGroupBaulk = new BranchGroup();
        //branchGroupBaulk.addChild(JGlassApplet.baulk.transformGroup);
    }

    public void setMovableForCollision(MaterialObject matObj, MouseEvent e) {
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
        gl.setTransform3D(gl.getScale(gl.relationWidthHeight));
        //gl.inclineGlass(gl.gamma,0, 0);
    }

    public void inclineGlassOnBaulkRight(Glass gl) {
        gl.gamma.z = gl.gamma.z - 0.02;
        gl.setTransform3D(gl.getScale(gl.relationWidthHeight));
        //gl.inclineGlass(gl.gamma,0, 0);
    }

    public void rotateRight(MaterialObject matObj) {
        matObj.gamma.z = matObj.gamma.z - 0.02;
        matObj.setTransform3D(matObj.getScale(matObj.relationWidthHeight));
    }

    public void rotateLeft(MaterialObject matObj) {
        matObj.gamma.z = matObj.gamma.z + 0.02;
        matObj.setTransform3D(matObj.getScale(matObj.relationWidthHeight));
    }

    public void setMouseDragParameters(MouseEvent e) {
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
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println(JGlassApplet.pick.myColDet.intrsctMatObj + "");
        setMouseDragParameters(e);
        JGlassApplet.baulk.setBGBaulkIsAttach();
        if (JGlassApplet.pick.selectedObject != null) {
            //System.out.println(JGlassApplet.pick.selectedObject.y+"y");
            if (!(JGlassApplet.pick.selectedObject instanceof Glass)) {
                //Ball bll = (Ball) JGlassApplet.pick.selectedObject;
                JGlassApplet.pick.selectedObject.setOutsideGlassForMatObj();
//                if (JGlassApplet.glass != null) {
                    JGlassApplet.pick.selectedObject.setMovable(JGlassApplet.pick.myColDet.intrsctMatObj, e.getX(), e.getY(), 0);
//                }
            } else if (JGlassApplet.pick.selectedObject instanceof Glass) {
                JGlassApplet.pick.selectedObject.setMovable(JGlassApplet.pick.myColDet.intrsctMatObj, e.getX(), e.getY(), 0);
            }
        }
        if (JGlassApplet.pick.selectedObject != null && !JGlassApplet.motionZ.shiftPressed) {
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
                if (JGlassApplet.pick.selectedObject instanceof Scale
                        && JGlassApplet.pick.selectedObject.isRotate) {
                    if (JGlassApplet.pick.xMouse > e.getX()) {
                        rotateLeft(JGlassApplet.pick.selectedObject);
                    } else {
                        rotateRight(JGlassApplet.pick.selectedObject);
                    }
                } else {
                    JGlassApplet.pick.selectedObject.matObjMoveTo(e.getX() - JGlassApplet.pick.grabShiftX,
                            e.getY() - JGlassApplet.pick.grabShiftY,
                            JGlassApplet.pick.selectedObject.z);
                }
            }
        }
        if (JGlassApplet.pick.selectedObject != null && JGlassApplet.motionZ.shiftPressed) {
            if (JGlassApplet.pick.selectedObject.movable) {
                JGlassApplet.pick.selectedObject.matObjMoveTo(
                        JGlassApplet.pick.selectedObject.x,
                        JGlassApplet.pick.selectedObject.y,
                        JGlassApplet.pick.selectedObject.z - JGlassApplet.pick.selectedObject.y
                        + e.getY() - JGlassApplet.pick.grabShiftY);
            }
        }
        JGlassApplet.pick.xMouse = e.getX();
        JGlassApplet.pick.yMouse = e.getY();
    }
}
