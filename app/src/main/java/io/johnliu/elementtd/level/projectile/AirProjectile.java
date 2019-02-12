package io.johnliu.elementtd.level.projectile;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.effect.SlowEffect;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.projectile.AirProjectileEntity;
import io.johnliu.elementtd.renderengine.entity.projectile.ProjectileEntity;

public class AirProjectile extends Projectile {

    private AirProjectileEntity projEntity;

    public AirProjectile(float x, float y, float damage, float armorPiercing, Mob target) {
        super(x, y, 6.0f, damage, armorPiercing, target, null);

        projEntity = new AirProjectileEntity(x, y, target.getX(), target.getY());
    }

    public boolean update() {
        projEntity.capture(x, y);
        return super.update();
    }

    public void render(RenderEngine engine) {
        projEntity.render(engine);
    }

}
