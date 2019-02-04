package io.johnliu.elementtd.gui;

import java.util.ArrayList;

import io.johnliu.elementtd.renderengine.RenderEngine;

public class Layout {

    private ArrayList<Widget> widgets;

    public Layout() {
        widgets = new ArrayList<Widget>();
    }

    public void render(RenderEngine engine) {
        for (Widget widget : widgets) {
            widget.render(engine);
        }
    }

    // adds a widget to this layout
    // widgets will then be rendered and respond to input
    public void addWidget(Widget widget) {
        this.widgets.add(widget);
    }

    public void deleteWidget(Widget widget) {
        this.widgets.remove(widget);
    }

    public boolean onTap(float x, float y) {
        for (Widget widget : widgets) {
            if (widget.onTap(x, y)) {
                return true;
            }
        }

        return false;
    }

    public boolean onScroll(float startX, float startY, float deltaX, float deltaY) {
        for (Widget widget : widgets) {
            if (widget.onScroll(startX, startY, deltaX, deltaY)) {
                return true;
            }
        }

        return false;
    }

    public boolean onScale(float x, float y, float scale) {
        for (Widget widget : widgets) {
            if (widget.onScale(x, y, scale)) {
                return true;
            }
        }

        return false;
    }

}
