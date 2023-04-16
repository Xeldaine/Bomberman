package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Area2D;
import model.components.Sprite2D;
import model.interfaces.Sprite2DListener;
import utils.MathUtils;
import utils.enumerations.EnemyType;
import utils.enumerations.EntityDirection;
import utils.enumerations.TileType;

import java.util.ArrayList;

public class Enemy extends Entity implements Sprite2DListener {

    private EnemyType type;
    private Boolean dead = false;

    public Enemy(int x, int y, EnemyType type) {
        super(x, y);
        this.type = type;
        sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize,  type.getPath());
        sprite2D.setPriority(2);
        area2D = new Area2D(8 * GamePanel.scale, 10 * GamePanel.scale, 16 * GamePanel.scale, 18 * GamePanel.scale, this);
        changeDirection();
        setSpeed(getSpeed() / 2);
    }

    @Override
    protected void update() {

        if (direction == null) {
            return;
        }

        if (!dead) {
            // the enemy is not dead so it can move
            switch (direction) {
                case DOWN -> {
                    y += speed;
                    sprite2D.setAnimationIndexes(0, 1);
                }
                case UP -> {
                    y -= speed;
                    sprite2D.setAnimationIndexes(0, 1);
                }
                case LEFT -> {
                    x -= speed;
                    sprite2D.setAnimationIndexes(0, 1);
                }
                case RIGHT -> {
                    x += speed;
                    sprite2D.setAnimationIndexes(2, 3);
                }
            }
        } else {
            switch (direction) {
                case UP, DOWN, LEFT -> sprite2D.setAnimationIndexes(4, 6);
                case RIGHT -> sprite2D.setAnimationIndexes(7, 9);
            }
        }
    }

    private void startAnimationDeath() {
        this.dead = true;
        sprite2D.setPriority(5);
        sprite2D.setListener(this);
    }

    private void changeDirection() {
        TileMap currMap = GamePanel.getInstance().getCurrTileMap();
        if (currMap != null) {
            Tile currTile = currMap.getTileByWorldPosition(getWorldX() + area2D.x, getWorldY() + area2D.y);
            ArrayList<EntityDirection> possibleDirection = new ArrayList<>();
            Tile[] adiacentTiles = currMap.getAdiacentTiles(currTile);
            for (int i = 0; i < adiacentTiles.length; i++) {
                Tile tile = adiacentTiles[i];
                if (tile != null && tile.getType() == TileType.GRASS) {
                    possibleDirection.add(EntityDirection.values()[i]);
                }
            }
            if (!possibleDirection.isEmpty()) {
                direction = possibleDirection.get(MathUtils.getRandomValueBetween(0, possibleDirection.size()));
            }
        }
    }

    @Override
    public void onCollisionHit(Area2D area) {
        super.onCollisionHit(area);
        changeDirection();
    }

    @Override
    public void onAreaEntered(Area2D area) {
        super.onAreaEntered(area);
        if (area.getEntity() instanceof Explosion && !dead) {
            startAnimationDeath();
        }
    }

    @Override
    public void didChangeFrame() {

    }

    @Override
    public void didEndAnimation() {
        this.destroy();
    }
}
