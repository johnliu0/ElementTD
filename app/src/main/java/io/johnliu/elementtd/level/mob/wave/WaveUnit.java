package io.johnliu.elementtd.level.mob.wave;

public class WaveUnit {

    private int mobId;
    private float spawnTime;
    private int spawnX;
    private int spawnY;

    public WaveUnit(int mobId, float spawnTime, int spawnX, int spawnY) {
        this.mobId = mobId;
        this.spawnTime = spawnTime;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    public int getMobId() {
        return mobId;
    }

    public float getSpawnTime() {
        return spawnTime;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

}
