package io.johnliu.elementtd.level.mob;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Point2d;
import io.johnliu.elementtd.level.Point2di;

public class MobPath {

    private ArrayList<Point2di> path;
    private float distLeft;
    // target point that mob is trying to reach
    private Point2d target;
    // index of the target node
    private int nodeIndex;

    public MobPath(ArrayList<Point2di> path) {
        this.path = path;
        nodeIndex = 1;
        setTarget(nodeIndex);
        calcDistanceLeft(target.x, target.y);
    }

    public Point2d move(float x, float y, float dist) {
        do {
            float diffX = target.x - x;
            float diffY = target.y - y;
            // since all paths always lie along either the x or y axis the
            // distance calculations may be simplified by removing the sqrt
            float diffLen = Math.abs(diffX + diffY);
            // check if distance that can be travelled is greater
            // than the distance needed to be travelled to the next point
            if (dist > diffLen) {
                x = target.x;
                y = target.y;
                nodeIndex++;
                dist -= diffLen;
                // check if mob has reached end
                if (nodeIndex == path.size()) {
                    calcDistanceLeft(x, y);
                    return new Point2d(x, y);
                }
                // otherwise update target
                setTarget(nodeIndex);
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
        // first find the point on the path that the given point is closest to
        // this matters more with mobs that move in a non-linear fashion

        // but to be honest, this math might just be total overkill

        // let point v be the previous target
        // let point w be the current target
        // let point p be the given point
        // the point on the path that is closest
        // to the given point is the projection
        // vp onto vw plus v
        float prevX = path.get(nodeIndex - 1).x + 0.5f;
        float prevY = path.get(nodeIndex - 1).y + 0.5f;
        float diffX = target.x - prevX;
        float diffY = target.y - prevY;
        float dot = (x - prevX) * diffX + (y - prevY) * diffY;
        float len2 = diffX * diffX + diffY * diffY;
        float t = dot / len2;
        float projX = t * diffX;
        float projY = t * diffY;
        float closeX = prevX + projX;
        float closeY = prevY + projY;
        float dist = Math.abs(diffX - closeX + diffY - closeY);
        // go through each segment that the mob must move through
        for (int i = nodeIndex; i < path.size() - 1; i++) {
            float dX = path.get(i + 1).x - path.get(i).x;
            float dY = path.get(i + 1).y - path.get(i).y;
            dist += diffX + diffY;
        }
        this.distLeft = dist;
    }

    // set target
    // note that 0.5f is added so that the target is the center of the cell
    private void setTarget(int index) {
        target = new Point2d(path.get(index).x + 0.5f, path.get(index).y + 0.5f);
    }

    public float getDistanceLeft() {
        return distLeft;
    }

}
