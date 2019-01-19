package io.johnliu.elementtd;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceLoader {

    private static ResourceLoader singleton = null;
    private Resources resources = null;

    public static void setResources(Resources resources) {
        getInstance().resources = resources;
    }

    public static Bitmap decodeResource(int resource) {
        return BitmapFactory.decodeResource(getInstance().resources, resource);
    }

    public static ResourceLoader getInstance() {
        if (singleton == null) {
            singleton = new ResourceLoader();
        }
        return singleton;
    }

}
