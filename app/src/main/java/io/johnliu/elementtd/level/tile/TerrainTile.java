package io.johnliu.elementtd.level.tile;

import android.graphics.Bitmap;
import android.graphics.Rect;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class TerrainTile extends Tile {

    private static Bitmap bitmap;

    /**
     * Tile that mobs can't walk on.
     * Towers can be built on this tile.
     */
    public TerrainTile(int x, int y) {
        super(x, y, false);
    }

    @Override
    public void update(Level level) {
        if (tower != null) {
            tower.update(level);
        }
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(getBitmap(), null, new Rect(x, y, x + 1, y + 1), null);
        if (tower != null) {
            tower.render(engine);
        }
    }

    private Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = ResourceLoader.decodeResource(R.drawable.grass1);
        }
        return bitmap;
    }

}
