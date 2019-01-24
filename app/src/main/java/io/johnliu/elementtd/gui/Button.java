package io.johnliu.elementtd.gui;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Button extends Widget {

    protected Paint paint;
    protected float posX;
    protected float posY;

    public Button() {
        paint = new Paint();
        posX = 0.0f;
        posY = 0.0f;
    }

    public Button(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
    }

    public abstract void doAction();

    @Override
    public abstract void render(Canvas canvas, float deltaTime);
    @Override
    public abstract boolean onTap(float x, float y);
    @Override
    public abstract boolean onScroll(float startX, float startY, float dx, float dy);
    @Override
    public abstract boolean onScale(float x, float y, float scale);

}
