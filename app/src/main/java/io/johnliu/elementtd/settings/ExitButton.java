package io.johnliu.elementtd.settings;

import android.graphics.Canvas;
import android.graphics.Paint;

import io.johnliu.elementtd.gui.RectButton;

public class ExitButton extends RectButton {

    private Settings settings;

    public ExitButton(Settings settings, float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        this.settings = settings;
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        canvas.drawRect(posX, posY, right, bottom, new Paint());
    }

    @Override
    public void doAction() {
        //settings.exit();
    }

}
