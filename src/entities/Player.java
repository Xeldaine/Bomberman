package entities;

import graphics.GamePanel;
import graphics.KeyHandler;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;

public class Player extends Entity{

    KeyHandler keyHandler = null;

    public Player(int x, int y, int speed) {
        super(x, y, speed);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
    }

    @Override
    public void update() {
        if (keyHandler.isNothingPressed()) {
            state = EntityState.IDLE;

        } else {
            state = EntityState.MOVING;

            if (keyHandler.downPressed) {
                y += speed;
                direction = EntityDirection.DOWN;
            }
            if (keyHandler.upPressed) {
                y -= speed;
                direction = EntityDirection.UP;
            }
            if (keyHandler.leftPressed) {
                x -= speed;
                direction = EntityDirection.LEFT;
            }
            if (keyHandler.rightPressed) {
                x += speed;
                direction = EntityDirection.RIGHT;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int tileSize = GamePanel.tileSize;
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(x, y, tileSize, tileSize);
    }
}
