package io.johnliu.elementtd.gui;

import android.graphics.Canvas;
import android.graphics.Color;

import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class RectButton extends Button {

    protected float right;
    protected float bottom;

    public RectButton(float left, float top, float right, float bottom) {
        super(left, top);
        this.right = right;
        this.bottom = bottom;
        init();
    }

    // renders text in the very center of the button
    protected void renderCenterText(RenderEngine engine, String text) {
        float width = paint.measureText(text);
        // with quattrocentro the font metrics are a little wonky
        // this below formula seems to center it the best
        float height = -(paint.getFontMetrics().ascent + paint.getFontMetrics().descent);

        engine.getCanvas().drawText(text,
                posX + (right - posX) / 2.0f - width / 2.0f,
                posY + (bottom - posY) / 2.0f + height / 2.0f, paint);
    }

    @Override
    public abstract void render(RenderEngine engine);

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
