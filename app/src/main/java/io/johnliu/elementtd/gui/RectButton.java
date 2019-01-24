package io.johnliu.elementtd.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class RectButton extends Button {

    protected float right;
    protected float bottom;

    public RectButton(float left, float top, float right, float bottom) {
        super(left, top);
        this.right = right;
        this.bottom = bottom;
    }

    // renders text in the very center of the button
    protected void renderCenterText(Canvas canvas, String text) {
        float width = paint.measureText(text);
        float h = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
        canvas.drawText(text,
                posX + (right - posY) / 2.0f - width / 2.0f,
                posY + (bottom - posY) / 2.0f + h / 2.0f, paint);
    }

    @Override
    public abstract void render(Canvas canvas, float deltaTime);
    @Override
    public abstract void doAction();

    @Override
    public boolean onTap(float x, float y) {
        if (x >= posX && x <= right && y >= posY && y <= bottom) {
            doAction();
            return true;
        }

        return false;
    }

    @Override
    public boolean onScroll(float startX, float startY, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean onScale(float x, float y, float scale) {
        return false;
    }

}
