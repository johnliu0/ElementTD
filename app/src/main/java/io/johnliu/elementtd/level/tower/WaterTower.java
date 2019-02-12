package io.johnliu.elementtd.level.tower;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.BasicProjectile;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.tower.WaterTowerEntity;

public class WaterTower extends Tower {

    private WaterTowerEntity waterTowerEntity;

    public WaterTower(int x, int y) {
        super(
                x,
                y,
                5,
                10,
                2.5f,
                2.5f,
                0.0f,
                40,
                25,
                "Water Tower");

        waterTowerEntity = new WaterTowerEntity(x, y);
    }

    @Override
    public void render(RenderEngine engine) {
        waterTowerEntity.render(engine);
    }

    @Override
    protected Projectile createProjectile(Mob target) {
        return new BasicProjectile(x + 0.5f, y + 0.5f, getDamage(), 0, target);
    }
}
