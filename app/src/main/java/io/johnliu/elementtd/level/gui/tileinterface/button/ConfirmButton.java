package io.johnliu.elementtd.level.gui.tileinterface.button;

import android.graphics.Canvas;

import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;

public class ConfirmButton extends Button {

    public ConfirmButton(TileInterface tileInterface, float posX, float posY) {
        super(tileInterface, posX, posY);
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        super.render(canvas, deltaTime);
    }

    @Override
    public void doAction() {
        tileInterface.confirmAction();
    }

}
