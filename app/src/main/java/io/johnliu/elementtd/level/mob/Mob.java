package io.johnliu.elementtd.level.mob;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.projectile.ProjectileEffect;

public abstract class Mob {

    protected float x;
    protected float y;

    protected float speed;
    protected float health;
    protected float maxHealth;
    // armor will act as a flat reduction on damage taken
    protected float armor;
    // size of the mob
    protected float radius;

    // the path that this mob will traverse
    protected MobPath mobPath;

    protected ArrayList<ProjectileEffect> projEffects;

    public Mob(float x, float y, float speed, float health, float armor, float radius) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
        this.radius = radius;

        mobPath = new MobPath(x, y);

        this.projEffects = new ArrayList<ProjectileEffect>();
    }

    // returns whether or not this mob should be removed
    public boolean update() {
        for (ProjectileEffect effect : projEffects) {
            effect.update();
        }

        if (health <= 0) {
            return true;
        }

        Point2d move = mobPath.move(x, y, speed * Game.TICK_TIME);
        this.x = move.x;
        this.y = move.y;

        return false;
    }

    public void render(Canvas canvas, float deltaTime) {
        // health bar
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        canvas.drawRect(x - radius, y - radius - 0.05f, x + radius, y - radius, paint);
        paint.setColor(Color.rgb(0, 255, 0));
        canvas.drawRect(x - radius, y - radius - 0.05f, x + radius - (1.0f - health / maxHealth) * 2.0f * radius, y - radius, paint);
    }

    // smooths out rendering
    protected Point2d getInterpolatedPos(float deltaTime) {
        return mobPath.move(x, y, speed * deltaTime);
    }

    public void takeDamage(float damage, float armorPiercing) {
        // calculate bonus damage from armor piercing
        float armorDamage = armor - armorPiercing;
        // do not allow extra damage to be dealt
        // if armor piercing is greater than mob armor
        if (armorDamage < 0) {
            armorDamage = 0;
        }
        health -= damage + armorDamage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public float getHealth() {
        return health;
    }

    public float getArmor() {
        return armor;
    }

    public float getRadius() {
        return radius;
    }

    public float getDistanceLeft() {
        return mobPath.getDistanceLeft();
    }

    // updates the mob's position
    private void updatePosition() {

    }

}
