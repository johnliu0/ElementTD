package io.johnliu.elementtd.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.Stack;

import io.johnliu.elementtd.Game;

public class StateManager {

    private Game game;
    private Stack<State> stateStack;

    public StateManager(Game game) {
        this.game = game;
        stateStack = new Stack<State>();
        stateStack.push(new GameLevelState(this));
    }

    public void update() {
        if (!stateStack.empty()) {
            stateStack.peek().update();
        }
    }

    public void render(Canvas canvas, float deltaTime) {
        for (State state : stateStack) {
            state.render(canvas, deltaTime);
        }
    }

    public State popState() {
        if (!stateStack.empty()) {
            return stateStack.pop();
        }
        return null;
    }

    public void pushState(State state) {
        this.stateStack.push(state);
    }

    public void onTap(MotionEvent e) {
        if (!stateStack.empty()) {
            stateStack.peek().onTap(e);
        }
    }

    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        if (!stateStack.empty()) {
            stateStack.peek().onScroll(e1, e2, x, y);
        }
    }

    public void onScale(ScaleGestureDetector detector) {
        if (!stateStack.empty()) {
            stateStack.peek().onScale(detector);
        }
    }

}
