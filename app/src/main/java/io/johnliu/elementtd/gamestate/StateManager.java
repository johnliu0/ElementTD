package io.johnliu.elementtd.gamestate;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.Stack;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class StateManager {

    private Game game;
    private Stack<State> stateStack;

    public StateManager(Game game) {
        this.game = game;
        stateStack = new Stack<State>();
        stateStack.push(new MainMenuState(this));
        //stateStack.push(new GameLevelState(this));
    }

    public void update() {
        if (!stateStack.empty()) {
            stateStack.peek().update();
        }
    }

    public void render(RenderEngine engine) {
        for (State state : stateStack) {
            state.render(engine);
        }
    }

    public State popState() {
        if (!stateStack.empty()) {
            State state = stateStack.pop();
            if (!stateStack.empty()) {
                stateStack.peek().onFocus();
            }

            return state;
        }
        return null;
    }

    public void pushState(State state) {
        if (!stateStack.empty()) {
            stateStack.peek().onLoseFocus();
        }

        this.stateStack.push(state);
    }

    public void clearAndSetState(State state) {
        if (!stateStack.empty()) {
            stateStack.peek().onLoseFocus();
        }

        while (!stateStack.empty()) {
            stateStack.pop();
        }

        stateStack.push(state);
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
