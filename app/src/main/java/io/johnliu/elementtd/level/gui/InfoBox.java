package io.johnliu.elementtd.level.gui;

import android.graphics.Canvas;

public class InfoBox {



    public void render(Canvas canvas, float deltaTime) {

    }

    // calculates the Y offset given line number and line spacing
    protected float getLineStartY(float fontHeight, int lineNum, float spacing) {
        return fontHeight * (lineNum - 1) + spacing * lineNum;
    }



}
