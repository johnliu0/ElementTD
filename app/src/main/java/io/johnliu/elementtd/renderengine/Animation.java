package io.johnliu.elementtd.renderengine;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public class Animation {

    private Bitmap bitmap;
    // width and height of one animation frame in pixels
    private int frameWidth;
    private int frameHeight;
    private int numFrames;

    // how long the entire animation lasts in seconds
    private float animLength;
    private float animTimer;

    public Animation(Bitmap bitmap, int numFrames, float animLength) {
        this.bitmap = bitmap;
        this.frameWidth = bitmap.getWidth() / numFrames;
        this.frameHeight = bitmap.getHeight();
        this.numFrames = numFrames;
        this.animLength = animLength;

        animTimer = 0.0f;
    }

    public void render(RenderEngine engine, RectF pos) {
        animTimer += engine.getDeltaTimeRender();

        if (animTimer > animLength) {
            animTimer -= animLength;
        }

        // get the frame that should be displayed
        int idx = (int) ((animTimer / animLength) * numFrames);

        engine.getCanvas().drawBitmap(bitmap, new Rect(
                idx * frameWidth, 0, (idx + 1) * frameWidth, frameHeight
        ), pos, null);
    }

}
