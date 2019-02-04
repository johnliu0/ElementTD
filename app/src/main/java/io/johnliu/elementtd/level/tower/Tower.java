package io.johnliu.elementtd.level.tower;

import java.util.ArrayList;
import java.util.Random;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.Projectile;
import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class Tower {

    protected int x;
    protected int y;

    protected float minDamage;
    protected float maxDamage;
    protected float attackRate;
    protected float range;
    protected float armorPen;
    protected int manaCost;
    protected int sellPrice;
    protected String name;

    private float attackTimer;
    private float attackRateTime;

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
            int manaCost,
            int sellPrice,
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
        attackRateTime = 1.0f / attackRate;
    }

    public void update(Level level) {
        attackTimer += Level.getTickTime();
        while (attackTimer > attackRateTime) {
            attackTimer -= attackRateTime;
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
                    for (Mob mob : mobsInRange) {
                        if (mob.getDistanceLeft() < target.getDistanceLeft()) {
                            target = mob;
                        }
                    }

                    break;
                case LAST:
                    for (Mob mob : mobsInRange) {
                        if (mob.getDistanceLeft() > target.getDistanceLeft()) {
                            target = mob;
                        }
                    }

                    break;
                case WEAKEST:
                    for (Mob mob : mobsInRange) {
                        if (mob.getHealth() < target.getHealth()){
                            target = mob;
                        }
                    }

                    break;
                case STRONGEST:
                    for (Mob mob : mobsInRange) {
                        if (mob.getHealth() > target.getHealth()) {
                            target = mob;
                        }
                    }

                    break;
            }

            level.addProjectile(createProjectile(target));
        }
    }

    public abstract void render(RenderEngine engine);

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
