package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.level.gui.LevelGui;
import io.johnliu.elementtd.level.tile.Tile;

public class Level {

    public static int gridWidth = 0;
    public static int gridHeight = 0;
    public static float tileWidth = 0;

    private float zoomScale;
    private float zoomScaleMax;
    private float zoomScaleX;
    private float zoomScaleY;
    private float offsetX;
    private float offsetY;

    private LevelGui gui;
    private Tile[][] tiles;

    private Bitmap bitmap;

    public Level(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.tileWidth = Game.DISPLAY_WIDTH / gridWidth + 200;

        zoomScale = 1.0f;
        zoomScaleMax = 3.0f;
        zoomScaleX = Game.DISPLAY_WIDTH / 2.0f;
        zoomScaleY = Game.DISPLAY_HEIGHT / 2.0f;
        offsetX = 0.0f;
        offsetY = 0.0f;

        gui = new LevelGui(this);
        tiles = new Tile[gridWidth][gridHeight];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tiles[x][y] = new Tile(x, y);
            }
        }

        bitmap = ResourceLoader.getInstance().decodeResource(R.drawable.firerock1);
    }

    public void update() {

    }

    public void render(Canvas canvas, long deltaTime) {
        //canvas.drawBitmap(bitmap, null, new Rect(0, 0, 256, 256), null);
        //canvas.scale(zoomScale, zoomScale, zoomScaleX, zoomScaleY);
        canvas.translate(-offsetX, -offsetY);


        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tiles[x][y].render(canvas, deltaTime);
            }
        }
    }

    public void onTap(MotionEvent e) {
        float x = e.getX() + offsetX;
        float y = e.getY() + offsetY;




        // find corresponding tile
        int tileX = (int) (x / tileWidth);
        int tileY = (int) (y / tileWidth);



        gui.selectTile(tileX, tileY);
    }

    public void onScroll(float x, float y) {
        this.offsetX += x / zoomScale;
        this.offsetY += y / zoomScale;

        if (gridWidth * tileWidth >= Game.DISPLAY_WIDTH) {
            if (offsetX > gridWidth * tileWidth - Game.DISPLAY_WIDTH) {
                offsetX = gridWidth * tileWidth - Game.DISPLAY_WIDTH;
            }
            if (offsetX < 0) {
                offsetX = 0;
            }
        } else {
            if (offsetX < gridWidth * tileWidth - Game.DISPLAY_WIDTH) {
                offsetX = gridWidth * tileWidth - Game.DISPLAY_WIDTH;
            }

            if (offsetX > 0) {
                offsetX = 0;
            }
        }

        if (gridHeight * tileWidth > Game.DISPLAY_HEIGHT) {
            if (offsetY > gridHeight * tileWidth - Game.DISPLAY_HEIGHT) {
                offsetY = gridHeight * tileWidth - Game.DISPLAY_HEIGHT;
            }

            if (offsetY < 0) {
                offsetY = 0;
            }
        } else {
            if (offsetY < gridHeight * tileWidth - Game.DISPLAY_HEIGHT) {
                offsetY = gridHeight * tileWidth - Game.DISPLAY_HEIGHT;
            }

            if (offsetY > 0) {
                offsetY = 0;
            }
        }
    }

    public void onScale(ScaleGestureDetector detector) {
        /*zoomScale *= detector.getScaleFactor();
        if (zoomScale < 1.0f) {
            zoomScale = 1.0f;
        } else if (zoomScale > zoomScaleMax) {
            zoomScale = zoomScaleMax;
        }*/
    }

}
