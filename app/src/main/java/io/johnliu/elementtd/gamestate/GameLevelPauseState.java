package io.johnliu.elementtd.gamestate;

import android.graphics.Color;
import android.view.MotionEvent;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.gui.RectButton;
import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class GameLevelPauseState extends State {

    private Level level;
    private Layout layout;
    private RectButton settingsButton;
    private RectButton quitButton;
    private RectButton restartButton;
    private RectButton closeButton;
    private boolean shouldHide;

    public GameLevelPauseState(StateManager stateManager, Level level) {
        super(stateManager);
        this.level = level;
        shouldHide = false;

        initGui();
    }

    @Override
    public void render(RenderEngine engine) {
        if (!shouldHide) {
            layout.render(engine);
        }
    }

    @Override
    public void onTap(MotionEvent e) {
        layout.onTap(e.getX(), e.getY());
    }

    @Override
    public void onFocus() {
        shouldHide = false;
    }

    @Override
    public void onLoseFocus() {
        shouldHide = true;
    }

    private void initGui() {
        layout = new Layout();

        // simple grid layout
        float left = Game.DISPLAY_WIDTH / 3.0f;
        float right = left * 2.0f;
        float cellHeight = Game.DISPLAY_HEIGHT / 32.0f;

        settingsButton = new RectButton(
                left, cellHeight * 3.0f,
                right, cellHeight * 8.0f
        ) {
            @Override
            public void render(RenderEngine engine) {
                paint.setColor(Color.rgb(128, 128, 128));
                engine.getCanvas().drawRect(posX, posY, right, bottom, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                renderCenterText(engine, "Settings");
            }

            @Override
            public void doAction() {
                stateManager.pushState(new SettingsState(stateManager));
            }
        };

        layout.addWidget(settingsButton);

        quitButton = new RectButton(
                left, cellHeight * 10.0f,
                right, cellHeight * 15.0f
        ) {
            @Override
            public void render(RenderEngine engine) {
                paint.setColor(Color.rgb(128, 128, 128));
                engine.getCanvas().drawRect(posX, posY, right, bottom, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                renderCenterText(engine, "Quit to main menu");
            }

            @Override
            public void doAction() {
                stateManager.clearAndSetState(new MainMenuState(stateManager));
            }
        };

        layout.addWidget(quitButton);

        restartButton = new RectButton(
                left, cellHeight * 17.0f,
                right, cellHeight * 22.0f
        ) {
            @Override
            public void render(RenderEngine engine) {
                paint.setColor(Color.rgb(128, 128, 128));
                engine.getCanvas().drawRect(posX, posY, right, bottom, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                renderCenterText(engine, "Restart round");
            }

            @Override
            public void doAction() {
                level.restartLevel();
                stateManager.popState();
            }
        };

        layout.addWidget(restartButton);

        closeButton = new RectButton(
                left, cellHeight * 24.0f,
                right, cellHeight * 29.0f
        ) {
            @Override
            public void render(RenderEngine engine) {
                paint.setColor(Color.rgb(128, 128, 128));
                engine.getCanvas().drawRect(posX, posY, right, bottom, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                renderCenterText(engine, "Close");
            }

            @Override
            public void doAction() {
                stateManager.popState();
            }
        };

        layout.addWidget(closeButton);
    }

}
