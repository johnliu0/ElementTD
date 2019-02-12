package io.johnliu.elementtd.level.tower;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.EarthProjectile;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.tower.EarthTowerEntity;

public class EarthTower extends Tower {

    private EarthTowerEntity earthTowerEntity;

    public EarthTower(int x, int y) {
        super(
                x,
                y,
                5,
                10,
                0.25f,
                4.0f,
                0.0f,
                40,
                25,
                "Earth Tower");

        earthTowerEntity = new EarthTowerEntity(x, y);
    }

    @Override
    public void render(RenderEngine engine) {
        earthTowerEntity.render(engine);
    }

    @Override
    protected Projectile createProjectile(Mob target) {
        return new EarthProjectile(x + 0.5f, y + 0.5f, getDamage(), 0, target);
    }
}
