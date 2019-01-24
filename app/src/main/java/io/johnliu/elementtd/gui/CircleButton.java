package io.johnliu.elementtd.gui;

import android.graphics.Canvas;

public abstract class CircleButton extends Button {

    protected float radius;

    public CircleButton(float cx, float cy, float radius) {
        super(cx, cy);
        this.radius = radius;
    }

    @Override
    public abstract void render(Canvas canvas, float deltaTime);
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
