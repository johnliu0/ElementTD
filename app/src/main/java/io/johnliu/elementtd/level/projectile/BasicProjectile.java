package io.johnliu.elementtd.level.projectile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.mob.Mob;

public class BasicProjectile extends Projectile {

    public BasicProjectile(float x, float y, float damage, float armorPiercing, Mob target) {
        super(x, y, 4.0f, damage, armorPiercing, target);
    }

    public void render(Canvas canvas, float deltaTime) {
        Paint paint = new Paint();
        paint.setColor(new Color().rgb(128, 128, 0));
        canvas.drawRect(x - 0.05f, y - 0.05f, x + 0.05f, y + 0.05f, paint);
    }

}
