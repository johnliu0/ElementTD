package io.johnliu.elementtd.level;

import android.graphics.Bitmap;
import android.graphics.RectF;

import io.johnliu.elementtd.R;
import io.johnliu.elementtd.ResourceLoader;
import io.johnliu.elementtd.renderengine.RenderEngine;

public class LevelBackground {

    private Bitmap tree1;

    public LevelBackground() {
        tree1 = ResourceLoader.decodeResource(R.drawable.bg_tree1);
    }

    public void render(RenderEngine engine) {
        engine.getCanvas().drawBitmap(tree1, null, new RectF(0.0f, 0.0f, 2.0f, 2.0f), null);
    }

}
