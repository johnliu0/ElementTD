package io.johnliu.elementtd.level.gui.tileinterface.layout;

import android.graphics.Canvas;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;
import io.johnliu.elementtd.level.gui.tileinterface.button.CancelButton;
import io.johnliu.elementtd.level.gui.tileinterface.button.ConfirmButton;

public class ConfirmLayout extends Layout {

    private ConfirmButton confirmButton;
    private CancelButton cancelButton;

    public ConfirmLayout(Level level, TileInterface tileInterface, int tileX, int tileY) {
        super(level, tileInterface, tileX, tileY);

        Point2d confirmButtonPos = super.getCircleRadialPosition(60.0f);
        confirmButton = new ConfirmButton(tileInterface, confirmButtonPos.x, confirmButtonPos.y);

        Point2d cancelButtonPos = super.getCircleRadialPosition(120.0f);
        cancelButton = new CancelButton(tileInterface, cancelButtonPos.x, cancelButtonPos.y);
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        super.render(canvas, deltaTime);
        confirmButton.render(canvas, deltaTime);
        cancelButton.render(canvas, deltaTime);
    }

    @Override
    public boolean onPress(float tileX, float tileY) {
        if (confirmButton.contains(tileX, tileY)) {
            confirmButton.doAction();
            return true;
        }

        if (cancelButton.contains(tileX, tileY)) {
            cancelButton.doAction();
            return true;
        }

        return false;
    }

}
