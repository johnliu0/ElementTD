package io.johnliu.elementtd.renderengine;

import android.graphics.Canvas;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.renderengine.entity.MobEntity;

public class RenderEngine {

    private Canvas canvas;
    // time since last update
    // used for interpolation and movement
    private float deltaTime;
    // locking the deltaTime allows for pausing of the game
    // animations can still continue however by using
    // deltaTimeRender
    private float prevDeltaTime;
    private boolean lockDeltaTime;
    // time since last rendering frame
    // used for timing animations
    private float deltaTimeRender;

    public void RenderEngine() {
        canvas = null;
        deltaTime = 0.0f;
        prevDeltaTime = 0.0f;
        lockDeltaTime = false;
        deltaTimeRender = 0.0f;
    }

    public void translate(float x, float y) {
        canvas.translate(x, y);
    }

    public void scale(float scaleX, float scaleY, float x, float y) {
        canvas.scale(scaleX, scaleY, x, y);
    }

    // removes any transformations applied
    public void clearTransform() {
        canvas.setMatrix(null);
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setDeltaTime(float deltaTime) {
        float diff = deltaTime - prevDeltaTime;
        // if the difference is negative then an update
        // had been called since the last render frame
        if (diff < 0.0f) {
            deltaTimeRender = Level.getTickTime() + diff;
        } else {
            deltaTimeRender = diff;
        }

        if (!lockDeltaTime) {
            prevDeltaTime = this.deltaTime;
        }

        this.deltaTime = deltaTime;
    }

    // return the number of seconds elapsed since the last update frame
    // this variable will not change when deltaTime is locked
    // this variable should be used for anything dependent on game logic e.g. movement of mobs
    public float getDeltaTime() {
        if (lockDeltaTime) {
            return prevDeltaTime;
        }

        return deltaTime;
    }

    // returns the number of seconds elapsed since the last render frame
    // this variable will continue to change even when the deltaTime is locked
    // this variable should be used for anything that is not dependent on game logic e.g. animations
    public float getDeltaTimeRender() {
        return deltaTimeRender;
    }

    public void lockDeltaTime() {
        lockDeltaTime = true;
    }

    public void unlockDeltaTime() {
        lockDeltaTime = false;
    }

}
