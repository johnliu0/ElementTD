package io.johnliu.elementtd.level.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.level.tower.Tower;

public class TowerInfoBox extends InfoBox {

    // selected tower
    private Tower tower;

    private Bitmap damageIcon;
    private Bitmap rangeIcon;
    private Bitmap attackRateIcon;
    private Bitmap armorPenIcon;
    private Paint paint;

    // variables for rendering
    private float halfBoxWidth;
    private float halfNameWidth;
    private float lineSpacing;
    private float halfIconPadding;

    public TowerInfoBox(Tower tower) {
        this.tower = tower;
        initRender();
    }

    // sets up all variables for rendering
    private void initRender() {
        damageIcon = ResourceLoader.decodeResource(R.drawable.damage_icon);
        rangeIcon = ResourceLoader.decodeResource(R.drawable.range_icon);
        attackRateIcon = ResourceLoader.decodeResource(R.drawable.attack_rate_icon);
        armorPenIcon = ResourceLoader.decodeResource(R.drawable.armor_penetration_icon);

        paint = new Paint();
        paint.setTextSize(Game.FONT_SIZE_SM);

        // maximum width of the TowerInfoBox
        halfBoxWidth = Game.DISPLAY_WIDTH / (6.0f * 2.0f);
        lineSpacing = Game.FONT_SIZE_SM / 2.0f;
        halfIconPadding = halfBoxWidth / 18.0f;

        halfNameWidth = paint.measureText(tower.getName()) / 2.0f;
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        paint.setColor(Color.argb(192, 64, 64, 64));
        canvas.drawRect(0, 0, halfBoxWidth * 2.0f,
                getLineStartY(Game.FONT_SIZE_SM, 3, lineSpacing) + Game.FONT_SIZE_SM * 2.0f, paint);
        paint.setColor(Color.rgb(255, 255, 255));

        // tower name is centred at the top
        canvas.drawText(tower.getName(), halfBoxWidth - halfNameWidth,
                getLineStartY(Game.FONT_SIZE_SM, 1, lineSpacing) + Game.FONT_SIZE_SM, paint);

        // starting y coordinate for the second line
        float startY = getLineStartY(Game.FONT_SIZE_SM, 2, lineSpacing);

        // damage
        canvas.drawBitmap(damageIcon, null, new RectF(halfIconPadding, startY,
                halfIconPadding + Game.FONT_SIZE_SM, startY + Game.FONT_SIZE_SM), paint);
        canvas.drawText((int) tower.getMinDamage() + "-" + (int) tower.getMaxDamage(),
                halfIconPadding * 2.0f + Game.FONT_SIZE_SM, startY + Game.FONT_SIZE_SM, paint);

        // range
        canvas.drawBitmap(rangeIcon, null, new RectF(halfIconPadding + halfBoxWidth, startY,
                halfIconPadding + halfBoxWidth + Game.FONT_SIZE_SM, startY + Game.FONT_SIZE_SM), paint);
        canvas.drawText(String.format("%.1f", tower.getArmorPen()),
                halfIconPadding * 2.0f + Game.FONT_SIZE_SM + halfBoxWidth, startY + Game.FONT_SIZE_SM, paint);

        // starting y coordinate for the third line
        startY = getLineStartY(Game.FONT_SIZE_SM, 3, lineSpacing);

        // attack rate
        canvas.drawBitmap(attackRateIcon, null, new RectF(halfIconPadding, startY,
                halfIconPadding + Game.FONT_SIZE_SM, startY + Game.FONT_SIZE_SM), paint);
        canvas.drawText(String.format("%.1f", tower.getAttackRate()),
                halfIconPadding * 2.0f + Game.FONT_SIZE_SM, startY + Game.FONT_SIZE_SM, paint);

        // armor penetration
        canvas.drawBitmap(armorPenIcon, null, new RectF(halfIconPadding + halfBoxWidth, startY,
                halfIconPadding + halfBoxWidth + Game.FONT_SIZE_SM, startY + Game.FONT_SIZE_SM), paint);
        canvas.drawText(String.format("%.1f", tower.getArmorPen()),
                halfIconPadding * 2.0f + Game.FONT_SIZE_SM + halfBoxWidth, startY + Game.FONT_SIZE_SM, paint);
    }

}
