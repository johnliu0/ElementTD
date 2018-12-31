package io.johnliu.elementtd.level.tile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

import io.johnliu.elementtd.level.Level;

public class Tile {

    private int x;
    private int y;

    private int r;
    private int g;
    private int b;


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;

        Random rand = new Random();
        this.r = rand.nextInt(50);
        this.g = rand.nextInt(50);
        this.b = rand.nextInt(50);
    }

    public void update() {

    }

    public void render(Canvas canvas, long deltaTime) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(128, r, g, b));
        canvas.drawRect(
                x * Level.tileWidth,
                y * Level.tileWidth,
                (x + 1) * Level.tileWidth,
                (y + 1) * Level.tileWidth,
                paint);
    }

    public void whiten() {
        r += 20;
        g += 20;
        b += 20;
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }
    }

}
