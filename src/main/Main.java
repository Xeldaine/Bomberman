package main;
import UI.GamePanel;
import utils.Const;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(Const.gameName);
        GamePanel gamePanel = GamePanel.getInstance();
        gamePanel.loadMap();
        gamePanel.startGame();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
