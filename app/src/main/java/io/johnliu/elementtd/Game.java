package io.johnliu.elementtd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.johnliu.elementtd.gamestate.StateManager;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public static final int UPDATE_FPS = 5;
    public static int RENDER_FPS_MAX = 60;

    public static float DISPLAY_DENSITY;
    public static float DISPLAY_WIDTH;
    public static float DISPLAY_HEIGHT;

    private GameThread thread;
    private StateManager stateManager;

    private Bitmap bitmap;

    public Game(Context context, float displayDensity, float displayWidth, float displayHeight) {
        super(context);
        this.DISPLAY_DENSITY = displayDensity;
        this.DISPLAY_WIDTH = displayWidth;
        this.DISPLAY_HEIGHT = displayHeight;

        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);

        stateManager = new StateManager(this);

        ResourceLoader.getInstance().setResources(getResources());
        bitmap = ResourceLoader.getInstance().decodeResource(R.drawable.firerock1);
    }

    public void update() {
        stateManager.update();
    }

    public void render(Canvas canvas, long deltaTime) {
        //canvas.drawBitmap(bitmap, null, new Rect(0, 0, 256, 256), null);
        stateManager.render(canvas, deltaTime);
    }

    public void onTap(MotionEvent e) {
        stateManager.onTap(e);
    }

    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        stateManager.onScroll(e1, e2, x, y);
    }

    public void onScale(ScaleGestureDetector detector) {
        stateManager.onScale(detector);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    class GameThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private Game game;
        private boolean isRunning;
        private Canvas canvas;

        public GameThread(SurfaceHolder surfaceHolder, Game game) {
            super();
            this.surfaceHolder = surfaceHolder;
            this.game = game;
            this.isRunning = false;
            this.canvas = null;
        }

        public void setRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        @Override
        public void run() {
            long beforeTime = System.nanoTime();
            long deltaTime = 0;
            long elapsedUpdateTime = 0;
            long elapsedRenderTime = 0;

            final long updateTime = 1000000000 / Game.UPDATE_FPS;
            final long maxRenderTime = 1000000000 / Game.RENDER_FPS_MAX;

            int updateFps = 0;
            int renderFps = 0;

            long elapsedTime = 0;

            while (isRunning) {
                canvas = null;
                long currentTime = System.nanoTime();
                deltaTime = currentTime - beforeTime;
                elapsedRenderTime += deltaTime;
                elapsedUpdateTime += deltaTime;
                beforeTime = currentTime;

                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {

                        while (elapsedUpdateTime >= updateTime) {
                            game.update();
                            elapsedUpdateTime -= updateTime;
                            updateFps++;
                        }

                        game.draw(canvas);
                        if (canvas != null) {
                            game.render(canvas, deltaTime);
                        }
                        renderFps++;


                    }
                } catch (Exception e) {} finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                elapsedTime += deltaTime;
                if (elapsedTime >= 3000000000L) {
                    elapsedTime -= 3000000000L;
                    System.out.println("ups: " + (updateFps / 3) + " | fps: " + (renderFps / 3));
                    updateFps = 0;
                    renderFps = 0;
                }
            }
        }

    }

}