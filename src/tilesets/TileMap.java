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
    private Tile[][] tilemap;
    private int worldX, worldY;
    private int screenX, screenY;

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

            tilemap = new Tile[lines.size()][];
            int tileSize = GamePanel.tileSize;

            for (int j = 0; j < lines.size(); j++) {
                int[] line = tilemapRaw[j];
                Tile[] tileLine = new Tile[line.length];
                for (int i = 0; i < line.length; i++) {
                    TileType type = TileType.rawValue(line[i]);
                    if (type != null) {
                        Tile tile = new Tile(i * tileSize, j * tileSize, type);
                        tileLine[i] = tile;
                    }
                }
                tilemap[j] = tileLine;
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

    public Tile getTileByWorldPosition(int worldX, int worldY) {
        int localX = worldX - this.worldX;
        int localY = worldY - this.worldY;
        int i = localX / GamePanel.tileSize;
        int j = localY / GamePanel.tileSize;

        if (j >= 0 && j < tilemap.length) {
            Tile[] line = tilemap[j];
            if (i >= 0 && i < line.length) {
                return line[i];
            }
        }

        return null;
    }

    public void draw(Graphics2D graphics2D) {
        if (tilemap != null) {
            for (Tile[] line: tilemap) {
                for (Tile tile: line) {
                    tile.setWorldX(worldX);
                    tile.setWorldY(worldY);
                    tile.setScreenX(screenX);
                    tile.setScreenY(screenY);
                    tile.draw(graphics2D);
                }
            }
        }
    }
}
