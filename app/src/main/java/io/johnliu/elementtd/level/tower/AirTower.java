package io.johnliu.elementtd.level.tower;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.BasicProjectile;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.tower.AirTowerEntity;
import io.johnliu.elementtd.renderengine.entity.tower.TowerEntity;

public class AirTower extends Tower {

    private AirTowerEntity airTowerEntity;

    public AirTower(int x, int y) {
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
                "Air Tower");

        airTowerEntity = new AirTowerEntity(x, y);
    }

    @Override
    public void render(RenderEngine engine) {
        airTowerEntity.render(engine);
    }

    @Override
    protected Projectile createProjectile(Mob target) {
        return new BasicProjectile(x + 0.5f, y + 0.5f, getDamage(), 0, target);
    }
}
