package io.johnliu.elementtd.settings;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.gamestate.StateManager;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.gui.RectButton;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class Settings extends Layout {

    private StateManager stateManager;
    private Paint paint;

    // closes the setting menu
    private RectButton closeButton;

    public Settings(StateManager stateManager) {
        this.stateManager = stateManager;
        paint = new Paint();
        initGui();
    }

    public void initGui() {
        closeButton = new RectButton(
                Game.DISPLAY_WIDTH / 5.0f * 1.0f,
                Game.DISPLAY_HEIGHT / 6.0f * 4.0f,
                Game.DISPLAY_WIDTH / 5.0f * 4.0f,
                Game.DISPLAY_HEIGHT / 6.0f * 5.0f
        ) {
            @Override
            public void render(RenderEngine engine) {
                Canvas canvas = engine.getCanvas();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.rgb(128, 128, 128));
                canvas.drawRect(posX, posY, right, bottom, paint);
                paint.setColor(Color.rgb(255, 255, 255));
                renderCenterText(engine, "CLOSE");
            }

            @Override
            public void doAction() {
                stateManager.popState();
            }
        };

        addWidget(closeButton);
    }

}
