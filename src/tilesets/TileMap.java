package tilesets;

import graphics.GamePanel;
import utils.enumerations.TileType;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileMap {
    ArrayList<Tile> tilemap;
    int worldX, worldY;

    int screenX, screenY;

    public void loadMap(String filepath, int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.screenX = worldX;
        this.screenY = worldY;

        int[][] tilemapRaw;

        try {
            Path path = Paths.get(filepath);
            List<String> lines = Files.readAllLines(path);

            tilemapRaw = new int[lines.size()][];

            for (int i = 0; i < lines.size(); i++) {
                String stringLine = lines.get(i);
                String[] line = stringLine.split(" ");
                int[] lineInt = Arrays.stream(line).mapToInt(Integer::valueOf).toArray();
                tilemapRaw[i] = lineInt;
            }

            tilemap = new ArrayList<>();
            int tileSize = GamePanel.tileSize;

            for (int j = 0; j < tilemapRaw.length; j++) {
                int[] line = tilemapRaw[j];
                for (int i = 0; i < line.length; i++) {
                    TileType type = TileType.rawValue(line[i]);
                    if (type != null) {
                        Tile tile = new Tile(i * tileSize, j * tileSize, type);
                        tilemap.add(tile);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void draw(Graphics2D graphics2D) {
        if (tilemap != null) {
            for (Tile tile : tilemap) {
                tile.setWorldX(worldX);
                tile.setWorldY(worldY);
                tile.setScreenX(screenX);
                tile.setScreenY(screenY);
                tile.draw(graphics2D);
            }
        }
    }
}
