package io.johnliu.elementtd.level.tile;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.Random;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class PathTile extends Tile {

    // collection of different textured tiles
    private static Bitmap[] tileBitmaps;
    private int bitmapIdx;

    /**
     * Tile for mob pathing.
     * Towers can't be built on this tile.
     */
    public PathTile(int x, int y) {
        super(x, y, true);

        if (tileBitmaps == null) {
            Bitmap orig = ResourceLoader.decodeResource(R.drawable.stone_tile_1);
            tileBitmaps = new Bitmap[4];
            tileBitmaps[0] = orig;

            Matrix matrix = new Matrix();
            matrix.postRotate(90.0f);
            tileBitmaps[1] = Bitmap.createBitmap(orig, 0, 0, orig.getWidth(), orig.getHeight(), matrix, true);

            matrix.postRotate(90.0f);
            tileBitmaps[2] = Bitmap.createBitmap(orig, 0, 0, orig.getWidth(), orig.getHeight(), matrix, true);

            matrix.postRotate(90.0f);
            tileBitmaps[3] = Bitmap.createBitmap(orig, 0, 0, orig.getWidth(), orig.getHeight(), matrix, true);
        }

        bitmapIdx = new Random().nextInt(tileBitmaps.length);
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(tileBitmaps[bitmapIdx], null, new Rect(x, y, x + 1, y + 1), null);
    }

}
