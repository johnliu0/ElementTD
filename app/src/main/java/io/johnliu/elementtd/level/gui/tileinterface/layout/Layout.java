package io.johnliu.elementtd.level.gui.tileinterface.layout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;

public abstract class Layout {

    protected Level level;
    protected TileInterface tileInterface;
    protected int tileX;
    protected int tileY;
    private Paint paint;
    private float radius;

    public Layout(Level level, TileInterface tileInterface, int tileX, int tileY) {
        this.level = level;
        this.tileInterface = tileInterface;
        this.radius = 1.0f;
        this.tileX = tileX;
        this.tileY = tileY;
        paint = new Paint();
        paint.setColor(Color.argb(128, 128, 128, 128));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0.02f);
    }

    public void render(Canvas canvas, float deltaTime) {
        canvas.drawCircle(tileX + 0.5f, tileY + 0.5f, radius, paint);
    }

    // attempts to register an input event
    // returns whether or not the event did anything
    // i.e. whether or not a button was pressed
    public abstract boolean onPress(float tileX, float tileY);

    // returns at a distance radius away from the given coordinate
    // rotated some degrees counter-clockwise from (1, 0)
    // this is used for button "collision detection" and rendering
    // since all buttons surround the selected tile in a circle
    protected Point2d getCircleRadialPosition(float degrees) {
        degrees %= 360.0f;
        float radians = degrees % 180;
        if (radians > 90.0f) {
            radians = 180.0f - radians;
        }
        radians = (float) Math.toRadians(radians);
        float x = radius * (float) Math.cos(radians);
        float y = radius * (float) Math.sin(radians);
        if (degrees < 90.0f) {
            return new Point2d(tileX + 0.5f + x, tileY + 0.5f + y);
        } else if (degrees < 180.0f) {
            return new Point2d(tileX + 0.5f - x, tileY + 0.5f + y);
        } else if (degrees < 270.0f) {
            return new Point2d(tileX + 0.5f - x, tileY + 0.5f - y);
        } else {
            return new Point2d(tileX + 0.5f + x, tileY + 0.5f - y);
        }
    }

}
