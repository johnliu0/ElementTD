package io.johnliu.elementtd;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.johnliu.elementtd.gamestate.StateManager;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public static AssetManager ASSETS;
    // ticks/updates per second
    public static final int UPDATE_FPS = 20;
    // how long a tick lasts for in seconds
    // used for kinematics calculations
    public static final float TICK_TIME = 1.0f / UPDATE_FPS;
    // how long a tick lasts for in nanoseconds
    // used for timers
    public static int RENDER_FPS_MAX = 60;

    public static float DISPLAY_DENSITY;
    public static float DISPLAY_WIDTH;
    public static float DISPLAY_HEIGHT;
    public static float DISPLAY_ASPECT_RATIO;
    public static float FONT_SIZE_SM;
    public static float FONT_SIZE_MD;
    public static float FONT_SIZE_LG;


    public static float MASTER_VOLUME = 0.7f;
    public static float MUSIC_VOLUME = 0.3f;
    public static float FX_VOLUME = 0.5f;

    private GameThread thread;
    private RenderEngine renderEngine;
    private StateManager stateManager;

    public Game(Context context, float displayDensity, float displayWidth, float displayHeight, float aspectRatio) {
        super(context);
        this.DISPLAY_DENSITY = displayDensity;
        this.DISPLAY_WIDTH = displayWidth;
        this.DISPLAY_HEIGHT = displayHeight;
        this.DISPLAY_ASPECT_RATIO = aspectRatio;
        this.FONT_SIZE_SM = displayDensity * 12;
        this.FONT_SIZE_MD = displayDensity * 18;
        this.FONT_SIZE_LG = displayDensity * 24;

        ASSETS = context.getAssets();
        ResourceLoader.getInstance().setResources(getResources());

        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);

        stateManager = new StateManager(this);
        renderEngine = new RenderEngine();
    }

    public void update() {
        stateManager.update();
    }

    public void render(Canvas canvas, float deltaTime) {
        renderEngine.setCanvas(canvas);
        renderEngine.setDeltaTime(deltaTime);
        stateManager.render(renderEngine);
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
            long timeSinceLastUpdate = 0;

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
                timeSinceLastUpdate += deltaTime;
                beforeTime = currentTime;

                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {

                        while (elapsedUpdateTime >= updateTime) {
                            game.update();
                            elapsedUpdateTime -= updateTime;
                            timeSinceLastUpdate = 0;
                            updateFps++;
                        }

                        game.draw(canvas);
                        if (canvas != null) {
                            game.render(canvas, ((float) timeSinceLastUpdate) / 1000000000.0f);
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
                if (elapsedTime >= 5000000000L) {
                    elapsedTime -= 5000000000L;
                    //System.out.println("ups: " + (updateFps / 5) + " | fps: " + (renderFps / 5));
                    updateFps = 0;
                    renderFps = 0;
                }
            }
        }

    }

}