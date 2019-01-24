package io.johnliu.elementtd.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.gamestate.StateManager;
import io.johnliu.elementtd.level.tile.PathTile;
import io.johnliu.elementtd.level.tile.TerrainTile;
import io.johnliu.elementtd.level.tile.Tile;

public class LevelLoader {



    public LevelLoader() {

    }

    public static Level loadLevel(String levelFile, StateManager stateManager) throws Exception {
        InputStream inputStream = Game.ASSETS.open(levelFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        if (!line.startsWith("GRID_WIDTH:")) {
            throw new IOException("Invalid level file. No GRID_WIDTH specifier.");
        }

        int gridWidth;
        try {
            gridWidth = Integer.parseInt(line.substring("GRID_WIDTH:".length()));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid level file. GRID_WIDTH specifier corrupt.");
        }

        line = reader.readLine();
        if (!line.startsWith("GRID_HEIGHT:")) {
            throw new IOException("Invalid level file. No GRID_HEIGHT specifier.");
        }

        int gridHeight;
        try {
            gridHeight = Integer.parseInt(line.substring("GRID_HEIGHT:".length()));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid level file. GRID_HEIGHT specifier corrupt.");
        }

        line = reader.readLine();
        if (!line.equals("START_LEVEL_GRID")) {
            throw new IOException("Invalid level file. No START_LEVEL_GRID specified.");
        }

        char[][] levelGrid = new char[gridWidth][gridHeight];

        for (int y = 0; y < gridHeight; y++) {
            line = reader.readLine();
            for (int x = 0; x < gridWidth; x++) {
                levelGrid[x][y] = line.charAt(x);
            }
        }

        line = reader.readLine();
        if (!line.equals("END_LEVEL_GRID")) {
            throw new IOException("Invalid level file. No END_LEVEL_GRID specified.");
        }

        ArrayList<Point2di> startPoints = new ArrayList<Point2di>();
        Point2di endPoint = null;

        Tile[][] tiles = new Tile[gridWidth][gridHeight];
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                switch (levelGrid[x][y]) {
                    case '.':
                        tiles[x][y] = new TerrainTile(x, y);
                        break;
                    case '#':
                        tiles[x][y] = new PathTile(x, y);
                        break;
                    case 'S':
                        tiles[x][y] = new PathTile(x, y);
                        startPoints.add(new Point2di(x, y));
                        break;
                    case 'E':
                        tiles[x][y] = new PathTile(x, y);
                        endPoint = new Point2di(x, y);
                        break;
                    default:
                        tiles[x][y] = new TerrainTile(x, y);
                        break;
                }
            }
        }

        if (endPoint == null) {
            throw new IOException("Invalid level file. No end point specified.");
        }

        line = reader.readLine();
        if (!line.startsWith("START_LIVES:")) {
            throw new IOException("Invalid level file. No start lives specifier.");
        }

        int startLives;
        try {
            startLives = Integer.parseInt(line.substring("START_LIVES:".length()));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid level file. START_LIVES specifier corrupt.");
        }

        line = reader.readLine();
        if (!line.startsWith("START_MANA:")) {
            throw new IOException("Invalid level file. No HEIGHT specifier.");
        }

        int startMana;
        try {
            startMana = Integer.parseInt(line.substring("START_MANA:".length()));
        } catch (NumberFormatException e) {
            throw new IOException("Invalid level file. START_MANA specifier corrupt.");
        }

        reader.close();

        return new Level(stateManager, tiles, startPoints, endPoint, startLives, startMana);
    }
}
