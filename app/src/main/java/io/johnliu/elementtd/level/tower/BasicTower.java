package io.johnliu.elementtd.level.tower;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.BasicProjectile;
import io.johnliu.elementtd.level.projectile.Projectile;

public class BasicTower extends Tower {

    public BasicTower(int x, int y) {
        super(
                x,
                y,
                3,
                6,
                1.5f,
                2.0f,
                0.0f,
                "Fire Tower");
    }

    @Override
    protected Projectile createProjectile(Mob target) {
        return new BasicProjectile(x + 0.5f, y + 0.5f, getDamage(), 0, target);
    }
}
