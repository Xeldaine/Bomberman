package UI;

import model.components.Camera2D;
import model.Entity;
import model.entities.Player;
import model.entities.TileMap;
import utils.Const;
import utils.LogUtils;

import javax.swing.*;
import java.awt.*;
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
    private final ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    private GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Const.backgroundGrey);
        setDoubleBuffered(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void loadMap() {
        currPlayer = new Player(0, 0);
        Camera2D.getInstance().setEntity(currPlayer);
        currTileMap = new TileMap(0, 0);
        currTileMap.loadMap(Const.map01Path);
        currTileMap.disposeBricksAndEnemiesRandomly();
        currTileMap.disposePlayer(currPlayer);
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
                System.out.printf("[%s] FPS: %d%n", LogUtils.getCurrentDateString(), actualFPS);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // order by priority of which each entity will be drawn on screen
        List<Entity> entitiesToDraw = new ArrayList<>(entities);

        for (Entity entity : entities) {
            entitiesToDraw.addAll(entity.getFamily());
        }

        entitiesToDraw.sort((e1, e2) -> {
            int prio1 = e1.getSprite2D() != null ? e1.getSprite2D().getPriority() : 0;
            int prio2 = e2.getSprite2D() != null ? e2.getSprite2D().getPriority() : 0;

            // y sorting
            if (prio1 == prio2) {
                int y1 = e1.getWorldY() + (e1.getArea2D() != null ? e1.getArea2D().y : 0);
                int y2 = e2.getWorldY() + (e2.getArea2D() != null ? e2.getArea2D().y : 0);

                return y1 - y2;
            }

            return prio1 - prio2;
        });

        for (Entity entity: entitiesToDraw) {
            entity.draw(graphics2D);
        }

        graphics2D.setFont(Const.getFont(Const.emulogicFontPath, Font.PLAIN, 18));
        graphics2D.setColor(Color.white);
        graphics2D.drawString("SCORE: " + (currPlayer != null ? currPlayer.getScore() : 0) , 30, 30);
        graphics2D.drawString("LEFT: " + (currPlayer != null ? currPlayer.getLives() : 0), GamePanel.screenWidth - 150, 30);

        graphics2D.dispose();
    }
}
