/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Alex
 */
public class MotionToAxisZ extends KeyAdapter {

    public static boolean shiftPressed = false;

    public MotionToAxisZ() {
        JGlassApplet.scene.canvas.addKeyListener(this);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
//        ke.setKeyCode(KeyEvent.ALT_DOWN_MASK);
        if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
    }

}
