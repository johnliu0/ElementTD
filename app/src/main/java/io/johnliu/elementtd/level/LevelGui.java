package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.gui.RectButton;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class LevelGui extends Layout {

    private Level level;
    private Paint paint;

    private RectButton fastForwardButton;
    private RectButton pauseButton;

    private Bitmap livesLeftIcon;
    private Bitmap manaIcon;

    public LevelGui(Level level) {
        this.level = level;
        initGui();
    }

    @Override
    public void render(RenderEngine engine) {
        super.render(engine);

        paint.setTextSize(Game.FONT_SIZE_MD);
        float height = (paint.getFontMetrics().descent - paint.getFontMetrics().ascent);
        float padding = height / 7.0f;
        height += 2 * padding;
        float left = Game.DISPLAY_WIDTH / 6.0f;

        Canvas canvas = engine.getCanvas();
        // lives left and mana
        // background
        paint.setColor(Color.rgb(96, 96, 96));
        canvas.drawRect(left, 0.0f, left + 7.0f * height, height, paint);
        // icon
        canvas.drawBitmap(livesLeftIcon, null, new RectF(
                left + padding, padding, left + height - padding, height - padding), paint);
        canvas.drawBitmap(manaIcon, null, new RectF(
                left + 3.0f * height + padding, padding, left + 4.0f * height - padding, height - padding), paint);
        // value
        paint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText(Integer.toString(level.getLivesLeft()), left + 1.5f * height, (height / 4.0f) * 3.0f, paint);
        canvas.drawText(Integer.toString(level.getMana()), left + 4.5f * height, (height / 4.0f) * 3.0f, paint);
    }

    public void initGui() {
        paint = new Paint();
        paint.setTypeface(ResourceLoader.getDefaultFont());

        livesLeftIcon = ResourceLoader.decodeResource(R.drawable.heart_icon);
        manaIcon = ResourceLoader.decodeResource(R.drawable.mana_icon);

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
            public void render(RenderEngine engine) {
                if (level.isFastForward()) {
                    engine.getCanvas().drawBitmap(iconOn, null, new RectF(posX, posY, right, bottom), paint);
                } else {
                    engine.getCanvas().drawBitmap(iconOff, null, new RectF(posX, posY, right, bottom), paint);
                }
            }

            @Override
            public void doAction() {
                level.toggleFastForward();
            }
        };

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
            public void render(RenderEngine engine) {
                engine.getCanvas().drawBitmap(icon, null, new RectF(posX, posY, right, bottom), paint);
            }

            @Override
            public void doAction() {
                level.pauseGame();
            }
        };

        addWidget(pauseButton);
    }

}
