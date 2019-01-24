package io.johnliu.elementtd.gamestate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.LevelLoader;

public class GameLevelState extends State {

    private Level level;

    public GameLevelState(StateManager stateManager) {
        super(stateManager);
        try {
            this.level = LevelLoader.loadLevel("level1.txt", stateManager);
        } catch (Exception e) {
            System.out.println("Failed to load level1.txt");
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void update() {
        level.update();
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        level.render(canvas, deltaTime);
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
