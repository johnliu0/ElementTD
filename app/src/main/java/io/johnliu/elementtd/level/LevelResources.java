package io.johnliu.elementtd.level;

import android.graphics.Bitmap;

import java.util.HashMap;

import io.johnliu.elementtd.R;

public class LevelResources {

    private static LevelResources singleton;
    private HashMap<String, Integer> towerHashMap;
    private HashMap<String, Integer> mobHashMap;

    // towers
    public static final int AIR_TOWER_ID = 1;
    public static final String AIR_TOWER_STRING = "AIR_TOWER";
    public static final int WATER_TOWER_ID = 2;
    public static final String WATER_TOWER_STRING = "WATER_TOWER";
    public static final int EARTH_TOWER_ID = 3;
    public static final String EARTH_TOWER_STRING = "EARTH_TOWER";
    public static final int FIRE_TOWER_ID = 4;
    public static final String FIRE_TOWER_STRING = "FIRE_TOWER";

    // mobs
    public static final int WALKLING_MOB_ID = 1;
    public static final String WALKLING_MOB_STRING = "WALKLING";
    public static final int GOBLIN_MOB_ID = 2;
    public static final String GOBLIN_MOB_STRING = "GOBLIN";
    public static final int HEAVY_GOBLIN_MOB_ID = 3;
    public static final String HEAVY_GOBLIN_MOB_STRING = "HEAVY_GOBLIN";

    // projectile effects
    public static final int BURNDOT_EFFECT = 1;
    public static final String BURNDOT_EFFECT_STRING = "BURNDOT";
    public static final int SLOW_EFFECT = 1;
    public static final String SLOW_EFFECT_STRING = "SLOW";

    public LevelResources() {
        towerHashMap = new HashMap();
        mobHashMap = new HashMap();
        // note that it's more useful to use the string as the key
        // and the id as the value since during map creation
        // the names of various entities will be used for an easier
        // human readable format
        // the strings can then be converted to a simple integer which is
        // much easier to use from within the program when loading the level

        // towers
        towerHashMap.put(AIR_TOWER_STRING, AIR_TOWER_ID);
        towerHashMap.put(WATER_TOWER_STRING, WATER_TOWER_ID);
        towerHashMap.put(EARTH_TOWER_STRING, EARTH_TOWER_ID);
        towerHashMap.put(FIRE_TOWER_STRING, FIRE_TOWER_ID);

        // mobs
        mobHashMap.put(WALKLING_MOB_STRING, WALKLING_MOB_ID);
        mobHashMap.put(GOBLIN_MOB_STRING, GOBLIN_MOB_ID);
        mobHashMap.put(HEAVY_GOBLIN_MOB_STRING, HEAVY_GOBLIN_MOB_ID);
    }

    public static int towerStringToId(String string) {
        Integer i = getInstance().towerHashMap.get(string);
        if (i == null) {
            return -1;
        } else {
            return i;
        }
    }

    public static int mobStringToId(String string) {
        Integer i = getInstance().mobHashMap.get(string);
        if (i == null) {
            return -1;
        } else {
            return i;
        }
    }

    public static LevelResources getInstance() {
        if (singleton == null) {
            singleton = new LevelResources();
        }

        return singleton;
    }

}
