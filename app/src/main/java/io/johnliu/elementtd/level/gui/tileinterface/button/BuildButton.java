package io.johnliu.elementtd.level.gui.tileinterface.button;

import android.graphics.Canvas;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;

public class BuildButton extends Button {

    private Level level;
    private int targetX;
    private int targetY;
    private int towerId;

    public BuildButton(
            Level level, TileInterface tileInterface,
            float posX, float posY,
            int targetX, int targetY, int towerId
    ) {
        super(tileInterface, posX, posY);
        this.level = level;
        this.towerId = towerId;
        this.targetX = targetX;
        this.targetY = targetY;
        this.towerId = towerId;
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        super.render(canvas, deltaTime);
    }

    @Override
    public void doAction() {
        level.buildTower(targetX, targetY, towerId);
    }

}
