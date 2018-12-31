package io.johnliu.elementtd.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.level.Level;

public class StateGameLevel extends State {

    private Level level;

    public StateGameLevel() {
        this.level = new Level(8, 8);
    }

    @Override
    public void update() {
        level.update();
    }

    @Override
    public void render(Canvas canvas, long deltaTime) {
        level.render(canvas, deltaTime);
    }

    @Override
    public void onTap(MotionEvent e) {
        level.onTap(e);
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        level.onScroll(x, y);
    }

    @Override
    public void onScale(ScaleGestureDetector detector) {
        level.onScale(detector);
    }

}
