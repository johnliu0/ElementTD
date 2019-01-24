package io.johnliu.elementtd.settings;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.gamestate.StateManager;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.gui.RectButton;

public class Settings extends Layout {

    private StateManager stateManager;
    private Paint paint;

    // closes the setting menu
    private RectButton closeButton;

    public Settings(StateManager stateManager) {
        this.stateManager = stateManager;
        initGui();
    }

    public void render(Canvas canvas, float deltaTime) {
        super.render(canvas, deltaTime);
    }

    public void initGui() {
        closeButton = new RectButton(
                Game.DISPLAY_WIDTH / 5.0f * 1.0f,
                Game.DISPLAY_HEIGHT / 6.0f * 4.0f,
                Game.DISPLAY_WIDTH / 5.0f * 4.0f,
                Game.DISPLAY_HEIGHT / 6.0f * 5.0f
        ) {
            @Override
            public void render(Canvas canvas, float deltaTime) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.rgb(128, 128, 128));
                canvas.drawRect(posX, posY, right, bottom, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1.0f);
                paint.setColor(Color.rgb(0, 0, 0));
                canvas.drawRect(posX, posY, right, bottom, paint);
                renderCenterText(canvas, "Close");
            }

            @Override
            public void doAction() {
                stateManager.popState();
            }
        };

        closeButton.setTextSize(Game.FONT_SIZE_SM);
        addWidget(closeButton);
    }

}
