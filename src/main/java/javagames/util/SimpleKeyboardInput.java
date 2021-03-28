package javagames.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimpleKeyboardInput implements KeyListener {
    private  boolean[] keys;

    public SimpleKeyboardInput() {
        keys = new boolean[256];
    }

    public synchronized boolean keyDown(int keyCode) {
        return keys[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public synchronized void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if(keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if( keyCode >= 0 && keyCode < keys.length ) {
            keys[ keyCode ] = false;
        }
    }
}
