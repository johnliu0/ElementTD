package io.johnliu.elementtd.level.projectile.effect;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.LevelResources;
import io.johnliu.elementtd.level.mob.Mob;

public class SlowEffect extends ProjectileEffect {

    private float timer;
    private float duration;
    private float slow;
    private float originalSpeed;

    // slow in percent
    public SlowEffect(float duration, float slow, Mob target) {
        super(target, LevelResources.SLOW_EFFECT, false);
        this.duration = duration;
        timer = 0.0f;
        this.slow = slow;
        this.originalSpeed = target.getSpeed();
    }

    @Override
    public void init() {
        target.setSpeed(originalSpeed * (1.0f - slow));
    }

    @Override
    public void update() {
        timer += Level.getTickTime();
        if (timer > duration) {
            target.setSpeed(originalSpeed);
            isDone = true;
        }
    }

}
