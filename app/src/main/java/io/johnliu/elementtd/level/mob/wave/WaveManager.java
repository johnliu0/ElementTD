package io.johnliu.elementtd.level.mob.wave;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;

public class WaveManager {

    private ArrayList<Wave> waves;
    private int waveIdx;
    private float waveTimer;
    private float waveDelay = 3.0f;
    private boolean doneSpawningWaves;

    public WaveManager() {
        waves = new ArrayList();
        waveIdx = 0;
    }

    public void update(Level level) {
        if (waveIdx < waves.size()) {
            if (!waves.get(waveIdx).isDoneSpawning()) {
                waves.get(waveIdx).update(level);
            } else if (level.getMobs().isEmpty()) {
                waveIdx++;
            }
        }
    }

    public void addWave(Wave wave) {
        waves.add(wave);
    }

    public boolean isDoneSpawningWaves() {
        return doneSpawningWaves;
    }

}
