package io.johnliu.elementtd.level.gui.tileinterface.layout;

import android.graphics.Canvas;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.gui.tileinterface.TileInterface;
import io.johnliu.elementtd.level.gui.tileinterface.button.CancelButton;
import io.johnliu.elementtd.level.gui.tileinterface.button.SellButton;
import io.johnliu.elementtd.level.gui.tileinterface.button.UpgradeButton;

public class UpgradeLayout extends Layout {

    private SellButton sellButton;
    private CancelButton cancelButton;
    private ArrayList<UpgradeButton> upgradeButtons;

    public UpgradeLayout(Level level, TileInterface tileInterface, int tileX, int tileY) {
        super(level, tileInterface, tileX, tileY);

        Point2d sellButtonPos = getCircleRadialPosition(60);
        sellButton = new SellButton(level, tileInterface, sellButtonPos.x, sellButtonPos.y, tileX, tileY);

        Point2d cancelButtonPos = getCircleRadialPosition(120);
        cancelButton = new CancelButton(tileInterface, cancelButtonPos.x, cancelButtonPos.y);

        Point2d upgradeButton1 = getCircleRadialPosition(-90);
        upgradeButtons = new ArrayList<UpgradeButton>();
        upgradeButtons.add(new UpgradeButton(level, tileInterface, upgradeButton1.x, upgradeButton1.y, tileX, tileY));
    }

    @Override
    public void render(Canvas canvas, float deltaTime) {
        super.render(canvas, deltaTime);

        sellButton.render(canvas, deltaTime);
        cancelButton.render(canvas, deltaTime);

        for (UpgradeButton button : upgradeButtons) {
            button.render(canvas, deltaTime);
        }
    }

    @Override
    public boolean onPress(float tileX, float tileY) {
        if (sellButton.contains(tileX, tileY)) {
            tileInterface.openConfirmLayout(sellButton);
            return true;
        }

        if (cancelButton.contains(tileX, tileY)) {
            cancelButton.doAction();
            return true;
        }

        for (UpgradeButton button : upgradeButtons) {
            if (button.contains(tileX, tileY)) {
                tileInterface.openConfirmLayout(sellButton);
                return true;
            }
        }

        return false;
    }

}
