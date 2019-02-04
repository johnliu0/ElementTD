package io.johnliu.elementtd.level.mob;

import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.BasicMobEntity;

public class BasicMob extends Mob {

    public static final int MOB_ID = 1;

    private BasicMobEntity mobEntity;

    public BasicMob(float x, float y) {
        super(x, y, 0.75f, 50.0f, 0.0f, 0.2f, 5, 1);
        mobEntity = new BasicMobEntity(x, y, 50.0f,0.2f);
    }

    @Override
    public void update() {
        super.update();
        mobEntity.capture(x, y, health);
    }

    @Override
    public void render(RenderEngine engine) {
        mobEntity.render(engine);
    }

    @Override
    public void renderHealthBar(RenderEngine engine) {
        mobEntity.renderHealthBar(engine);
    }

}
