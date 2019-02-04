package io.johnliu.elementtd.renderengine.entity.tower;

import android.graphics.Bitmap;
import android.graphics.RectF;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.Animation;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class AirTowerEntity extends TowerEntity {

    private static Bitmap pillar = null;
    private static Bitmap ball = null;

    private RectF pillarRect;
    private RectF ballRect;
    private Animation ballAnim;

    public AirTowerEntity(int x, int y) {
        super(x, y);

        if (pillar == null) {
            pillar = ResourceLoader.decodeResource(R.drawable.air_tower_pillar);
            ball = ResourceLoader.decodeResource(R.drawable.air_tower_ball);
        }

        pillarRect = new RectF(x, y, x + 1, y + 1);
        ballRect = new RectF(x + 0.3f, y + 0.1f, x + 0.7f, y + 0.5f);
        ballAnim = new Animation(ball, 4, 0.4f);
    }

    @Override
    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(pillar, null, pillarRect, null);
        ballAnim.render(engine, ballRect);
    }

}
