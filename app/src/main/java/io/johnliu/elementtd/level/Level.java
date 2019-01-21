package io.johnliu.elementtd.level;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import java.util.ArrayList;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;
import io.johnliu.elementtd.level.gui.LevelGui;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;
import io.johnliu.elementtd.level.mob.BasicMob;
import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.mob.MobPathFinder;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.level.tile.Tile;
import io.johnliu.elementtd.level.tower.BasicTower;
import io.johnliu.elementtd.level.tower.Tower;

public class Level {

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
    private ArrayList<Point2di> startPoints;
    // end destination for all mobs
    private Point2di endPoint;

    /**
     * Rendering
     */
    private LevelGui gui;
    private TileInterface tileInterface;
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
    private int numLives;
    // currency
    private float mana;
    // list of all active mobs
    private ArrayList<Mob> mobs;
    // list of all projectile entities
    private ArrayList<Projectile> projectiles;
    // list of all towers
    private ArrayList<Tower> towers;
    // currently selected tile
    //  private Point2di

    /**
     * The level object used to represent one distinct level.
     * A level can be loaded using the LevelLoader.
     */
    public Level(
            Tile[][] tiles, // initial tile setup
            ArrayList<Point2di> startPoints, // start points for mobs
            Point2di endPoint, // end point for mobs
            int startLives, // number of lives to begin level with
            float startMana // amount of mana to begin level with
    ) {
        this.gridWidth = tiles.length;
        this.gridHeight = tiles[0].length;
        // by default the tileWidth will be such that the
        // width of the map matches the width of the screen
        this.tileWidth = Game.DISPLAY_WIDTH / gridWidth;
        this.tiles = tiles;
        this.startPoints = startPoints;
        this.endPoint = endPoint;

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

        mobs.add(new BasicMob(startX, startY));
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

        ArrayList<Mob> removeMobs = new ArrayList<Mob>();
        for (Mob mob : mobs) {
            // the projectile update function will return
            // whether or not the mob should be removed
            if (mob.update()) {
                removeMobs.add(mob);
            }
        }

        mobs.removeAll(removeMobs);

//        mobWaveTimer += Game.TICK_TIME;
//        if (mobWaveTimer >= 1000) {
//            mobWaveTimer = 0;
//            Random rand = new Random();
//            //mobs.add(new BasicMob(rand.nextFloat() * this.gridWidth, rand.nextFloat() * this.gridHeight));
//        }
//

    }

    public void render(Canvas canvas, float deltaTime) {
        canvas.translate(offsetX * zoomScale, offsetY * zoomScale);
        canvas.scale(zoomScale, zoomScale, Game.DISPLAY_WIDTH / 2.0f + offsetX, Game.DISPLAY_HEIGHT / 2.0f + offsetY);
        canvas.scale(tileWidth, tileWidth, 0.0f, 0.0f);

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                tiles[x][y].render(canvas, deltaTime);
            }
        }

        for (Mob mob : mobs) {
            mob.render(canvas, deltaTime);
        }

        for (Projectile projectile : projectiles) {
            projectile.render(canvas, deltaTime);
        }

        tileInterface.render(canvas, deltaTime);
        // clears transformations
        canvas.setMatrix(null);
        gui.render(canvas, deltaTime);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void onTap(MotionEvent e) {
        // first check for gui interaction
        if (gui.onPress(e.getX(), e.getY())) {
            return;
        }

        // otherwise check for game interaction
        // find location on level pressed relative to level coordinates
        float tileX = (-offsetX + e.getX()) / tileWidth;
        float tileY = (-offsetY + e.getY()) / tileWidth;

        // defocus the tile if the user presses
        // anywhere outside of the tile interface buttons
        if (tileInterface.isTileSelected()) {
            if (!tileInterface.onPress(tileX, tileY)) {
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
    public void onScroll(float x, float y) {
        this.offsetX -= x;
        this.offsetY -= y;
        calcOffset();
    }

    // zoom in and out of level
    public void onScale(ScaleGestureDetector detector) {
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

    public boolean buildTower(int x, int y, int towerType) {
        if (!isValidPoint(x, y)) {
            return false;
        }

        Tower tower = null;
        switch(towerType) {
            case BasicTower.TOWER_ID:
                tower = new BasicTower(x, y);
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

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public ArrayList<Point2di> getStartPoints() {
        return startPoints;
    }

    public Point2di getEndPoint() {
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

    public float getMana() {
        return mana;
    }

    public float getLives() {
        return numLives;
    }

    // returns whether or not the given coordinate
    // is a valid coordinate in the level
    public boolean isValidPoint(int x, int y) {
        return !(x < 0 || y < 0 || x >= gridWidth || y >= gridHeight);
    }

}
