package UI;

import model.components.Area2D;
import model.components.Camera2D;
import model.Entity;
import model.entities.Player;
import model.entities.Tile;
import model.entities.TileMap;
import utils.Const;
import utils.LogUtils;
import utils.enumerations.EntityDirection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    public static final int originalTileSize = 32;
    public static final int scale = 2;
    public static final int tileSize = originalTileSize * scale;
    public static final int screenHeight = 720;
    public static final int screenWidth = 1280;
    public static final int FPS = 60;
    private static GamePanel instance;
    private Thread gameThread;
    private final KeyHandler keyHandler;
    private TileMap currTileMap;
    private Player currPlayer;
    private Boolean isGameOver = false;
    private ArrayList<Entity> entities = new ArrayList<>();

    private GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void loadEntities() {
        currPlayer = new Player(tileSize, tileSize);
        Camera2D.getInstance().setEntity(currPlayer);
        currTileMap = new TileMap(0, 0);
        currTileMap.loadMap(Const.map01Path);
        entities.add(currTileMap);
        entities.add(currPlayer);
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }

        return instance;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }

    public Boolean getGameOver() {
        return isGameOver;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public TileMap getCurrTileMap() {
        return currTileMap;
    }

    public void startGame() {
        if (gameThread != null) {
            gameThread.interrupt();
        }
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        final double timeBetweenFrames = Math.pow(10, 9) / FPS; // in nanoseconds
        long lastTime = System.nanoTime();
        int accumulator = 0;
        long timer = 0;
        int actualFPS = 0;

        while (!isGameOver) {
            long currentTime = System.nanoTime();
            accumulator += currentTime - lastTime;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (accumulator >= timeBetweenFrames) {
                accumulator -= timeBetweenFrames;
                update();
                repaint();
                actualFPS++;
            }

            //log
            if (timer >= Math.pow(10, 9)) {
                System.out.println(String.format("[%s] FPS: %d", LogUtils.getCurrentDateString(), actualFPS));
                actualFPS = 0;
                timer = 0;
            }
        }
    }

    private void update(){
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public void checkCollision(Entity entity){
        Area2D area = entity.getArea2D();
        EntityDirection direction = entity.getDirection();
        entity.setSpeed(Const.defaultSpeed);

        for (int i = 1; i <= Const.defaultSpeed; i++) {
            int x = entity.getWorldX() + (area != null ? area.x : 0);
            int y = entity.getWorldY() + (area != null ? area.y : 0);

            int offsetX = 0;
            int offsetY = 0;
            switch (direction) {
                case UP: y -= i; offsetY = -i; break;
                case DOWN: y += (area != null ? area.height : 0) + i; offsetY = i; break;
                case LEFT: x -= i; offsetX = -i; break;
                case RIGHT: x += (area != null ? area.width : 0) + i; offsetX = i; break;
            }

            Tile tile = currTileMap.getTileByWorldPosition(x, y);

            if (tile != null) {
                Area2D areaEntity = entity.getArea2D();
                Area2D areaTile = tile.getArea2D();
                if (areaEntity.intersectsWithOffset(areaTile, offsetX, offsetY) && tile.isCollisionEnabled()) {
                    entity.setSpeed(i-1);
                    return;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        for (Entity entity : entities) {
            entity.draw(graphics2D);
        }

        graphics2D.dispose();
    }
}
