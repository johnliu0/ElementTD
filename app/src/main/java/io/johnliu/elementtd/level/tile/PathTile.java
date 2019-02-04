package io.johnliu.elementtd.level.tile;

import android.graphics.Bitmap;
import android.graphics.Rect;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class PathTile extends Tile {

    private static Bitmap bitmap = null;

    /**
     * Tile for mob pathing.
     * Towers can't be built on this tile.
     */
    public PathTile(int x, int y) {
        super(x, y, true);
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(getBitmap(), null, new Rect(x, y, x + 1, y + 1), null);
    }

    private Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = ResourceLoader.decodeResource(R.drawable.rock1);
        }
        return bitmap;
    }

}
