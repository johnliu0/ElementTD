package io.johnliu.elementtd.level.mob;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.Point2di;

public class MobPathFinder {

    private static MobPathFinder singleton = null;

    private int gridWidth;
    private int gridHeight;
    private Point2di endPoint;
    private PathNode[][] nodes;

    public MobPathFinder() {
        gridWidth = 0;
        gridHeight = 0;
        endPoint = null;
        nodes = new PathNode[5][5];
    }

    // calculates a minimum spanning tree
    // that describes the fastest path from
    // every passable point to the end point
    public void calcMobPathTree(Level level) {
        gridWidth = level.getGridWidth();
        gridHeight = level.getGridHeight();
        nodes = new PathNode[gridWidth][gridHeight];

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0;  y < gridHeight; y++) {
                nodes[x][y] = new PathNode(x, y, level.isMobPassable(x, y));
            }
        }


        // Dijkstra's algorithm
        endPoint = level.getEndPoint();
        nodes[endPoint.x][endPoint.y].dist = 0;
        ArrayList<PathNode> open = new ArrayList<PathNode>();

        // current node
        PathNode node = nodes[endPoint.x][endPoint.y];
        open.add(node);

        while(open.size() > 0) {
            // directly adjacent blocks are treated as 1 unit
            int newDist = node.dist + 1;

            for (int x = node.x - 1; x <= node.x + 1; x++) {
                for (int y = node.y - 1; y <= node.y + 1; y++) {
                    if (!(x != node.x && y != node.y) && !(x == node.x && y == node.y)) {
                        if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
                            if (nodes[x][y].passable && !nodes[x][y].visited) {
                                open.add(nodes[x][y]);
                                if (nodes[x][y].dist > newDist) {
                                    nodes[x][y].parent = node;
                                    nodes[x][y].dist = newDist;
                                }
                            }
                        }
                    }
                }
            }

            node.visited = true;
            open.remove(node);

            if (open.size() > 0) {
                PathNode next = open.get(0);
                for (PathNode low : open) {
                    if (low.dist < next.dist) {
                        next = low;
                    }
                }
                node = next;
            }
        }
    }

    public PathNode getClosestNode(float x, float y) {
        if (x < 0.0f || y < 0.0f || x >= gridWidth || y >= gridHeight) {
            return null;
        } else {
            return nodes[(int) x][(int) y];
        }
    }

    public static MobPathFinder getInstance() {
        if (singleton == null) {
            singleton = new MobPathFinder();
        }
        return singleton;
    }

    // simplifies the path by removing unnecessary nodes
    // that lie along a straight path
    private ArrayList<Point2di> simplifyPath(ArrayList<Point2di> path) {
        // simplify the pathing by removing
        // unnecessary points
        ArrayList<Point2di> finalPath = new ArrayList<Point2di>();
        // add the start point
        finalPath.add(path.get(0));
        int firstDiffX = path.get(1).x - path.get(0).x;
        int firstDiffY = path.get(1).y - path.get(0).y;
        // 0 = up, 1 = right, 2 = down, 3 = left
        int prevDir;
        if (firstDiffX > 0) {
            prevDir = 1; // right
        } else if (firstDiffX < 0) {
            prevDir = 3; // left
        } else if (firstDiffY > 0) {
            prevDir = 2; // down
        } else {
            prevDir = 0; // up
        }

        int dir;
        for (int i = 1; i < path.size() - 1; i++) {
            int diffX = path.get(i + 1).x - path.get(i).x;
            int diffY = path.get(i + 1).y - path.get(i).y;
            if (diffX > 0) {
                dir = 1; // right
            } else if (diffX < 0) {
                dir = 3; // left
            } else if (diffY > 0) {
                dir = 2; // down
            } else {
                dir = 0; // up
            }
            // if the direction changes from this current point
            // then we may add it to the final path
            // otherwise we may ignore this current point
            if (dir != prevDir) {
                finalPath.add(path.get(i));
                prevDir = dir;
            }
        }
        // add the end point
        finalPath.add(path.get(path.size() - 1));
        finalPath.trimToSize();

        return finalPath;
    }

}