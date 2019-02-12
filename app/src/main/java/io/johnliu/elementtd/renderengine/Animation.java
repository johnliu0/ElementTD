package io.johnliu.elementtd.renderengine;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public class Animation {

    private Bitmap bitmapSrc;
    // width and height of one animation frame in pixels
    private int frameWidth;
    private int frameHeight;
    private int numFrames;

    // how long the entire animation lasts in seconds
    private float animLength;
    private float animTimer;

    private Matrix transform;

    public Animation(Bitmap bitmap, int numFrames, float animLength) {
        this.bitmapSrc = bitmap;
        this.frameWidth = bitmap.getWidth() / numFrames;
        this.frameHeight = bitmap.getHeight();
        this.numFrames = numFrames;
        this.animLength = animLength;

        animTimer = 0.0f;

        transform = new Matrix();
    }

    // specify whether the animation should keep animating if the
    // render engine is locked (happens for example when the pause screen is brought up)
    public void render(RenderEngine engine, RectF pos, boolean freezeWhenLocked) {
        if ((freezeWhenLocked && !engine.isDeltaTimeLocked()) || !freezeWhenLocked) {
            animTimer += engine.getDeltaTimeRender();
        }

        while (animTimer > animLength) {
            animTimer -= animLength;
        }

        // get the frame that should be displayed
        int idx = (int) ((animTimer / animLength) * numFrames);

        Bitmap frame = Bitmap.createBitmap(bitmapSrc, idx * frameWidth, 0, frameWidth, frameHeight);
        Bitmap transformed = Bitmap.createBitmap(frame, 0, 0, frame.getWidth(), frame.getHeight(), transform, true);

        engine.getCanvas().drawBitmap(transformed, null, pos, null);
    }

    // in degrees counter-clockwise from the positive x-axis (right)
    public void setRotation(float deg) {
        transform.setRotate(deg);
    }

}
