package io.johnliu.elementtd.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class CircleButton extends Button {

    protected float radius;
    protected Bitmap icon;

    public CircleButton(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
        icon = null;
        init();
    }

    @Override
    public abstract void render(RenderEngine engine);

    protected void renderIcon(Canvas canvas) {
        if (icon != null) {
            canvas.drawBitmap(icon, null, new RectF(
                    posX - radius, posY - radius, posX + radius, posY + radius
            ), null);
        }
    }

    @Override
    public abstract void doAction();

    @Override
    public boolean onTap(float x, float y) {
        float diffX = x - posX;
        float diffY = y - posY;
        if (diffX * diffX + diffY * diffY <= radius * radius) {
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
