package io.johnliu.elementtd.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public abstract class State {

    protected StateManager stateManager;

    public State(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public abstract void update();
    public abstract void render(Canvas canvas, float deltaTime);

    public void onTap(MotionEvent e) {}
    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {}
    public void onScale(ScaleGestureDetector detector) {}

}
