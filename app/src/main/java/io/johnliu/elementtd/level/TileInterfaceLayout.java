package io.johnliu.elementtd.level;

import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class TileInterfaceLayout extends Layout {

    protected Level level;
    protected TileInterface tileInterface;
    protected int tileX;
    protected int tileY;
    protected float radius;

    public TileInterfaceLayout(Level level, TileInterface tileInterface, int tileX, int tileY, float radius) {
        this.level = level;
        this.tileInterface = tileInterface;
        this.tileX = tileX;
        this.tileY = tileY;
        this.radius = radius;
    }

    @Override
    public void render(RenderEngine engine) {
        super.render(engine);
    }

    // used to space out buttons in a circle around the tile
    protected Vec2f getCircleRadialPosition(float degrees) {
        degrees %= 360.0f;
        float radians = degrees % 180;
        if (radians > 90.0f) {
            radians = 180.0f - radians;
        }
        radians = (float) Math.toRadians(radians);
        float x = radius * (float) Math.cos(radians);
        float y = radius * (float) Math.sin(radians);
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
