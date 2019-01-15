package io.johnliu.elementtd.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.Game;

public class StateManager {

    private Game game;
    private State currentState;

    public StateManager(Game game) {
        this.game = game;
        this.currentState = new StateGameLevel();
    }

    public void update() {
        currentState.update();
    }

    public void render(Canvas canvas, float deltaTime) {
        currentState.render(canvas, deltaTime);
    }

    public void onTap(MotionEvent e) {
        currentState.onTap(e);
    }

    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        currentState.onScroll(e1, e2, x, y);
    }

    public void onScale(ScaleGestureDetector detector) {
        currentState.onScale(detector);
    }

}
