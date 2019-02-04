package io.johnliu.elementtd;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

public class ResourceLoader {

    private static ResourceLoader singleton = null;
    private Resources resources = null;

    private Typeface defaultFont;
    private Typeface defaultFontBold;

    public static void setResources(Resources resources) {
        getInstance().resources = resources;
        getInstance().defaultFont = Typeface.createFromAsset(Game.ASSETS, "font/quattrocento_regular.ttf");
        getInstance().defaultFontBold = Typeface.createFromAsset(Game.ASSETS, "font/quattrocento_bold.ttf");
    }

    public static Bitmap decodeResource(int resource) {
        return BitmapFactory.decodeResource(getInstance().resources, resource);
    }

    public static Typeface getDefaultFont() {
        return getInstance().defaultFont;
    }

    public static Typeface getDefaultFontBold() {
        return getInstance().defaultFontBold;
    }

    public static ResourceLoader getInstance() {
        if (singleton == null) {
            singleton = new ResourceLoader();
        }
        return singleton;
    }

}
