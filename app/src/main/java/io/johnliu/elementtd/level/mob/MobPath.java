package io.johnliu.elementtd.level.mob;

import io.johnliu.elementtd.math.Vec2f;

public class MobPath {

    private PathNode target;
    private float distLeft;
    private boolean endReached;

    public MobPath(float startX, float startY) {
        target = MobPathFinder.getInstance().getClosestNode(startX, startY);
        calcDistanceLeft(target.x, target.y);
    }

    // returns a vector representing how much to displace
    public Vec2f move(float x, float y, float dist) {
        if (endReached) {
            return new Vec2f(target.x + 0.5f, target.y + 0.5f);
        }

        do {
            // difference from current position to target
            float diffX = target.x + 0.5f - x;
            float diffY = target.y + 0.5f - y;
            // since the next target always lie along either the x or y axis the
            // distance calculations may be simplified by removing the sqrt
            float diffLen = Math.abs(diffX + diffY);
            // check if distance that can be travelled is greater
            // than the distance needed to be travelled to the next point
            if (dist > diffLen) {
                x = target.x + 0.5f;
                y = target.y + 0.5f;
                if (target.parent == null) {
                    distLeft = 0.0f;
                    endReached = true;
                    return new Vec2f(x, y);
                }

                target = target.parent;
                dist -= diffLen;
                // check if mob has reached end
            } else {
                // normalize direction
                diffX /= diffLen;
                diffY /= diffLen;
                // turn direction into displacement vector
                diffX *= dist;
                diffY *= dist;
                calcDistanceLeft(x, y);
                return new Vec2f(x + diffX, y + diffY);
            }
        } while(true);
    }

    private void calcDistanceLeft(float x, float y) {
        PathNode node = target;
        distLeft = Math.abs(target.x + 0.5f - x) + Math.abs(target.y + 0.5f - y);
        while (node.parent != null) {
            distLeft += 1.0f;
            node = node.parent;
        }
    }

    public float getDistanceLeft() {
        return distLeft;
    }

    public boolean hasReachedEnd() {
        return endReached;
    }

}
