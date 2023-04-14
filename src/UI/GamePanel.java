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
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    private GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void loadMapAndPlayer() {
        currPlayer = new Player(tileSize, tileSize);
        Camera2D.getInstance().setEntity(currPlayer);
        currTileMap = new TileMap(0, 0);
        currTileMap.loadMap(Const.map01Path);
        addEntity(currPlayer);
        addEntity(currTileMap);
    }

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }

        return instance;
    }

    @Override
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        super.firePropertyChange(propertyName, oldValue, newValue);
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public TileMap getCurrTileMap() {
        return currTileMap;
    }

    public void setCurrTileMap(TileMap currTileMap) {
        this.currTileMap = currTileMap;
    }

    public void addEntity(Entity entity) {
        this.addPropertyChangeListener(entity);
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.removePropertyChangeListener(entity);
        entities.remove(entity);
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

        while (gameThread != null) {
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

    private void update() {
        for (Entity entity: entities) {
            entity.updateData();
        }
    }

    public void checkCollision(Entity entity){
        Area2D area = entity.getArea2D();
        EntityDirection direction = entity.getDirection();
        entity.setSpeed(Const.defaultSpeed);

        for (int i = 1; i <= Const.defaultSpeed; i++) {
            int x1 = entity.getWorldX() + (area != null ? area.x : 0);
            int y1 = entity.getWorldY() + (area != null ? area.y : 0);
            int x2 = x1;
            int y2 = y1;

            int offsetX = 0;
            int offsetY = 0;

            int height = area != null ? area.height : 0;
            int width = area != null ? area.width : 0;

            switch (direction) {
                case UP:
                    y1 -= i;
                    x2 += width;
                    y2 = y1;
                    offsetY = -i;
                    break;
                case DOWN:
                    y1 += height + i;
                    x2 += width;
                    y2 = y1;
                    offsetY = i;
                    break;
                case LEFT:
                    x1 -= i;
                    x2 = x1;
                    y2 += height;
                    offsetX = -i;
                    break;
                case RIGHT:
                    x1 += width + i;
                    x2 = x1;
                    y2 += height;
                    offsetX = i;
                    break;
            }

            Tile tile1 = currTileMap.getTileByWorldPosition(x1, y1);
            Tile tile2 = currTileMap.getTileByWorldPosition(x2, y2);

            for (Tile tile : new Tile[]{tile1, tile2}) {
                if (tile != null) {
                    Area2D areaEntity = entity.getArea2D();
                    Area2D areaTile = tile.getArea2D();
                    if (areaEntity.intersectsWithOffset(areaTile, offsetX, offsetY) && tile.isCollisionEnabled()) {
                        entity.setSpeed(i - 1);
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        List<Entity> entitiesToDraw = new ArrayList<>(entities);
        Collections.sort(entitiesToDraw, (e1, e2) -> {
            int prio1 = e1.getSprite2D() != null ? e1.getSprite2D().getPriority() : 0;
            int prio2 = e2.getSprite2D() != null ? e2.getSprite2D().getPriority() : 0;

            return prio1 - prio2;
        });

        for (Entity entity: entitiesToDraw) {
            entity.draw(graphics2D);
        }

        graphics2D.dispose();
    }
}
