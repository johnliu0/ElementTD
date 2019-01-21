package io.johnliu.elementtd.level.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.level.Level;

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
    public void update(Level level) {

    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        canvas.drawBitmap(getBitmap(), null, new Rect(x, y, x + 1, y + 1), null);
    }

    private Bitmap getBitmap() {
        if (bitmap == null) {
            bitmap = ResourceLoader.decodeResource(R.drawable.rock1);
        }
        return bitmap;
    }

}
