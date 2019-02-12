package io.johnliu.elementtd.renderengine.entity.projectile;

import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class ProjectileEntity {

    protected static Paint paint = null;
    protected float[] positions;
    protected float targetX;
    protected float targetY;

    public ProjectileEntity(float x, float y, float targetX, float targetY) {
        positions = new float[] {x, y, x, y};

        if (paint == null) {
            paint = new Paint();
        }

        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void render(RenderEngine engine) {
        Vec2f pos = interp(engine.getDeltaTime());
        paint.setColor(Color.rgb(32, 32, 32));
        engine.getCanvas().drawRect(
                pos.x - 0.05f,
                pos.y - 0.05f,
                pos.x + 0.05f,
                pos.y + 0.05f,
                paint);
    }

    protected Vec2f interp(float delta) {
        float ratio = delta / Level.getTickTime();
        float diffX = positions[2] - positions[0];
        float diffY = positions[3] - positions[1];
        float x = diffX + positions[2] * ratio + positions[0] * (1.0f - ratio);
        float y = diffY + positions[3] * ratio + positions[1] * (1.0f - ratio);
        return new Vec2f(x, y);
    }

    public void capture(float x, float y) {
        positions[0] = positions[2];
        positions[1] = positions[3];
        positions[2] = x;
        positions[3] = y;
    }
}
