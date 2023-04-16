package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Sprite2D;
import utils.enumerations.EnemyType;

public class Enemy extends Entity {

    private EnemyType type;

    public Enemy(int x, int y, EnemyType type) {
        super(x, y);
        this.type = type;
        sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 2,  type.getPath());
    }

    @Override
    protected void update() {

    }
}
