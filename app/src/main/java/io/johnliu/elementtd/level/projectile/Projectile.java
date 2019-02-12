package io.johnliu.elementtd.level.projectile;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.effect.ProjectileEffect;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

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
            Mob target,
            ProjectileEffect effect
    ) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.armorPiercing = armorPiercing;
        this.target = target;
        this.effect = effect;
    }

    // returns whether or not this projectile should be removed
    public boolean update() {
        Vec2f move = move(Level.getTickTime());
        float diffX = target.getX() - move.x;
        float diffY = target.getY() - move.y;
        if (diffX * diffX + diffY * diffY < target.getRadius() * target.getRadius()) {
            target.takeDamage(damage, armorPiercing);
            if (effect != null) {
                target.applyEffect(effect);
            }

            return true;
        }

        x = move.x;
        y = move.y;
        return false;
    }

    public abstract void render(RenderEngine engine);

    private Vec2f move(float deltaTime) {
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
            return new Vec2f(target.getX(), target.getY());
        }

        return new Vec2f(x + diffX, y + diffY);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
