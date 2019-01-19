package io.johnliu.elementtd.level.mob;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.Point2di;

public class MobPath {

    private PathNode target;
    private float distLeft;

    public MobPath(float startX, float startY) {
        target = MobPathFinder.getInstance().getClosestNode(startX, startY);
        calcDistanceLeft(target.x, target.y);
    }

    public Point2d move(float x, float y, float dist) {
        do {
            float diffX = target.x + 0.5f - x;
            float diffY = target.y + 0.5f - y;
            // since all paths always lie along either the x or y axis the
            // distance calculations may be simplified by removing the sqrt
            float diffLen = Math.abs(diffX + diffY);
            // check if distance that can be travelled is greater
            // than the distance needed to be travelled to the next point
            if (dist > diffLen) {
                x = target.x + 0.5f;
                y = target.y + 0.5f;
                target = target.parent;
                dist -= diffLen;
                // check if mob has reached end
                if (target.parent == null) {
                    calcDistanceLeft(x, y);
                    return new Point2d(x, y);
                }
            } else {
                // normalize direction
                diffX /= diffLen;
                diffY /= diffLen;
                // turn direction into displacement vector
                diffX *= dist;
                diffY *= dist;
                calcDistanceLeft(x, y);
                return new Point2d(x + diffX, y + diffY);
            }
        } while(true);
    }

    private void calcDistanceLeft(float x, float y) {
        PathNode node = target;
        distLeft = target.x - x + target.y - y;
        while (node.parent != null) {
            distLeft += 1.0f;
            node = node.parent;
        }
    }

    public float getDistanceLeft() {
        return distLeft;
    }

}
