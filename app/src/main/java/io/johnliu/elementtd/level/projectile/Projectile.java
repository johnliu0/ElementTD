package io.johnliu.elementtd.level.projectile;

import android.graphics.Canvas;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.Point2d;
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
        Point2d move = move(Game.TICK_TIME);
        float diffX = target.getX() - move.x;
        float diffY = target.getY() - move.y;
        if (diffX * diffX + diffY * diffY < target.getRadius() * target.getRadius()) {
            target.takeDamage(damage, armorPiercing);
            return true;
        }

        x = move.x;
        y = move.y;
        return false;
    }

    public abstract void render(Canvas canvas, float deltaTime);

    // smooths out rendering
    protected Point2d getInterpolatedPos(float deltaTime) {
        return move(deltaTime);
    }

    private Point2d move(float deltaTime) {
        float diffX = target.getX() - x;
        float diffY = target.getY() - y;
        // normalize direction
        float diffSqr =  diffX * diffX + diffY * diffY;
        float diffLen = (float) Math.sqrt(diffSqr);
        diffX /= diffLen;
        diffY /= diffLen;
        // adjust for speed
        diffX *= speed * deltaTime;
        diffY *= speed * deltaTime;
        float moveSqr = diffX * diffX + diffY * diffY;

        if (moveSqr >= diffSqr) {
            return new Point2d(target.getX(), target.getY());
        }

        return new Point2d(x + diffX, y + diffY);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
