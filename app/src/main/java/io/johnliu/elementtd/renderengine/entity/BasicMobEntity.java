package io.johnliu.elementtd.renderengine.entity;

import android.graphics.Canvas;
import android.graphics.Color;

import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class BasicMobEntity extends MobEntity {

    public BasicMobEntity(float x, float y, float health, float radius) {
        super(x, y, health, radius);
    }

    @Override
    public void render(RenderEngine engine) {
        super.render(engine);
        float len = radius;
        paint.setColor(new Color().rgb(128, 128, 0));
        engine.getCanvas().drawRect(
                interpPos.x - len, interpPos.y - len,
                interpPos.x + len, interpPos.y + len, paint);
    }

}
