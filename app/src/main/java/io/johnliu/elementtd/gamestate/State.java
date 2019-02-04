package io.johnliu.elementtd.gamestate;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class State {

    protected StateManager stateManager;

    public State(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void update() {}
    public void render(RenderEngine engine) {}

    // method is called when this state is back on top of the state manager stack
    public void onFocus() {};
    // method is called when another state is pushed above this one on the state manager stack
    public void onLoseFocus() {};

    public void onTap(MotionEvent e) {}
    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {}
    public void onScale(ScaleGestureDetector detector) {}

}
