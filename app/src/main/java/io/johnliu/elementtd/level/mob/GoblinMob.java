package io.johnliu.elementtd.level.mob;

import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.mob.GoblinMobEntity;

public class GoblinMob extends Mob {

    private GoblinMobEntity mobEntity;

    public GoblinMob(float x, float y) {
        super(x, y, 1.0f, 30.0f, 1.0f, 0.2f, 7, 1);
        mobEntity = new GoblinMobEntity(x, y, 20.0f, 0.2f);
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
