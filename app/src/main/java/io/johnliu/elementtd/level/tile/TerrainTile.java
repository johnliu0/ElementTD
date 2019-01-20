package io.johnliu.elementtd.level.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;

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
    public void update() {

    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(getBitmap(), null, new Rect(x, y, x + 1, y + 1), null);
        if (tower != null) {
            tower.render(canvas, deltaTime);
        }
    }

    private Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = ResourceLoader.decodeResource(R.drawable.grass1);
        }
        return bitmap;
    }

}
