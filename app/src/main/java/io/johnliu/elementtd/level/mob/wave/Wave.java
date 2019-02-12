package io.johnliu.elementtd.level.mob.wave;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.LevelResources;
import io.johnliu.elementtd.level.mob.WalklingMob;
import io.johnliu.elementtd.level.mob.GoblinMob;
import io.johnliu.elementtd.level.mob.Mob;

public class Wave {

    // list of all units that should be spawned sorted by spawn time
    private ArrayList<WaveUnit> waveUnits;
    // because the wave units are all sorted
    // all we need to do is keep track of the index of the
    // previous mob that was spawned, and then keep track
    // of how much time has passed in the wave
    // in order to quickly determine when mobs should be spawned
    private int unitIdx;
    private float waveTimer;
    private boolean doneSpawning;

    // describes information about a wave
    public Wave(ArrayList<WaveUnit> waveUnits) {
        this.waveUnits = waveUnits;
        unitIdx = 0;
        waveTimer = 0.0f;
        doneSpawning = false;
    }

    public void update(Level level) {
        waveTimer += Level.getTickTime();
        while (unitIdx < waveUnits.size()) {
            WaveUnit unit = waveUnits.get(unitIdx);
            if (unit.getSpawnTime() < waveTimer) {
                Mob mob = null;
                if (unit.getMobId() == LevelResources.WALKLING_MOB_ID) {
                    mob = new WalklingMob(unit.getSpawnX() + 0.5f, unit.getSpawnY() + 0.5f);
                } else if (unit.getMobId() == LevelResources.GOBLIN_MOB_ID) {
                    mob = new GoblinMob(unit.getSpawnX() + 0.5f, unit.getSpawnY() + 0.5f);
                }

                if (mob != null) {
                    level.spawnMob(mob);
                } else {
                    System.err.println("Attempted to spawn mob with non-existent id: " + unit.getMobId());
                }

                unitIdx++;
            } else {
                break;
            }
        }

        if (unitIdx == waveUnits.size()) {
            doneSpawning = true;
        }
    }

    public boolean isDoneSpawning() {
        return doneSpawning;
    }

}
