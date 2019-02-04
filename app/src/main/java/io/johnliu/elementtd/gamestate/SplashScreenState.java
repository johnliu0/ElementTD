package io.johnliu.elementtd.gamestate;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class SplashScreenState extends State {

    private float splashTimer;

    public SplashScreenState(StateManager stateManager) {
        super(stateManager);
    }

    @Override
    public void update() {
        splashTimer += Game.TICK_TIME;

        if (splashTimer >= 2.0f) {
            stateManager.clearAndSetState(new MainMenuState(stateManager));
        }
    }

    @Override
    public void render(RenderEngine engine) {

    }

}
