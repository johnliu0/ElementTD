package io.johnliu.elementtd.level.gui.tileinterface.button;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;

public abstract class Button {

    // buttons are circles
    protected float posX;
    protected float posY;
    protected float radius;
    protected TileInterface tileInterface;
    private Paint paint;

    public Button(TileInterface tileInterface, float posX, float posY) {
        this.tileInterface = tileInterface;
        this.posX = posX;
        this.posY = posY;
        this.radius = 0.15f;
        paint = new Paint();
        paint.setColor(Color.rgb(128, 128, 128));
    }

    public void render(Canvas canvas, float deltaTime) {
        canvas.drawCircle(posX, posY, radius, paint);
    }

    public abstract void doAction();

    public boolean contains(float x, float y) {
        float diffX = x - this.posX;
        float diffY = y - this.posY;
        return diffX * diffX + diffY * diffY <= radius * radius;
    }

}
