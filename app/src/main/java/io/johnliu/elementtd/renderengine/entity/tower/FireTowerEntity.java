package io.johnliu.elementtd.renderengine.entity.tower;

import android.graphics.Bitmap;
import android.graphics.RectF;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class FireTowerEntity extends TowerEntity {

    private static Bitmap bitmap = null;
    private RectF bitmapRect;

    public FireTowerEntity(int x, int y) {
        super(x, y);

        bitmapRect = new RectF(x, y, x + 1, y + 1);

        if (bitmap == null) {
            bitmap = ResourceLoader.decodeResource(R.drawable.fire_slab_1);
        }
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(bitmap, null, bitmapRect, null);
    }

}
