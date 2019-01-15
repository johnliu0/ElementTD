package io.johnliu.elementtd.level.projectile;

import android.graphics.Canvas;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.mob.Mob;

public abstract class Projectile {

    protected float x;
    protected float y;
    protected float speed;
    protected float damage;
    protected float armorPiercing;
    protected ProjectileEffect effect;

    protected Mob target;

    /**
     * Entity that the tower produces to attack mobs.
     */
    public Projectile(
            float x,
            float y,
            float speed,
            float damage,
            float armorPiercing,
            Mob target
    ) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.armorPiercing = armorPiercing;
        this.target = target;
        effect = null;
    }

    // returns whether or not this projectile should be removed
    public boolean update() {
        float diffX = target.getX() - x;
        float diffY = target.getY() - y;
        // normalize direction
        float diffSqr =  diffX * diffX + diffY * diffY;
        float diffLen = (float) Math.sqrt(diffSqr);
        diffX /= diffLen;
        diffY /= diffLen;
        // adjust for speed
        diffX *= speed * Game.TICK_TIME;
        diffY *= speed * Game.TICK_TIME;

        float moveSqr = diffX * diffX + diffY * diffY;

        // check if the projectile has reached the target
        if (moveSqr >= diffSqr) {
            target.takeDamage(damage, armorPiercing);
            return true;
        }

        x += diffX;
        y += diffY;

        return false;
    }

    public abstract void render(Canvas canvas, float deltaTime);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
