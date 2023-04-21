package model.entities;

import model.Entity;
import UI.GamePanel;
import utils.enumerations.EnemyType;
import utils.enumerations.EntityDirection;
import utils.enumerations.TileType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileMap extends Entity {
    private float bricksPercentageRate = 0.3f;
    private float enemyPercentageRate = 0.1f;

    private Tile[][] tilemap;

    public TileMap(int x, int y) {
        super(x, y);
        this.isStatic = true;
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

    public float getBricksPercentageRate() {
        return bricksPercentageRate;
    }

    public void setBricksPercentageRate(float bricksPercentageRate) {
        this.bricksPercentageRate = bricksPercentageRate;
    }

    public float getEnemyPercentageRate() {
        return enemyPercentageRate;
    }

    public void setEnemyPercentageRate(float enemyPercentageRate) {
        this.enemyPercentageRate = enemyPercentageRate;
    }

    public Tile getTile(int i, int j) {
        if (j >= 0 && j < tilemap.length) {
            Tile[] row = tilemap[j];
            if (i >= 0 && i < row.length) {
                return row[i];
            }
        }

        return null;
    }

    public Tile getTileByWorldPosition(int worldX, int worldY) {
        int localX = worldX - getWorldX();
        int localY = worldY - getWorldY();
        int i = localX / GamePanel.tileSize;
        int j = localY / GamePanel.tileSize;

        return getTile(i, j);
    }

    public void disposeBricksAndEnemiesRandomly() {
        for (Tile[] row : tilemap) {
            for (Tile tile : row) {
                if (tile != null && tile.getType() == TileType.GRASS) {
                    if (Math.random() < bricksPercentageRate) {
                        tile.setType(TileType.BRICK);
                    } else if (Math.random() < enemyPercentageRate) {
                        Enemy enemy = new Enemy(tile.getX(), tile.getY(), EnemyType.generateRandomType());
                        this.addChild(enemy);
                    }
                }
            }
        }
    }

    public List<Tile> getFreeTiles() {
        ArrayList<Tile> freeTiles = new ArrayList<>();
        for (Tile[] row : tilemap) {
            for (Tile tile : row) {
                if (tile != null && tile.getType() == TileType.GRASS) {
                    freeTiles.add(tile);
                }
            }
        }

        return freeTiles;
    }

    public Tile getRandomFreeTile() {
        List<Tile> tiles = getFreeTiles();
        if (tiles.size() == 0) {
            return null;
        }

        return tiles.get((int)(Math.random() * (tiles.size() - 1)));
    }

    public void disposePlayer(Player player) {
        Tile tile = getRandomFreeTile();
        if (tile != null) {
            player.setX(tile.getX());
            player.setY(tile.getY());
            this.addChild(player);
        }
    }

    public Tile getAdiacentTilesByDirection(Tile tile, EntityDirection direction) {

        Tile result = null;

        if (direction == null || tile == null) {
            return result;
        }

        int i = tile.getX() / GamePanel.tileSize;
        int j = tile.getY() / GamePanel.tileSize;

        switch (direction) {
            case DOWN ->  result = getTile(i, j + 1);
            case UP -> result = getTile(i, j - 1);
            case RIGHT -> result = getTile(i + 1, j);
            case LEFT -> result = getTile(i - 1, j);
        }

        return result;
    }

    public Tile[] getAdiacentTiles(Tile tile) {
        Tile[] tiles = new Tile[EntityDirection.values().length];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = getAdiacentTilesByDirection(tile, EntityDirection.values()[i]);
        }

        return tiles;
    }

    @Override
    protected void update() {
        // Nothing
    }
}
