package io.johnliu.elementtd.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.settings.Settings;

public class SettingsState extends State {

    private Settings settings;

    public SettingsState(StateManager stateManager) {
        super(stateManager);

        settings = new Settings(stateManager);
    }

    @Override
    public void update() {
        //this.settings.update();
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        this.settings.render(canvas, deltaTime);
    }

    @Override
    public void onTap(MotionEvent e) {
        this.settings.onTap(e.getX(), e.getY());
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        this.settings.onScroll(e1.getX(), e1.getY(), x, y);
    }

    @Override
    public void onScale(ScaleGestureDetector detector) {
        this.settings.onScale(detector.getFocusX(), detector.getFocusY(), detector.getScaleFactor());
    }

}
