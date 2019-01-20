package io.johnliu.elementtd.level.gui;

import android.graphics.Canvas;

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
    }

    // handle gui interaction: pause, play, start next wave
    // returns whether or not the input did anything
    public boolean onPress(float x, float y) {
        return false;
    }

}
