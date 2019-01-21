package io.johnliu.elementtd.level.gui.tileinterface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Stack;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.gui.tileinterface.button.Button;
import io.johnliu.elementtd.level.gui.tileinterface.layout.BuildLayout;
import io.johnliu.elementtd.level.gui.tileinterface.layout.ConfirmLayout;
import io.johnliu.elementtd.level.gui.tileinterface.layout.Layout;
import io.johnliu.elementtd.level.gui.tileinterface.layout.UpgradeLayout;
import io.johnliu.elementtd.level.tile.Tile;

public class TileInterface {

    private Level level;
    private Stack<Layout> layoutStack;
    private Button selectedAction;
    private Tile selectedTile;

    public TileInterface(Level level) {
        this.level = level;
        layoutStack = new Stack<Layout>();
        selectedAction = null;
        selectedTile = null;
    }

    public void update() {}

    public void render(Canvas canvas, float deltaTime) {
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
            layout.render(canvas, deltaTime);
        }
    }

    // handles input given an input coordinate
    // returns whether or not the input did anything
    public boolean onPress(float tileX, float tileY) {
        if (layoutStack.size() > 0) {
            if (layoutStack.peek().onPress(tileX, tileY)) {
                return true;
            }
        }

        return false;
    }

    // opens up a cancel/confirm layout to confirm an action
    // the action could be upgrading, selling, building, etc.
    public void openConfirmLayout(Button button) {
        this.selectedAction = button;
        layoutStack.push(new ConfirmLayout(level, this, selectedTile.getX(), selectedTile.getY()));
    }

    // if there is a cancel/confirm layout for an action
    // then this handles the confirm action
    public void confirmAction() {
        if (layoutStack.size() > 0) {
            if (layoutStack.peek() instanceof ConfirmLayout) {
                if (selectedAction != null) {
                    selectedAction.doAction();
                }
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

    public void setTile(Tile tile) {
        this.selectedTile = tile;

        if (tile.getTower() == null) {
            layoutStack.push(new BuildLayout(level, this, selectedTile.getX(), selectedTile.getY()));
        } else {
            layoutStack.push(new UpgradeLayout(level, this, selectedTile.getX(), selectedTile.getY()));
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

}
