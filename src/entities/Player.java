package entities;

import graphics.GamePanel;
import graphics.KeyHandler;

import java.awt.*;

public class Player extends Entity{

    KeyHandler keyHandler = null;

    public Player(int x, int y, int speed) {
        super();
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    @Override
    public void update() {
        y += keyHandler.downPressed ? speed : 0;
        y -= keyHandler.upPressed ? speed : 0;
        x += keyHandler.rightPressed ? speed : 0;
        x -= keyHandler.leftPressed ? speed : 0;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int tileSize = GamePanel.tileSize;
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(x, y, tileSize, tileSize);
    }
}
