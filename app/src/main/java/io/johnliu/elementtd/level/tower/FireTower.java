package io.johnliu.elementtd.level.tower;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.FireProjectile;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.tower.FireTowerEntity;

public class FireTower extends Tower {

    private FireTowerEntity fireTowerEntity;

    public FireTower(int x, int y) {
        super(
                x,
                y,
                10,
                15,
                1.0f,
                2.5f,
                0.0f,
                40,
                25,
                "Fire Tower");

        fireTowerEntity = new FireTowerEntity(x, y);
    }

    @Override
    public void render(RenderEngine engine) {
        fireTowerEntity.render(engine);
    }

    @Override
    protected Projectile createProjectile(Mob target) {
        return new FireProjectile(x + 0.5f, y + 0.5f, getDamage(), 0, target);
    }
}
