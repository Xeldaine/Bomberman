package UI;

import utils.enumerations.EntityDirection;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public  Boolean upPressed = false, downPressed = false, rightPressed = false, leftPressed =  false;
    public Boolean spacePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            default -> {}
        }
    }

    public Boolean arrowNotPressed() {
        return !upPressed && !downPressed && !rightPressed && !leftPressed;
    }

    public EntityDirection getDirectionByKeyPressed() {
        if (downPressed) {
            return EntityDirection.DOWN;
        }
        if (upPressed) {
            return EntityDirection.UP;
        }
        if (leftPressed) {
            return EntityDirection.LEFT;
        }
        if (rightPressed) {
            return EntityDirection.RIGHT;
        }

        return null;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
            default -> {}
        }
    }
}
