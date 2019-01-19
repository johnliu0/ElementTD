package io.johnliu.elementtd.level.mob;

import android.graphics.Canvas;

import java.util.ArrayList;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.projectile.ProjectileEffect;

public abstract class Mob {

    protected float x;
    protected float y;

    protected float speed;
    protected float health;
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

        Point2d newPos = mobPath.move(x, y, speed * Game.TICK_TIME);
        this.x = newPos.x;
        this.y = newPos.y;

        return false;
    }

    public void render(Canvas canvas, float deltaTime) {}

    // smooth movement for rendering
    protected Point2d getInterpolatedPos(float x, float y, float deltaTime) {
        return mobPath.move(x, y,  speed * deltaTime);
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

    public float getDistanceLeft() {
        return mobPath.getDistanceLeft();
    }

    // updates the mob's position
    private void updatePosition() {

    }

}
