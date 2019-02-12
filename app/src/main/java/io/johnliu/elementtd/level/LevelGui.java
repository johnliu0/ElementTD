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

    private Bitmap heartIcon;
    private Bitmap manaIcon;
    private Bitmap heartManaBg;

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
        height += 2.0f * padding;
        float left = Game.DISPLAY_WIDTH / 6.0f;

        Canvas canvas = engine.getCanvas();
        // background for lives left and mana
        canvas.drawBitmap(heartManaBg, null, new RectF(left, 0.0f, left + 6.0f * height, height), paint);
        // icon
        canvas.drawBitmap(heartIcon, null, new RectF(
                left + padding, padding, left + height - padding, height - padding), paint);
        canvas.drawBitmap(manaIcon, null, new RectF(
                left + 3.0f * height + padding, padding, left + 4.0f * height - padding, height - padding), paint);

        // value
        paint.setColor(Color.rgb(255, 255, 255));
        canvas.drawText(Integer.toString(level.getLivesLeft()), left + 1.15f * height, (height / 4.0f) * 3.0f, paint);
        canvas.drawText(Integer.toString(level.getMana()), left + 4.15f * height, (height / 4.0f) * 3.0f, paint);
    }

    public void initGui() {
        paint = new Paint();
        paint.setTypeface(ResourceLoader.getDefaultFont());

        heartIcon = ResourceLoader.decodeResource(R.drawable.icon_heart);
        manaIcon = ResourceLoader.decodeResource(R.drawable.icon_mana);
        heartManaBg = ResourceLoader.decodeResource(R.drawable.bg_heart_mana);

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
                iconOff = ResourceLoader.decodeResource(R.drawable.icon_fastforward_off);
                iconOn = ResourceLoader.decodeResource(R.drawable.icon_fastforward_on);
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
                icon = ResourceLoader.decodeResource(R.drawable.icon_pause);
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
