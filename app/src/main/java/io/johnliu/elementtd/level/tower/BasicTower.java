package io.johnliu.elementtd.level.tower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.mob.Mob;
import io.johnliu.elementtd.level.projectile.BasicProjectile;
import io.johnliu.elementtd.level.projectile.Projectile;

public class BasicTower extends Tower {

    public static final int TOWER_ID = 1;

    public BasicTower(int x, int y) {
        super(
                x,
                y,
                3,
                6,
                1.5f,
                2.0f,
                0.0f,
                50.0f,
                25.0f,
                "Fire Tower");
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(0, 0, 128));
        canvas.drawRect(x + 0.2f, y + 0.2f, x + 0.8f, y + 0.8f, paint);
    }

    @Override
    protected Projectile createProjectile(Mob target) {
        return new BasicProjectile(x + 0.5f, y + 0.5f, getDamage(), 0, target);
    }
}
