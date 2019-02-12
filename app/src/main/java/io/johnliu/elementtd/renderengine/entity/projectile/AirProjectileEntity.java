package io.johnliu.elementtd.renderengine.entity.projectile;

import android.graphics.Bitmap;
import android.graphics.RectF;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class AirProjectileEntity extends ProjectileEntity {

    private Bitmap bitmap;
    private float radius;

    public AirProjectileEntity(float x, float y, float targetX, float targetY) {
        super(x, y, targetX, targetY);

        if (bitmap == null) {
            bitmap = ResourceLoader.decodeResource(R.drawable.projectile_air);
        }

        radius = 0.15f;
    }

    @Override
    public void render(RenderEngine engine) {
        Vec2f pos = interp(engine.getDeltaTime());
        engine.getCanvas().drawBitmap(bitmap, null, new RectF(
                pos.x - radius,
                pos.y - radius,
                pos.x + radius,
                pos.y + radius), null);
    }

}
