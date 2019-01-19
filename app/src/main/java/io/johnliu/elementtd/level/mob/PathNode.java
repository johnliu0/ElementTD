package io.johnliu.elementtd.level.mob;

public class PathNode {

    public PathNode parent;
    public int x;
    public int y;
    public int dist;
    public boolean passable;
    public boolean visited;

    public PathNode(int x, int y, boolean passable) {
        this.x = x;
        this.y = y;
        this.dist = 1000000000; // effectively infinite
        this.passable = passable;
        this.parent = null;
        visited = !passable;
    }

}
