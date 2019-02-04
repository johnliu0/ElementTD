package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.ArrayList;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.gamestate.GameLevelPauseState;
import io.johnliu.elementtd.gamestate.StateManager;
import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.mob.MobPathFinder;
import io.johnliu.elementtd.level.mob.wave.WaveManager;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.level.tile.Tile;
import io.johnliu.elementtd.level.tower.AirTower;
import io.johnliu.elementtd.level.tower.Tower;
import io.johnliu.elementtd.math.Vec2i;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class Level {

    private static float TICK_TIME;

    private StateManager stateManager;
    private RenderEngine engine;

    /**
     * Map specification
     */
    // grid dimensions
    public static int gridWidth;
    public static int gridHeight;
    // width of a tile in pixels
    public static float tileWidth;
    private Tile[][] tiles;
    // list of all possible start locations for mobs
    private ArrayList<Vec2i> startPoints;
    // end destination for all mobs
    private Vec2i endPoint;

    /**
     * Rendering
     */
    private LevelGui gui;
    private TileInterface tileInterface;
    private String backgroundType;
    private Bitmap backgroundBitmap;
    // zoom
    private float zoomScale = 1.0f;
    private float zoomScaleMin = 0.5f;
    private float zoomScaleMax = 2.0f;
    // pan
    private float offsetX;
    private float offsetY;

    /**
     *  Game variables
     */
    private WaveManager waveManager;
    private boolean paused;
    private float pausedDelta;

    private int numLives;
    // currency
    private int mana;
    // list of all active mobs
    private ArrayList<Mob> mobs;
    // list of all projectile entities
    private ArrayList<Projectile> projectiles;
    // list of all towers
    private ArrayList<Tower> towers;
    private boolean fastForward;
    // currently selected tile
    //  private Point2di

    /**
     * The level object used to represent one distinct level.
     * A level can be loaded using the LevelLoader.
     */
    public Level(
            StateManager stateManager,
            Tile[][] tiles, // initial tile setup
            ArrayList<Vec2i> startPoints, // start points for mobs
            Vec2i endPoint, // end point for mobs
            int startLives, // number of lives to begin level with
            int startMana, // amount of mana to begin level with
            WaveManager waveManager,
            String backgroundType
    ) {
        Level.TICK_TIME = Game.TICK_TIME;
        this.stateManager = stateManager;
        this.gridWidth = tiles.length;
        this.gridHeight = tiles[0].length;
        // by default the tileWidth will be such that the
        // width of the map matches the width of the screen
        this.tileWidth = Game.DISPLAY_WIDTH / gridWidth;
        this.tiles = tiles;
        this.startPoints = startPoints;
        this.endPoint = endPoint;
        this.waveManager = waveManager;

        loadBackground(backgroundType);

        gui = new LevelGui(this);
        tileInterface = new TileInterface(this);
        zoomScale = 1.0f;
        zoomScaleMin = 0.5f;
        zoomScaleMax = 2.0f;
        offsetX = 0.0f;
        offsetY = 0.0f;

        this.numLives = startLives;
        this.mana = startMana;
        mobs = new ArrayList<Mob>();
        // calculate optimal paths for mobs
        MobPathFinder.getInstance().calcMobPathTree(this);
        projectiles = new ArrayList<Projectile>();

        float startX = startPoints.get(0).x + 0.5f;
        float startY = startPoints.get(0).y + 0.5f;

        fastForward = false;

        paused = false;
        pausedDelta = 0.0f;
    }

    public void update() {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tiles[x][y].update(this);
            }
        }

        ArrayList<Projectile> removeProjectiles = new ArrayList<Projectile>();
        for (Projectile projectile : projectiles) {
            // the projectile update function will return
            // whether or not the projectile should be removed
            if (projectile.update()) {
                removeProjectiles.add(projectile);
            }
        }

        projectiles.removeAll(removeProjectiles);

        waveManager.update(this);

        ArrayList<Mob> removeMobs = new ArrayList<Mob>();
        for (Mob mob : mobs) {
            // the projectile update function will return
            // whether or not the mob should be removed
            mob.update();

            if (mob.getState() == Mob.STATE_DEAD) {
                removeMobs.add(mob);
                this.mana += mob.getKillReward();
            } else if (mob.getState() == Mob.STATE_REACHED_END) {
                removeMobs.add(mob);
                this.numLives -= mob.getEndPenalty();
            }
        }

        mobs.removeAll(removeMobs);
    }

    public void render(RenderEngine engine) {
        this.engine = engine;
        engine.translate(offsetX * zoomScale, offsetY * zoomScale);
        engine.scale(zoomScale, zoomScale, Game.DISPLAY_WIDTH / 2.0f + offsetX, Game.DISPLAY_HEIGHT / 2.0f + offsetY);
        engine.scale(tileWidth, tileWidth, 0.0f, 0.0f);

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tiles[x][y].render(engine);
            }
        }

        for (Mob mob : mobs) {
            mob.render(engine);
        }

        // this is to prevent mobs from being drawn over the health bar
        for (Mob mob : mobs) {
            mob.renderHealthBar(engine);
        }

        for (Projectile projectile : projectiles) {
            projectile.render(engine);
        }

        tileInterface.render(engine);

        // clears transformations
        engine.clearTransform();
        gui.render(engine);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void onTap(MotionEvent e) {
        if (gui.onTap(e.getX(), e.getY())) {
            return;
        }

        // otherwise check for game interaction
        // find location on level pressed relative to level coordinates
        float tileX = (-offsetX + e.getX()) / tileWidth;
        float tileY = (-offsetY + e.getY()) / tileWidth;

        // defocus the tile if the user presses
        // anywhere outside of the tile interface buttons
        if (tileInterface.isTileSelected()) {
            if (!tileInterface.onTap(tileX, tileY)) {
                tileInterface.clearSelection();
                return;
            }
        } else {
            // otherwise focus on the selected tile
            if (!tiles[(int) tileX][(int) tileY].isMobPassable()) {
                tileInterface.setTile(tiles[(int) tileX][(int) tileY]);
                return;
            }
        }

        // check for mob interaction
    }


    // pan level around
    public void onScroll(MotionEvent e1, MotionEvent e2, float x, float y) {
        if (gui.onScroll(e1.getX(), e1.getY(), x, y)) {
            return;
        }

        this.offsetX -= x;
        this.offsetY -= y;
        calcOffset();
    }

    // zoom in and out of level
    public void onScale(ScaleGestureDetector detector) {
        if (gui.onScale(detector.getFocusX(), detector.getFocusY(), detector.getScaleFactor())) {
            return;
        }

        //zoomScale *= detector.getScaleFactor();
        if (zoomScale > 2.0f) {
            zoomScale = 2.0f;
        } else if (zoomScale < 0.5f) {
            zoomScale = 0.5f;
        }

        calcOffset();
    }

    // fix level rendering offset when zooming
    private void calcOffset() {
        // the level should not be able to be panned beyond its boundaries
        if (offsetX > (Game.DISPLAY_WIDTH / 2) * (zoomScale - 1.0f)) {
            offsetX = (Game.DISPLAY_WIDTH / 2) * (zoomScale - 1.0f);
        }

        if (offsetY > (Game.DISPLAY_HEIGHT / 2) * (zoomScale - 1.0f)) {
            offsetY = (Game.DISPLAY_HEIGHT / 2) * (zoomScale - 1.0f);
        }

        if (offsetX < -((tileWidth * gridWidth - Game.DISPLAY_WIDTH) * zoomScale + (Game.DISPLAY_WIDTH / 2) * (zoomScale - 1.0f))) {
            offsetX = -((tileWidth * gridWidth - Game.DISPLAY_WIDTH) * zoomScale + (Game.DISPLAY_WIDTH / 2) * (zoomScale - 1.0f));
        }

        if (offsetY < -((tileWidth * gridHeight - Game.DISPLAY_HEIGHT) * zoomScale + (Game.DISPLAY_HEIGHT / 2) * (zoomScale - 1.0f))) {
            offsetY = -((tileWidth * gridHeight - Game.DISPLAY_HEIGHT) * zoomScale + (Game.DISPLAY_HEIGHT / 2) * (zoomScale - 1.0f));
        }
    }

    public void restartLevel() {

    }

    public boolean buildTower(int x, int y, int towerType) {
        if (!isValidPoint(x, y)) {
            return false;
        }

        Tower tower = null;
        switch(towerType) {
            case LevelResources.AIR_TOWER_ID:
                tower = new AirTower(x, y);
                break;
        }

        if (tower != null) {
            if (mana >= tower.getManaCost()) {
                mana -= tower.getManaCost();
                tiles[x][y].setTower(tower);
                return true;
            }
        }

        return false;
    }

    public void sellTower(int x, int y) {
        if (!isValidPoint(x, y)) {
            return;
        }

        Tile tile = getTile(x, y);
        if (tile.getTower() != null) {
            mana += tile.getTower().getSellPrice();
            tile.removeTower();
        }
    }

    public void pauseGame() {
        paused = true;
        stateManager.pushState(new GameLevelPauseState(stateManager, this));
        engine.lockDeltaTime();
    }

    public void resumeGame() {
        paused = false;
        engine.unlockDeltaTime();
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * Checks whether or not a tile is passable by mobs.
     */
    public boolean isMobPassable(int x, int y) {
        if (x < 0 || x >= gridWidth || y < 0 || y >= gridWidth) {
            return false;
        }

        return tiles[x][y].isMobPassable();
    }

    public void toggleFastForward() {
        fastForward = !fastForward;
        if (fastForward) {
            Level.TICK_TIME = Game.TICK_TIME * 2.0f;
        } else {
            Level.TICK_TIME = Game.TICK_TIME;
        }
    }

    public void loadBackground(String backgroundType) {
        if (backgroundType.equals("GRASSY_PLAINS")) {
            this.backgroundType = backgroundType;
        }
    }

    public void spawnMob(Mob mob) {
        this.mobs.add(mob);
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public ArrayList<Vec2i> getStartPoints() {
        return startPoints;
    }

    public Vec2i getEndPoint() {
        return endPoint;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && y >= 0 && x < gridWidth && y < gridHeight) {
            return tiles[x][y];
        }
        return null;
    }

    public int getMana() {
        return mana;
    }

    public int getLivesLeft() {
        return numLives;
    }

    public boolean isFastForward() {
        return fastForward;
    }

    // returns whether or not the given coordinate
    // is a valid coordinate in the level
    public boolean isValidPoint(int x, int y) {
        return !(x < 0 || y < 0 || x >= gridWidth || y >= gridHeight);
    }

    public static float getTickTime() {
        return Level.TICK_TIME;
    }

}
