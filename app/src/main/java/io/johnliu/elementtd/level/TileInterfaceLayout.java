package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.graphics.RectF;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class TileInterfaceLayout extends Layout {

    protected Level level;
    protected TileInterface tileInterface;
    protected int tileX;
    protected int tileY;
    protected float radius;
    private static Bitmap bgRing = null;
    private RectF bgRingRect;

    public TileInterfaceLayout(Level level, TileInterface tileInterface, int tileX, int tileY, float radius) {
        this.level = level;
        this.tileInterface = tileInterface;
        this.tileX = tileX;
        this.tileY = tileY;
        this.radius = radius;

        if (bgRing == null) {
            bgRing = ResourceLoader.decodeResource(R.drawable.button_bg_ring);
        }

        bgRingRect = new RectF(tileX + 0.5f - radius, tileY + 0.5f - radius, tileX + 0.5f + radius, tileY + 0.5f + radius);
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(bgRing, null, bgRingRect, null);
        super.render(engine);
    }

    // used to space out buttons in a circle around the tile
    protected Vec2f getCircleRadialPosition(float degrees) {
        degrees %= 360.0f;
        if (degrees < 0.0f) {
            degrees = 360.0f + degrees;
        }

        float radians = (float) Math.toRadians(degrees);
        float x = radius * (float) Math.abs(Math.cos(radians));
        float y = radius * (float) Math.abs(Math.sin(radians));
        if (degrees < 90.0f) {
            return new Vec2f(tileX + 0.5f + x, tileY + 0.5f + y);
        } else if (degrees < 180.0f) {
            return new Vec2f(tileX + 0.5f - x, tileY + 0.5f + y);
        } else if (degrees < 270.0f) {
            return new Vec2f(tileX + 0.5f - x, tileY + 0.5f - y);
        } else {
            return new Vec2f(tileX + 0.5f + x, tileY + 0.5f - y);
        }
    }

}
