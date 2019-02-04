package io.johnliu.elementtd.renderengine.entity.tower;

import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class TowerEntity {

    protected int x;
    protected int y;

    public TowerEntity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void render(RenderEngine engine);

}
