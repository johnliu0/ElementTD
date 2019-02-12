package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Stack;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.gui.CircleButton;
import io.johnliu.elementtd.gui.Layout;
import io.johnliu.elementtd.level.tile.Tile;
import io.johnliu.elementtd.math.Vec2f;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class TileInterface {

    private Level level;
    private Stack<Layout> layoutStack;
    private Tile selectedTile;
    private Action selectedAction;
    // distance from selected tile to buttons
    private float buttonSpreadRadius;
    // keep the button size consistent
    private float buttonRadius;

    private Bitmap airTowerButtonBitmap;
    private Bitmap waterTowerButtonBitmap;
    private Bitmap earthTowerButtonBitmap;
    private Bitmap fireTowerButtonBitmap;
    private Bitmap cancelButtonBitmap;
    private Bitmap confirmButtonBitmap;
    private Bitmap upgradeButtonBitmap;
    private Bitmap sellButtonBitmap;

    public TileInterface(Level level) {
        this.level = level;
        layoutStack = new Stack();
        selectedTile = null;
        selectedAction = null;
        
        buttonRadius = (Game.DISPLAY_WIDTH / Level.getTileWidth()) * 0.0225f;
        buttonSpreadRadius = 16.0f * buttonRadius / (float) Math.PI;

        airTowerButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_air_tower);
        waterTowerButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_water_tower);
        earthTowerButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_earth_tower);
        fireTowerButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_fire_tower);
        cancelButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_cancel);
        confirmButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_confirm);
        upgradeButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_upgrade);
        sellButtonBitmap = ResourceLoader.decodeResource(R.drawable.button_sell);

    }

    public void render(RenderEngine engine) {
        Canvas canvas = engine.getCanvas();
        // range of tower
        if (selectedTile != null && selectedTile.getTower() != null) {
            Paint paint = new Paint();
            paint.setColor(Color.argb(32, 0, 0, 0));
            canvas.drawCircle(selectedTile.getX() + 0.5f, selectedTile.getY() + 0.5f, selectedTile.getTower().getRange(), paint);
            paint.setColor(Color.rgb( 0, 0, 0));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(0.015f);
            canvas.drawCircle(selectedTile.getX() + 0.5f, selectedTile.getY() + 0.5f, selectedTile.getTower().getRange(), paint);
        }

        if (layoutStack.size() > 0) {
            Layout layout = layoutStack.peek();
            layout.render(engine);
        }
    }

    public void setTile(Tile tile) {
        this.selectedTile = tile;
        if (tile.getTower() == null) {
            layoutStack.push(new BuildLayout(level, this, selectedTile.getX(), selectedTile.getY(), buttonSpreadRadius));
        } else {
            layoutStack.push(new UpgradeLayout(level, this, selectedTile.getX(), selectedTile.getY(), buttonSpreadRadius));
        }
    }

    public boolean isTileSelected() {
        return selectedTile != null;
    }

    public void clearSelection() {
        this.selectedTile = null;
        this.selectedAction = null;
        this.layoutStack.clear();
    }

    // confirms the current action
    public void confirmAction() {
        if (layoutStack.size() > 0) {
            if (selectedAction != null) {
                selectedAction.doAction();
                clearSelection();
            }
        }
    }

    // cancels the current action and pops the corresponding
    // layout out of the stack so that the one previous now shows
    public void cancelAction() {
        if (layoutStack.size() > 0) {
            layoutStack.pop();
            selectedAction = null;
            if (layoutStack.size() == 0) {
                selectedTile = null;
            }
        }
    }

    // opens up a cancel/confirm layout to confirm an action
    // the action could be upgrading, selling, building, etc.
    public void openConfirmLayout(Action action) {
        this.selectedAction = action;
        layoutStack.push(new ConfirmLayout(level, this, selectedTile.getX(), selectedTile.getY(), buttonSpreadRadius));
    }

    public boolean onTap(float x, float y) {
        if (layoutStack.size() > 0) {
            return layoutStack.peek().onTap(x, y);
        }

        return false;
    }

    public boolean onScroll(float startX, float startY, float deltaX, float deltaY) {
        if (layoutStack.size() > 0) {
            return layoutStack.peek().onScroll(startX, startY, deltaX, deltaY);
        }

        return false;
    }

    public boolean onScale(float x, float y, float scale) {
        if (layoutStack.size() > 0) {
            return layoutStack.peek().onScale(x, y, scale);
        }

        return false;
    }

    class BuildLayout extends TileInterfaceLayout {

        private CancelButton cancelButton;
        private ArrayList<BuildButton> buildButtons;

        public BuildLayout(Level level, TileInterface tileInterface, int tileX, int tileY, float radius) {
            super(level, tileInterface, tileX, tileY, radius);
            this.level = level;
            this.tileInterface = tileInterface;

            Vec2f cancelButtonPos = getCircleRadialPosition(120.0f);
            cancelButton = new CancelButton(tileInterface, cancelButtonPos.x, cancelButtonPos.y, buttonRadius);
            addWidget(cancelButton);

            buildButtons = new ArrayList();

            Vec2f airTowerPos = getCircleRadialPosition(-180.0f);
            Vec2f waterTowerPos = getCircleRadialPosition(-120.0f);
            Vec2f earthTowerPos = getCircleRadialPosition(-60.0f);
            Vec2f fireTowerPos = getCircleRadialPosition(0.0f);

            buildButtons.add(
                    new BuildButton(level, tileInterface, airTowerPos.x, airTowerPos.y,
                    buttonRadius, tileX, tileY, LevelResources.AIR_TOWER_ID, airTowerButtonBitmap));

            buildButtons.add(
                    new BuildButton(level, tileInterface, waterTowerPos.x, waterTowerPos.y,
                    buttonRadius, tileX, tileY, LevelResources.WATER_TOWER_ID, waterTowerButtonBitmap));

            buildButtons.add(
                    new BuildButton(level, tileInterface, earthTowerPos.x, earthTowerPos.y,
                    buttonRadius, tileX, tileY, LevelResources.EARTH_TOWER_ID, earthTowerButtonBitmap));

            buildButtons.add(
                    new BuildButton(level, tileInterface, fireTowerPos.x, fireTowerPos.y,
                    buttonRadius, tileX, tileY, LevelResources.FIRE_TOWER_ID, fireTowerButtonBitmap));

            for (BuildButton button : buildButtons) {
                addWidget(button);
            }
        }

    }

    class UpgradeLayout extends TileInterfaceLayout {

        private SellButton sellButton;
        private CancelButton cancelButton;
        private ArrayList<UpgradeButton> upgradeButtons;

        public UpgradeLayout(Level level, TileInterface tileInterface, int tileX, int tileY, float radius) {
            super(level, tileInterface, tileX, tileY, radius);
            this.level = level;
            this.tileInterface = tileInterface;

            Vec2f sellButtonPos = getCircleRadialPosition(60.0f);
            sellButton = new SellButton(level, tileInterface, sellButtonPos.x, sellButtonPos.y, buttonRadius, tileX, tileY);
            addWidget(sellButton);

            Vec2f cancelButtonPos = getCircleRadialPosition(120.0f);
            cancelButton = new CancelButton(tileInterface, cancelButtonPos.x, cancelButtonPos.y, buttonRadius);
            addWidget(cancelButton);

            upgradeButtons = new ArrayList();
        }

    }

    class ConfirmLayout extends TileInterfaceLayout {

        private ConfirmButton confirmButton;
        private CancelButton cancelButton;

        public ConfirmLayout(Level level, TileInterface tileInterface, int tileX, int tileY, float radius) {
            super(level, tileInterface, tileX, tileY, radius);
            this.level = level;
            this.tileInterface = tileInterface;

            Vec2f confirmButtonPos = getCircleRadialPosition(60.0f);
            confirmButton = new ConfirmButton(tileInterface, confirmButtonPos.x, confirmButtonPos.y, buttonRadius);
            addWidget(confirmButton);

            Vec2f cancelButtonPos = getCircleRadialPosition(120.0f);
            cancelButton = new CancelButton(tileInterface, cancelButtonPos.x, cancelButtonPos.y, buttonRadius);
            addWidget(cancelButton);
        }

    }

    class ConfirmButton extends CircleButton {

        private TileInterface tileInterface;

        public ConfirmButton(TileInterface tileInterface, float x, float y, float radius) {
            super(x, y, radius);
            this.tileInterface = tileInterface;
            paint = new Paint();
            icon = confirmButtonBitmap;
        }

        @Override
        public void render(RenderEngine engine) {
            paint.setColor(Color.rgb(128, 128, 128));
            engine.getCanvas().drawCircle(posX, posY, radius, paint);
            renderIcon(engine.getCanvas());
        }

        @Override
        public void doAction() {
            tileInterface.confirmAction();
        }

    }

    class CancelButton extends CircleButton {

        private TileInterface tileInterface;

        public CancelButton(TileInterface tileInterface, float x, float y, float radius) {
            super(x, y, radius);
            this.tileInterface = tileInterface;
            paint = new Paint();
            icon = cancelButtonBitmap;
        }

        @Override
        public void render(RenderEngine engine) {
            paint.setColor(Color.rgb(128, 128, 128));
            engine.getCanvas().drawCircle(posX, posY, radius, paint);
            renderIcon(engine.getCanvas());
        }

        @Override
        public void doAction() {
            tileInterface.cancelAction();
        }

    }

    class BuildButton extends CircleButton {
        private Level level;
        private TileInterface tileInterface;
        private int targetX;
        private int targetY;
        private int towerId;

        public BuildButton(Level level, TileInterface tileInterface, float x, float y,
                           float radius, int targetX, int targetY, int towerId, Bitmap bitmap) {
            super(x, y, radius);
            this.level = level;
            this.tileInterface = tileInterface;
            this.targetX = targetX;
            this.targetY = targetY;
            this.towerId = towerId;
            this.icon = bitmap;
        }

        @Override
        public void render(RenderEngine engine) {
            renderIcon(engine.getCanvas());
        }

        @Override
        public void doAction() {
            tileInterface.openConfirmLayout(new Action() {
                @Override
                public void doAction() {
                    level.buildTower(targetX, targetY, towerId);
                }
            });
        }

    }

    class UpgradeButton extends CircleButton {

        private Level level;
        private TileInterface tileInterface;
        private int targetX;
        private int targetY;

        public UpgradeButton(Level level, TileInterface tileInterface,
                             float x, float y, float radius, int targetX, int targetY) {
            super(x, y, radius);
            this.level = level;
            this.targetX = targetX;
            this.targetY = targetY;
            icon = upgradeButtonBitmap;
        }

        @Override
        public void render(RenderEngine engine) {
            renderIcon(engine.getCanvas());
        }

        @Override
        public void doAction() {
            tileInterface.openConfirmLayout(new Action() {
                @Override
                public void doAction() {}
            });
        }

    }

    class SellButton extends CircleButton {

        private Level level;
        private TileInterface tileInterface;
        private int targetX;
        private int targetY;

        public SellButton(Level level, TileInterface tileInterface,
                          float x, float y, float radius, int targetX, int targetY) {
            super(x, y, radius);
            this.level = level;
            this.tileInterface = tileInterface;
            this.targetX = targetX;
            this.targetY = targetY;
            icon = sellButtonBitmap;
        }

        @Override
        public void render(RenderEngine engine) {
            renderIcon(engine.getCanvas());
        }

        @Override
        public void doAction() {
            tileInterface.openConfirmLayout(new Action() {
                @Override
                public void doAction() {
                    level.sellTower(targetX, targetY);
                }
            });
        }

    }

    // used for passing statements to functions
    abstract class Action {
        public abstract void doAction();
    }

}
