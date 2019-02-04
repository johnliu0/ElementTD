package io.johnliu.elementtd.level.mob;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.projectile.ProjectileEffect;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class Mob {

    public static final int STATE_ALIVE = 0;
    public static final int STATE_DEAD = 1;
    public static final int STATE_REACHED_END = 2;

    protected float x;
    protected float y;

    protected float speed;
    protected float health;
    protected float maxHealth;
    // armor will act as a flat reduction on damage taken
    protected float armor;
    // size of the mob
    protected float radius;
    // how much mana is granted for killing this mob
    protected int killReward;
    // how many lives are lost if this mob reaches the end
    protected int endPenalty;
    // state of the mob (states described above)
    protected int mobState;
    // any effects from projectiles
    protected ArrayList<ProjectileEffect> projEffects;
    // the path that this mob will traverse
    protected MobPath mobPath;

    public Mob(
            float x,
            float y,
            float speed,
            float health,
            float armor,
            float radius,
            int killReward,
            int endPenalty
    ) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
        this.radius = radius;
        this.killReward = killReward;
        this.endPenalty = endPenalty;
        this.projEffects = new ArrayList();
        mobPath = new MobPath(x, y);
        mobState = STATE_ALIVE;
    }

    public void update() {
        for (ProjectileEffect effect : projEffects) {
            effect.update();
        }

        if (health <= 0.0f) {
            mobState = STATE_DEAD;
        }

        Vec2f newPos = mobPath.move(x, y, speed * Level.getTickTime());
        x = newPos.x;
        y = newPos.y;

        if (mobPath.hasReachedEnd()) {
            mobState = STATE_REACHED_END;
        }
    }

    public abstract void render(RenderEngine engine);

    public abstract void renderHealthBar(RenderEngine engine);

    public void takeDamage(float damage, float armorPiercing) {
        // calculate bonus damage from armor piercing
        float armorDamage = armor - armorPiercing;
        // do not allow extra damage to be dealt
        // if armor piercing is greater than mob armor
        if (armorDamage < 0) {
            armorDamage = 0;
        }

        health -= damage + armorDamage;

        if (health < 0.0f) {
            health = 0.0f;
        }
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

    public int getKillReward() {
        return killReward;
    }

    public int getEndPenalty() {
        return endPenalty;
    }

    public int getState() {
        return mobState;
    }

    public float getDistanceLeft() {
        return mobPath.getDistanceLeft();
    }

}
