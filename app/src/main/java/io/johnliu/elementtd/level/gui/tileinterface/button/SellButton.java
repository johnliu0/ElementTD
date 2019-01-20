package io.johnliu.elementtd.level.gui.tileinterface.button;

import android.graphics.Canvas;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;
import io.johnliu.elementtd.level.tile.Tile;

public class SellButton extends Button {

    private Level level;
    private int targetX;
    private int targetY;

    public SellButton(
            Level level, TileInterface tileInterface,
            float posX, float posY, int targetX, int targetY
    ) {
        super(tileInterface, posX, posY);
        this.level = level;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {

    }

    @Override
    public void doAction() {
        Tile tile = level.getTile(targetX, targetY);
        level.sellTower(tile.getX(), tile.getY());
    }

}
