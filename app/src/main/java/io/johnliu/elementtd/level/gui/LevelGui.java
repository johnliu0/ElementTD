package io.johnliu.elementtd.level.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.tower.BasicTower;

public class LevelGui {

    private Level level;


    TowerInfoBox test;


    public LevelGui(Level level) {
        this.level = level;
        test = new TowerInfoBox(new BasicTower(0, 0));
    }

    public void update() {

    }

    public void render(Canvas canvas, float deltaTime) {
        test.render(canvas, deltaTime);

        //canvas.drawBitmap(damageIcon, null, new Rect(0, 0, 256, 256), null);





//        float max
//
//        canvas.drawRect(
//                Game.DISPLAY_WIDTH / 6
//                paint);
    }

    public void selectTile() {

    }



}
