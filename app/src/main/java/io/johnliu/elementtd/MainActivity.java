package io.johnliu.elementtd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    private Game game;
    private GestureDetectorCompat mDetector;
    private ScaleGestureDetector mScaleDetector;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        game.onScroll(e1, e2, x, y);
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        game.onTap(e);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float x, float y) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDetector = new GestureDetectorCompat(this, this);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        game = new Game(this, metrics.density, (float) metrics.widthPixels, (float) metrics.heightPixels);
        setContentView(game);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getPointerCount() == 1) {
            if (mDetector.onTouchEvent(e)) {
                return true;
            }
        } else if (e.getPointerCount() == 2) {
            if (mScaleDetector.onTouchEvent(e)) {
                return true;
            }
        }

        return super.onTouchEvent(e);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            game.onScale(detector);
            return true;
        }
    }
}
