package graphics;

import components.Camera2D;
import entities.Entity;
import entities.Player;
import tilesets.TileMap;
import utils.Const;
import utils.LogUtils;

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
    private final Thread gameThread;
    private final KeyHandler keyHandler;
    private TileMap tileMap;
    private Boolean isGameOver = false;

    private ArrayList<Entity> entities = new ArrayList<>();

    private GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        tileMap = new TileMap();
        tileMap.loadMap(Const.map01Path, 0, 0);
        gameThread = new Thread(this);
        setFocusable(true);
    }

    public void loadEntities() {
        Player player = new Player(tileSize, tileSize);
        Camera2D.getInstance().setEntity(player);
        entities.add(player);
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

    public void startGame() {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        Camera2D camera2D = Camera2D.getInstance();
        tileMap.setScreenX(camera2D.getOffsetX());
        tileMap.setScreenY(camera2D.getOffsetY());
        tileMap.draw(graphics2D);

        for (Entity entity : entities) {
            entity.draw(graphics2D);
        }

        graphics2D.dispose();
    }
}
