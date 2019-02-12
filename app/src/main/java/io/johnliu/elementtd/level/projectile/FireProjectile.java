package io.johnliu.elementtd.level.projectile;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.effect.BurnDotEffect;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.projectile.ProjectileEntity;

public class FireProjectile extends Projectile {

    private ProjectileEntity projEntity;

    public FireProjectile(float x, float y, float damage, float armorPiercing, Mob target) {
        super(x, y, 4.0f, damage, armorPiercing, target,
                new BurnDotEffect(5.0f, damage * 1.0f, target));

        projEntity = new ProjectileEntity(x, y, target.getX(), target.getY());
    }

    public boolean update() {
        projEntity.capture(x, y);
        return super.update();
    }

    public void render(RenderEngine engine) {
        projEntity.render(engine);
    }

}
