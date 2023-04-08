package graphics;

import entities.Entity;
import entities.Player;
import utils.LogUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    static final int originalTileSize = 32;
    static final int scale = 2;
    static final int columnNumber = 20;
    static final int rowNumber = 12;
    public static final int tileSize = originalTileSize * scale;
    public static final int screenHeight = rowNumber * tileSize;
    public static final int screenWidth = columnNumber * tileSize;
    public static final int FPS = 60;
    private static GamePanel gp;
    private final Thread gameThread;
    private final KeyHandler keyHandler;
    private Boolean isGameOver = false;

    private ArrayList<Entity> entities = new ArrayList<Entity>();

    private GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        gameThread = new Thread(this);
        setFocusable(true);
    }

    public void loadEntities() {
        entities.add(new Player(100, 100, 4));
    }

    public static GamePanel getInstance() {
        if (gp == null) {
            gp = new GamePanel();
        }

        return gp;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
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
                actualFPS ++;
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

        for (Entity entity : entities) {
            entity.draw(graphics2D);
        }

        graphics2D.dispose();
    }
}
