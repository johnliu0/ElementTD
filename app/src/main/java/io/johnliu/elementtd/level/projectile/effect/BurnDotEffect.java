package io.johnliu.elementtd.level.projectile.effect;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.LevelResources;
import io.johnliu.elementtd.level.mob.Mob;

public class BurnDotEffect extends ProjectileEffect {

    private float timer;
    private float duration;
    private float dps;

    public BurnDotEffect(float duration, float totalDamage, Mob target) {
        super(target, LevelResources.BURNDOT_EFFECT, false);
        this.duration = duration;
        timer = 0.0f;
        dps = totalDamage / duration;
    }

    @Override
    public void update() {
        timer += Level.getTickTime();
        if (timer > duration) {
            float diff = duration - timer - Level.getTickTime();
            target.takeDamage(dps * diff, 0);
            isDone = true;
        } else {
            target.takeDamage(dps * Level.getTickTime(), 0);
        }
    }

}
