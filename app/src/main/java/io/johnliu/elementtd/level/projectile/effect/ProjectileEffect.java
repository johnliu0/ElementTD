package io.johnliu.elementtd.level.projectile.effect;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.mob.Mob;

public abstract class ProjectileEffect {

    protected boolean isDone;
    protected Mob target;
    protected int type;
    protected boolean stackable;

    public ProjectileEffect(Mob target, int type, boolean stackable) {
        isDone = false;
        this.type = type;
        this.target = target;
    }

    // may be called when effect is first applied to target
    public void init() {}

    public abstract void update();

    public boolean isDone() {
        return isDone;
    }

    public int getType() {
        return type;
    }

    public boolean isStackable() {
        return stackable;
    }

}
