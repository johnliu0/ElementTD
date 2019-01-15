package io.johnliu.elementtd.level.projectile;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.mob.Mob;

public abstract class ProjectileEffect {

    private long timer;
    private long duration;
    private Mob target;

    public ProjectileEffect(long duration, Mob target) {
        timer = 0;
        this.duration = duration;
        this.target = target;
    }

    public void update() {
        timer += Game.TICK_TIME;
    }

}
