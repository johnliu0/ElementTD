package io.johnliu.elementtd.gui;

import android.graphics.Paint;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class Button extends Widget {

    protected Paint paint;
    protected float posX;
    protected float posY;

    public Button() {
        this(0.0f, 0.0f);
    }

    public Button(float posX, float posY) {
        paint = new Paint();
        paint.setTypeface(ResourceLoader.getDefaultFontBold());
        paint.setTextSize(Game.FONT_SIZE_LG);
        this.posX = posX;
        this.posY = posY;
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
    }

    public abstract void doAction();

    @Override
    public abstract void render(RenderEngine engine);

    @Override
    public abstract boolean onTap(float x, float y);

    @Override
    public abstract boolean onScroll(float startX, float startY, float dx, float dy);

    @Override
    public abstract boolean onScale(float x, float y, float scale);

}
