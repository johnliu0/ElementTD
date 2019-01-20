package io.johnliu.elementtd.level.gui.tileinterface.button;

import android.graphics.Canvas;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;

public class UpgradeButton extends Button {

    private Level level;
    private int targetX;
    private int targetY;

    public UpgradeButton(
            Level level, TileInterface tileInterface,
            float posX, float posY, int targetX, int targetY
    ) {
        super(tileInterface, posX, posY);
        this.level = level;
        this.targetX = targetX;
        this.targetY = targetY;
    }


    public void render(Canvas canvas, float deltaTime) {

    }

    @Override
    public void doAction() {

    }

}
