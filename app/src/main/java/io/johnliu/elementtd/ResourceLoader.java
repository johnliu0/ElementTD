package io.johnliu.elementtd;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceLoader {

    private static ResourceLoader singleton = null;
    private Resources resources = null;

    public Bitmap decodeResource(int resource) {
        return BitmapFactory.decodeResource(resources, resource);
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public static ResourceLoader getInstance() {
        if (singleton == null) {
            singleton = new ResourceLoader();
        }
        return singleton;
    }

}
