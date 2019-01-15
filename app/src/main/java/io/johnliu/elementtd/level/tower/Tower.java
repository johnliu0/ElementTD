package io.johnliu.elementtd.level.tower;

import java.util.ArrayList;
import java.util.Random;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.Projectile;

public abstract class Tower {

    protected int x;
    protected int y;

    protected float minDamage;
    protected float maxDamage;
    protected float attackRate;
    protected float range;


    private long attackTimer;
    private long attackRateNs;

    private enum AttackTarget {
        FIRST, LAST, WEAKEST, STRONGEST
    }

    AttackTarget attackTarget;

    public Tower(
            int x,
            int y,
            float minDamage,
            float maxDamage,
            float attackRate,
            float range
    ) {
        this.x = x;
        this.y = y;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attackRate = attackRate;
        this.range = range;
        attackTarget = AttackTarget.FIRST;
        attackTimer = 0;
        attackRateNs = (long) (1.0f / attackRate) * 1000000000l;
    }

    public void update(Level level) {
        attackTimer += Game.TICK_TIME_NS;
        if (attackTimer > attackRateNs) {
            attackTimer -= attackRateNs;
            // attack!
            ArrayList<Mob> mobs = level.getMobs();
            Mob target = mobs.get(0);
            // determine which mob to hit
            switch (attackTarget) {
                case FIRST:
                    for (Mob mob : mobs) {
                        if (mob.getDistanceLeft() < target.getDistanceLeft()) {
                            target = mob;
                        }
                    }

                    break;
                case LAST:
                    for (Mob mob : mobs) {
                        if (mob.getDistanceLeft() > target.getDistanceLeft()) {
                            target = mob;
                        }
                    }

                    break;
                case WEAKEST:
                    for (Mob mob : mobs) {
                        if (mob.getHealth() < target.getHealth()){
                            target = mob;
                        }
                    }

                    break;
                case STRONGEST:
                    for (Mob mob : mobs) {
                        if (mob.getHealth() > target.getHealth()) {
                            target = mob;
                        }
                    }

                    break;
            }

            level.addProjectile(createProjectile(target));
        }
    }

    protected abstract Projectile createProjectile(Mob target);

    // returns a random value between the min and max damage
    protected float getDamage() {
        return new Random().nextFloat() * (minDamage - maxDamage) + minDamage;
    }

}
