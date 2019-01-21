package io.johnliu.elementtd.level.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.tower.BasicTower;

public class LevelGui {

    private Level level;
    private Paint paint;

    TowerInfoBox test;

    public LevelGui(Level level) {
        this.level = level;
        paint = new Paint();
        test = new TowerInfoBox(new BasicTower(0, 0));
    }

    public void update() {

    }

    public void render(Canvas canvas, float deltaTime) {
        test.render(canvas, deltaTime);


        // mana
        paint.setColor(Color.rgb(105, 105, 105));
        paint.setTextSize(Game.FONT_SIZE_SM);
        canvas.drawRect(Game.DISPLAY_WIDTH / 3.0f, 0.0f, Game.DISPLAY_WIDTH / 3.0f + paint.measureText("#######"), Game.FONT_SIZE_SM * 1.5f, paint);
        paint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText(String.valueOf((int) level.getMana()), Game.DISPLAY_WIDTH / 3.0f + Game.FONT_SIZE_SM / 2.0f, Game.FONT_SIZE_SM, paint);

    }

    // handle gui interaction: pause, play, start next wave
    // returns whether or not the input did anything
    public boolean onPress(float x, float y) {
        return false;
    }

}
