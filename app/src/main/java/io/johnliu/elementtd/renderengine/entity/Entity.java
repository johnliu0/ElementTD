package io.johnliu.elementtd.renderengine.entity;

import java.util.LinkedList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.math.Vec2f;

public class Entity {

    protected final int interpNum = 2;
    protected LinkedList<Vec2f> positions;

    // create an entity given an initial position
    public Entity(Vec2f initialPos) {
        positions = new LinkedList();
        // populate the positions queue
        // this is so that the interpolation can work right away
        for (int i = 0; i < interpNum; i++) {
            positions.add(new Vec2f(initialPos.x, initialPos.y));
        }
    }

    // captures an entity's position at one specific point in time
    // make sure to use after every logic update in the game
    public void capturePosition(Vec2f pos) {
        positions.removeFirst();
        positions.add(new Vec2f(pos.x, pos.y));
    }

    // interpolates the position of the entity given
    // the time since the 2nd last position was captured
    public Vec2f interpPos(float deltaTime) {
        // linear interpolation is used here
        // although perhaps some verlet or euler
        // integration could be used in the future
        // for even smoother movement
        float ratio = deltaTime / Level.getTickTime();
        return positions.getLast().mult(ratio).add(positions.getFirst().mult(1.0f - ratio));
    }

}
