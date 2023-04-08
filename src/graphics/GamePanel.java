package graphics;

import utils.LogUtils;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    static final int originalTileSize = 32;
    static final int scale = 2;
    static final int columnNumber = 20;
    static final int rowNumber = 12;
    static final int tileSize = originalTileSize * scale;
    static final int screenHeight = rowNumber * tileSize;
    static final int screenWidth = columnNumber * tileSize;
    static final int FPS = 60;
    int speed = 240 / FPS;

    private final Thread gameThread;
    private final KeyHandler keyHandler;
    private Boolean isGameOver = false;
    int x = 128;
    int y = 128;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.black);
        setDoubleBuffered(true);
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        gameThread = new Thread(this);
        setFocusable(true);
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
        y += keyHandler.downPressed ? speed : 0;
        y -= keyHandler.upPressed ? speed : 0;
        x += keyHandler.rightPressed ? speed : 0;
        x -= keyHandler.leftPressed ? speed : 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(Color.white);
        graphics2D.fillRect(x, y, tileSize, tileSize);
        graphics2D.dispose();
    }
}
