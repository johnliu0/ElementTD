package io.johnliu.elementtd.level.tower;

import android.graphics.Canvas;

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
    protected float armorPen;
    protected float manaCost;
    protected float sellPrice;
    protected String name;

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
            float range,
            float armorPen,
            float manaCost,
            float sellPrice,
            String name
    ) {
        this.x = x;
        this.y = y;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attackRate = attackRate;
        this.range = range;
        this.armorPen = armorPen;
        this.manaCost = manaCost;
        this.sellPrice = sellPrice;
        this.name = name;
        attackTarget = AttackTarget.FIRST;
        attackTimer = 0;
        attackRateNs = (long) (1000000000l / attackRate);
    }

    public void update(Level level) {
        attackTimer += Game.TICK_TIME_NS;
        if (attackTimer > attackRateNs) {
            attackTimer -= attackRateNs;
            // attack!

            ArrayList<Mob> mobs = level.getMobs();
            // find all mobs in range first
            ArrayList<Mob> mobsInRange = new ArrayList<Mob>();

            for (Mob mob : mobs) {
                float diffX = x + 0.5f - mob.getX();
                float diffY = y + 0.5f - mob.getY();
                if (diffX * diffX + diffY * diffY <= (mob.getRadius() + range) * (mob.getRadius() + range)) {
                    mobsInRange.add(mob);
                }
            }

            if (mobsInRange.size() == 0) {
                return;
            }

            Mob target = mobsInRange.get(0);
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

    public abstract void render(Canvas canvas, float deltaTime);

    protected abstract Projectile createProjectile(Mob target);

    // returns a random value between the min and max damage
    protected float getDamage() {
        return new Random().nextFloat() * (maxDamage - minDamage) + minDamage;
    }

    public float getMinDamage() {
        return minDamage;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public float getRange() {
        return range;
    }

    public float getAttackRate() {
        return attackRate;
    }

    public float getManaCost() {
        return manaCost;
    }

    public float getArmorPen() {
        return armorPen;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return name;
    }

}
