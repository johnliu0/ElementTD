package io.johnliu.elementtd.level.tile;

import android.graphics.Canvas;

public class Tile {

    protected int x;
    protected int y;
    protected boolean mobPassable;

    public Tile(int x, int y, boolean mobPassable) {
        this.x = x;
        this.y = y;
        this.mobPassable = mobPassable;
    }

    public void update() {}

    public void render(Canvas canvas, float deltaTime) {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMobPassable() {
        return mobPassable;
    }

}
