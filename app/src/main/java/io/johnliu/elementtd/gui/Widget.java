package io.johnliu.elementtd.gui;

import android.graphics.Canvas;

public abstract class Widget {

    protected float posX;
    protected float posY;

    public Widget() {
        posX = 0.0f;
        posY = 0.0f;
    }

    public Widget(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void init() {}

    public abstract void render(Canvas canvas, float deltaTime);
    public abstract boolean onTap(float x, float y);
    public abstract boolean onScroll(float startX, float startY, float dx, float dy);
    public abstract boolean onScale(float x, float y, float scale);

}
