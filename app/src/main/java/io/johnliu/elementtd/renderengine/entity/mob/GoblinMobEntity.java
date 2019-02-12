package io.johnliu.elementtd.renderengine.entity.mob;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.Animation;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class GoblinMobEntity extends MobEntity {

    private static Bitmap bitmapAnim;

    private Animation animation;

    public GoblinMobEntity(float x, float y, float health, float radius) {
        super(x, y, health, radius);

        if (bitmapAnim == null) {
            bitmapAnim = ResourceLoader.decodeResource(R.drawable.mob_goblin);
        }

        animation = new Animation(bitmapAnim, 8, 1.0f);
    }

    @Override
    public void render(RenderEngine engine) {
        super.render(engine);
        animation.setRotation(angle);
        animation.render(engine, new RectF(
                interpPos.x - radius, interpPos.y - radius,
                interpPos.x + radius, interpPos.y + radius
        ), true);
    }

}
