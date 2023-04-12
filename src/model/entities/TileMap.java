package model.entities;

import model.Entity;
import UI.GamePanel;
import utils.enumerations.TileType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TileMap extends Entity {
    private Tile[][] tilemap;

    public TileMap(int x, int y) {
        super(x, y);
    }

    public void loadMap(String filepath) {

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
                        tile.setParent(this);
                        addChild(tile);
                        tileLine[i] = tile;
                    }
                }
                tilemap[j] = tileLine;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tile getTileByWorldPosition(int worldX, int worldY) {
        int localX = worldX - getWorldX();
        int localY = worldY - getWorldY();
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

    @Override
    public void update() {
        // Nothing
    }
}
