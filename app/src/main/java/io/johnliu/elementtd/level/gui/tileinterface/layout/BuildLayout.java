package io.johnliu.elementtd.level.gui.tileinterface.layout;

import android.graphics.Canvas;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;
import io.johnliu.elementtd.level.gui.tileinterface.button.BuildButton;
import io.johnliu.elementtd.level.gui.tileinterface.button.CancelButton;
import io.johnliu.elementtd.level.tower.BasicTower;

public class BuildLayout extends Layout {

    private CancelButton cancelButton;
    private ArrayList<BuildButton> buildButtons;

    public BuildLayout(Level level, TileInterface tileInterface, int tileX, int tileY) {
        super(level, tileInterface, tileX, tileY);

        Point2d cancelButtonPos = super.getCircleRadialPosition(120.0f);
        cancelButton = new CancelButton(tileInterface, cancelButtonPos.x, cancelButtonPos.y);

        Point2d basicTowerPos = super.getCircleRadialPosition(-90.0f);
        buildButtons = new ArrayList<BuildButton>();
        buildButtons.add(new BuildButton(level, tileInterface, basicTowerPos.x, basicTowerPos.y, tileX, tileY, BasicTower.TOWER_ID));
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        super.render(canvas, deltaTime);
        cancelButton.render(canvas, deltaTime);
        for (BuildButton button : buildButtons) {
            button.render(canvas, deltaTime);
        }
    }

    @Override
    public boolean onPress(float tileX, float tileY) {
        if (cancelButton.contains(tileX, tileY)) {
            cancelButton.doAction();
            return true;
        }

        for (BuildButton button : buildButtons) {
            if (button.contains(tileX, tileY)) {
                tileInterface.openConfirmLayout(button);
                return true;
            }
        }

        return false;
    }

}
