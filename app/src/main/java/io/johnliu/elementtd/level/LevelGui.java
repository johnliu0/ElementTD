package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.gui.RectButton;

public class LevelGui extends Layout {

    private Level level;

    private RectButton fastForwardButton;
    private RectButton pauseButton;

    public LevelGui(Level level) {
        this.level = level;
        initGui();
    }

    public void initGui() {
        fastForwardButton = new RectButton(
                Game.DISPLAY_WIDTH / 16.0f * 14.0f,
                0,
                Game.DISPLAY_WIDTH / 16.0f * 15.0f,
                Game.DISPLAY_WIDTH / 16.0f
        ) {
            private Bitmap iconOff;
            private Bitmap iconOn;

            @Override
            public void init() {
                iconOff = ResourceLoader.decodeResource(R.drawable.fast_forward_off_btn);
                iconOn = ResourceLoader.decodeResource(R.drawable.fast_forward_on_btn);
            }

            @Override
            public void render(Canvas canvas, float deltaTime) {
                if (level.isFastForward()) {
                    canvas.drawBitmap(iconOn, null, new RectF(posX, posY, right, bottom), paint);
                } else {
                    canvas.drawBitmap(iconOff, null, new RectF(posX, posY, right, bottom), paint);
                }
            }

            @Override
            public void doAction() {
                level.toggleFastForward();
            }
        };

        fastForwardButton.init();
        addWidget(fastForwardButton);

        pauseButton = new RectButton(
                Game.DISPLAY_WIDTH / 16.0f * 15.0f,
                0,
                Game.DISPLAY_WIDTH,
                Game.DISPLAY_WIDTH / 16.0f
        ) {
            private Bitmap icon;

            @Override
            public void init() {
                icon = ResourceLoader.decodeResource(R.drawable.pause_btn);
            }

            @Override
            public void render(Canvas canvas, float deltaTime) {
                canvas.drawBitmap(icon, null, new RectF(posX, posY, right, bottom), paint);
            }

            @Override
            public void doAction() {
                level.pauseGame();
            }
        };

        pauseButton.init();
        addWidget(pauseButton);
    }

}
