package io.johnliu.elementtd.level.projectile;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.effect.SlowEffect;
import io.johnliu.elementtd.renderengine.RenderEngine;
import io.johnliu.elementtd.renderengine.entity.projectile.ProjectileEntity;

public class EarthProjectile extends Projectile {

    private ProjectileEntity projEntity;

    public EarthProjectile(float x, float y, float damage, float armorPiercing, Mob target) {
        super(x, y, 3.0f, damage, armorPiercing, target,
                new SlowEffect(3.0f, 0.3f, target));

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
