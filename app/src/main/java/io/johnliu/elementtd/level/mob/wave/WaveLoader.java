package io.johnliu.elementtd.level.mob.wave;

import java.util.ArrayList;

public class WaveLoader {

    private ArrayList<WaveUnit> waveUnits;

    public WaveLoader() {
        waveUnits = new ArrayList();
    }

    // spawn a given number of a single mob type evenly throughout the start and end time
    public void addWaveGroup(int mobId, float startTime, float endTime, int numMobs, int spawnX, int spawnY) {
        if (numMobs == 1) {
            addWaveUnit(mobId, startTime, spawnX, spawnY);
        } else {
            float delay = (endTime - startTime) / (numMobs - 1);
            for (int i = 0; i < numMobs; i++) {
                addWaveUnit(mobId, startTime + i * delay, spawnX, spawnY);
            }
        }
    }

    private void addWaveUnit(int mobId, float spawnTime, int spawnX, int spawnY) {
        WaveUnit unit = new WaveUnit(mobId, spawnTime, spawnX, spawnY);
        // find the index to insert this unit into
        // the wave units should be sorted in terms of spawn time+
        if (waveUnits.size() == 0) {
            waveUnits.add(unit);
        } else {
            int left = 0;
            int right = waveUnits.size() - 1;
            int idx = (right + left) / 2;

            while (right - left > 0) {
                if (waveUnits.get(idx).getSpawnTime() > spawnTime) {
                    right = idx;
                } else if (waveUnits.get(idx).getSpawnTime() < spawnTime) {
                    left = idx + 1;
                } else {
                    break;
                }

                idx = (right + left) / 2;
            }

            if (waveUnits.get(idx).getSpawnTime() > spawnTime) {
                waveUnits.add(idx, unit);
            } else {
                waveUnits.add(idx + 1, unit);
            }
        }
    }

    // once you finish adding the wave groups you can export to a wave object
    // this will clear the wave units
    public Wave exportWave() {
        Wave wave = new Wave(waveUnits);
        waveUnits = new ArrayList();
        return wave;
    }

}
