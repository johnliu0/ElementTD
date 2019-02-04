package io.johnliu.elementtd.gamestate;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.MotionEvent;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.gui.RectButton;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class MainMenuState extends State {

    private Bitmap bg;

    private RectButton startButton;

    public MainMenuState(StateManager stateManager) {
        super(stateManager);

        bg = ResourceLoader.decodeResource(R.drawable.mainmenu_bg);
        startButton = new RectButton(
                Game.DISPLAY_WIDTH / 5.0f, Game.DISPLAY_HEIGHT / 6.0f * 4.0f,
                Game.DISPLAY_WIDTH / 5.0f * 4.0f, Game.DISPLAY_HEIGHT / 6.0f * 5.0f
        ) {
            @Override
            public void render(RenderEngine engine) {
                paint.setColor(Color.rgb(128, 128, 128));
                engine.getCanvas().drawRect(posX, posY, right, bottom, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                renderCenterText(engine, "START");
            }

            @Override
            public void doAction() {
                MainMenuState.this.stateManager.clearAndSetState(
                        new GameLevelState(MainMenuState.this.stateManager));
            }
        };
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(bg, null, new RectF(
                0, 0, Game.DISPLAY_WIDTH, Game.DISPLAY_HEIGHT), null);
        startButton.render(engine);
    }

    @Override
    public void onTap(MotionEvent e) {
        startButton.onTap(e.getX(), e.getY());
    }

}
