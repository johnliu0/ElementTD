package io.johnliu.elementtd.level.mob;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import io.johnliu.elementtd.level.Point2d;

public class BasicMob extends Mob {

    public BasicMob(float x, float y) {
        super(x, y, 0.25f, 20.0f, 0.0f, 0.2f);
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        Paint paint = new Paint();
        paint.setColor(new Color().rgb(255, 0, 0));
        float len = 0.1f;
        Point2d pos = getInterpolatedPos(x, y, deltaTime * speed);
        canvas.drawRect(pos.x - len, pos.y - len, pos.x + len, pos.y + len, paint);
    }

}
