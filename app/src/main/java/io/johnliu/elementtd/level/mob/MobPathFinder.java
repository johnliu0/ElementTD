package io.johnliu.elementtd.level.mob;

import java.util.ArrayList;

import io.johnliu.elementtd.level.Level;
import io.johnliu.elementtd.level.Point2di;

public class MobPathFinder {

    private static MobPathFinder singleton = null;

    private int gridWidth;
    private int gridHeight;
    boolean[][] passable;
    private ArrayList<Point2di> startPoints;
    private Point2di endPoint;

    public MobPathFinder() {
        gridWidth = 0;
        gridHeight = 0;
        passable = null;
        endPoint = null;
        startPoints = new ArrayList<Point2di>();
    }

    // finds the optimal pathing to the end given a start position
    public MobPath findPath(int startX, int startY) {
        // check if setLevel has been set before
        if (passable == null) {
            System.out.println("ERROR: MobPathFinder.setLevel was never called");
            return null;
        }
        // check if this position is out of bounds
        if (startX < 0 || startY < 0 || startX >= gridWidth || startY >= gridWidth) {
            System.out.println("ERROR: mob out of bounds at " + startX + ", " + startY);
            return null;
        }

        if (!passable[startX][startY]) {
            System.out.println("ERROR: mob on a non-passable block at " + startX + ", " + startY);
            return null;
        }

        ArrayList<Node> openList = new ArrayList<Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();
        openList.add(new Node(startX, startY, 0, 0, null));

        while (openList.size() > 0) {
            // find node with lowest f value
            int idx = 0;
            for (int i = 1; i < openList.size(); i++) {
                if (openList.get(i).f < openList.get(idx).f) {
                    idx = i;
                }
            }

            Node node = openList.get(idx);
            openList.remove(idx);
            closedList.add(node);

            // end found
            if (node.x == endPoint.x && node.y == endPoint.y) {
                break;
            }

            // check the four directly adjacent nodes
            ArrayList<Node> children = new ArrayList<Node>();
            // left
            if (node.x - 1 >= 0 && passable[node.x - 1][node.y])   {
                children.add(new Node(
                        node.x - 1,
                        node.y,
                        1,
                        getHeuristic(node.x - 1, node.y, endPoint.x, endPoint.y),
                        node));
            }
            // right
            if (node.x + 1 < gridWidth && passable[node.x + 1][node.y]) {
                children.add(new Node(
                        node.x + 1,
                        node.y,
                        1,
                        getHeuristic(node.x + 1, node.y, endPoint.x, endPoint.y),
                        node));
            }
            // up
            if (node.y - 1 >= 0 && passable[node.x][node.y - 1]) {
                children.add(new Node(
                        node.x,
                        node.y - 1,
                        1,
                        getHeuristic(node.x, node.y - 1, endPoint.x, endPoint.y),
                        node));
            }
            // down
            if (node.y + 1 < gridHeight && passable[node.x][node.y + 1]) {
                children.add(new Node(
                        node.x,
                        node.y + 1,
                        1,
                        getHeuristic(node.x, node.y + 1, endPoint.x, endPoint.y),
                        node));
            }

            outer:
            for (Node child : children) {
                for (Node n : closedList) {
                    if (child.x == n.x && child.y == n.y) {
                        continue outer;
                    }
                }

                Node open = null;
                for (Node n : openList) {
                    if (child.x == n.x && child.y == n.y) {
                        if (child.g >= n.g) {
                            continue outer;
                        } else {
                            open = n;
                            break;
                        }
                    }
                }

                if (open == null) {
                    openList.add(child);
                } else {
                    open.parent = child;
                    open.g = child.g;
                    open.f = child.g + getHeuristic(child.x, child.y, endPoint.y, endPoint.y);
                }
            }
        }

        ArrayList<Point2di> path = new ArrayList<Point2di>();
        Node finalNode = closedList.get(closedList.size() - 1);
        path.add(new Point2di(finalNode.x, finalNode.y));
        Node traverse = finalNode.parent;
        while (traverse != null) {
            path.add(0, new Point2di(traverse.x, traverse.y));
            traverse = traverse.parent;
        }

        return new MobPath(simplifyPath(path));
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

    // updates the path finding
    // should be called once when the level loads
    // and any time the path changes
    public void updatePath(Level level) {
        gridWidth = level.getGridWidth();
        gridHeight = level.getGridHeight();
        passable = new boolean[gridWidth][level.getGridHeight()];
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0;  y < gridHeight; y++) {
                passable[x][y] = level.isMobPassable(x, y);
            }
        }

        endPoint = level.getEndPoint();
        startPoints = level.getStartPoints();
    }

    private int getHeuristic(int startX, int startY, int endX, int endY) {
        // Pythagorean theorem
        return (endX - startX) * (endX - startX) + (endY - startY) * (endY - startY);
    }

    class Node {
        Node parent;
        int x, y;
        int f, g, h;
        Node(int x, int y, int g, int h, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.parent = parent;
            f = g + h;
        }
    }
}