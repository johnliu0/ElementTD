package io.johnliu.elementtd.gamestate;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.LevelLoader;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class GameLevelState extends State {

    private Level level;

    public GameLevelState(StateManager stateManager) {
        super(stateManager);
        try {
            this.level = new LevelLoader().loadLevel("level2.txt", stateManager);
        } catch (Exception e) {
            System.err.println("Failed to load level1.txt");
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void update() {
        level.update();
    }

    @Override
    public void render(RenderEngine engine) {
        level.render(engine);
    }

    @Override
    public void onFocus() {
        level.resumeGame();
    }

    @Override
    public void onTap(MotionEvent e) {
        level.onTap(e);
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        level.onScroll(e1, e2, x, y);
    }

    @Override
    public void onScale(ScaleGestureDetector detector) {
        level.onScale(detector);
    }

}
