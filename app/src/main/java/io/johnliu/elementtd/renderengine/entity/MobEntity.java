package io.johnliu.elementtd.renderengine.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public abstract class MobEntity {
    // paint class shared across all mobs
    // to only be used for painting simple items like the healthbar
    protected static Paint paint = null;
    // variables for mob movement interpolation
    private static float interpDelta, interpCoeff1, interpCoeff2, interpCoeff3, interpDenom;

    // will hold the previous three positions of the mob
    protected float[] positions;
    private float maxHealth;
    // animHealth will be used to animate the health bar
    // smoothly shrinking when the mob takes damage
    private float animHealth;
    private float currentHealth;
    // size of the mob
    private float healthBarHeight = 0.03f;
    protected float radius;
    protected Vec2f interpPos;

    // rendering class for the Mob object
    public MobEntity(float x, float y, float maxHealth, float radius) {
        positions = new float[] { x, y, x, y, x, y };
        this.maxHealth = maxHealth;
        this.animHealth = animHealth;
        this.currentHealth = maxHealth;
        this.radius = radius;
        interpPos = new Vec2f();

        if (paint == null) {
            paint = new Paint();
        }
    }

    public void render(RenderEngine engine) {
        Canvas canvas = engine.getCanvas();
        float delta = engine.getDeltaTime();

        interpPos = interp(delta);
    }

    public void renderHealthBar(RenderEngine engine) {
        paint.setColor(Color.rgb(255, 0, 0));
        engine.getCanvas().drawRect(
                interpPos.x - radius,
                interpPos.y - radius - healthBarHeight,
                interpPos.x + radius,
                interpPos.y - radius,
                paint);
        paint.setColor(Color.rgb(0, 255, 0));

        engine.getCanvas().drawRect(
                interpPos.x - radius,
                interpPos.y - radius - healthBarHeight,
                interpPos.x + radius - (1.0f - currentHealth / maxHealth) * radius * 2.0f,
                interpPos.y - radius,
                paint);
    }

    // interpolates the position of the mob
    protected Vec2f interp(float delta) {
        if (interpDelta != delta) {
            // this is done by performing a quadratic interpolation on both the x and y axis
            // note im not actually sure how the math works but it works!
            float tt = Level.getTickTime() * Level.getTickTime();
            float td = Level.getTickTime() * delta;
            float dd = delta * delta;
            // i can however simplify the equations behind it and optimize it for code usage
            // these static variables below can be used to interpolate any other mob
            // since they are just fixed coefficients calculated based on Game.TICK_TIME and deltaTime
            // all that is needed is to have just one mob update this info every time deltaTime changes
            // and every other mob can simply reuse these numbers to interpolate allowing for
            // extremely efficient rendering interpolation
            interpCoeff1 = dd - 3.0f * td + 2.0f * tt;
            interpCoeff2 = 4.0f * td - 2.0f * dd;
            interpCoeff3 = dd - td;
            interpDenom = 2.0f * tt;
            interpDelta = delta;
        }

        float x = (positions[0] * interpCoeff1 + positions[2] * interpCoeff2 + positions[4] * interpCoeff3) / interpDenom;
        float y = (positions[1] * interpCoeff1 + positions[3] * interpCoeff2 + positions[5] * interpCoeff3) / interpDenom;
        return new Vec2f(x, y);
    }


    // update the rendering variables
    public void capture(float x, float y, float health) {
        positions[0] = positions[2];
        positions[1] = positions[3];
        positions[2] = positions[4];
        positions[3] = positions[5];
        positions[4] = x;
        positions[5] = y;
        currentHealth = health;
    }

}
