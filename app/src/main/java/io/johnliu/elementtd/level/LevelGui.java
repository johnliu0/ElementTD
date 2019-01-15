package io.johnliu.elementtd.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.Level;

public class LevelGui {

    private Level level;

    public LevelGui(Level level) {
        this.level = level;
    }

    public void update() {

    }

    public void render(Canvas canvas, long deltaTime) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(128, 255, 50, 50));
//
//        float max
//
//        canvas.drawRect(
//                Game.DISPLAY_WIDTH / 6
//                paint);
    }

    public void selectTile() {

    }
}
