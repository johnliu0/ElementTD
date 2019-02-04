package io.johnliu.elementtd.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.johnliu.elementtd.Game;
import io.johnliu.elementtd.gamestate.StateManager;
import io.johnliu.elementtd.level.mob.BasicMob;
import io.johnliu.elementtd.level.mob.wave.WaveLoader;
import io.johnliu.elementtd.level.mob.wave.WaveManager;
import io.johnliu.elementtd.level.tile.PathTile;
import io.johnliu.elementtd.level.tile.TerrainTile;
import io.johnliu.elementtd.level.tile.Tile;
import io.johnliu.elementtd.math.Vec2i;

public class LevelLoader {

    public LevelLoader() {

    }

    public static Level loadLevel(String levelFile, StateManager stateManager) throws IOException {
        InputStream inputStream = Game.ASSETS.open(levelFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // get some basic level info
        String backgroundType = getValue(reader, "BACKGROUND_TYPE");
        int startLives = getInteger(reader, "START_LIVES");
        int startMana = getInteger(reader, "START_MANA");
        int gridWidth = getInteger(reader, "GRID_WIDTH");
        int gridHeight = getInteger(reader, "GRID_HEIGHT");

        // read the map grid
        expectLine(reader,"START_LEVEL_GRID");
        char[][] levelGrid = new char[gridWidth][gridHeight];
        for (int y = 0; y < gridHeight; y++) {
            String line = getNextValidLine(reader);
            if (line.length() != gridWidth) {
                throw new IOException("GRID_WIDTH does not match width of given grid.");
            }

            for (int x = 0; x < gridWidth; x++) {
                levelGrid[x][y] = line.charAt(x);
            }
        }

        try {
            expectLine(reader, "END_LEVEL_GRID");
        } catch (IOException e) {
            throw new IOException("GRID_HEIGHT does not match height of given grid.");
        }

        ArrayList<StartPoint> startPoints = new ArrayList();
        Vec2i endPoint = new Vec2i();

        // convert grid to actual Tile objects
        Tile[][] tiles = new Tile[gridWidth][gridHeight];
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                char c = levelGrid[x][y];
                if (c == ',') {
                    tiles[x][y] = new TerrainTile(x, y);
                } else if (c == '#') {
                    tiles[x][y] = new PathTile(x, y);
                } else if (c == 'E') {
                    tiles[x][y] = new PathTile(x, y);
                    endPoint.x = x;
                    endPoint.y = y;
                } else if (c >= '1' && c <= '9') {
                    tiles[x][y] = new PathTile(x, y);
                    startPoints.add(new StartPoint(Integer.parseInt(c + ""), x, y));
                } else {
                    tiles[x][y] = new TerrainTile(x, y);
                }
            }
        }

        if (endPoint == null) {
            throw new IOException("No end point specified.");
        }

        if (startPoints.size() == 0) {
            throw new IOException("No start point(s) specified.");
        }

        // wave information
        WaveLoader waveLoader = new WaveLoader();
        WaveManager waveManager = new WaveManager();
        while(true) {
            String line;
            try {
                line = getNextValidLine(reader);
            } catch (IOException e) {
                // end of file reached
                break;
            }

            if (!line.equals("START_WAVE")) {
                throw new IOException("Expected START_WAVE | provided " + line);
            }

            do {
                line = getNextValidLine(reader);

                if (line.equals("END_WAVE")) {
                    break;
                }

                String[] tokens = line.split(",");
                if (tokens.length != 5) {
                    throw new IOException("Wave descriptor should be in the format: " +
                            "MOB_TYPE, START_TIME, END_TIME, NUMBER, SPAWN_POINT where start and end time are in seconds | line provided: " + line);
                }

                int mobId, num, start;
                float startTime, endTime;

                if (tokens[0].equals("BASIC_MOB")) {
                    mobId = BasicMob.MOB_ID;
                } else {
                    throw new IOException("Mob does not exist: " + tokens[0]);
                }

                try {
                    startTime = Float.parseFloat(tokens[1]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid start time: " + tokens[1]);
                }

                try {
                    endTime = Float.parseFloat(tokens[2]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid end time: " + tokens[2]);
                }

                try {
                    num = Integer.parseInt(tokens[3]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid number of mobs: " + tokens[3]);
                }

                try {
                    start = Integer.parseInt(tokens[4]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid start point: " + tokens[4]);
                }

                boolean spawnFound = false;
                for (StartPoint point : startPoints) {
                    if (point.start == start) {
                        waveLoader.addWaveGroup(mobId, startTime, endTime, num, point.x, point.y);
                        spawnFound = true;
                        break;
                    }
                }

                if (!spawnFound) {
                    throw new IOException("Start not found: " + start);
                }
            } while (true);

            waveManager.addWave(waveLoader.exportWave());
        }

        // convert our spawn points into usable Vec2i spawn points
        ArrayList<Vec2i> vecStartPoints = new ArrayList();
        for (StartPoint point : startPoints) {
            vecStartPoints.add(new Vec2i(point.x, point.y));
        }

        reader.close();
        return new Level(stateManager, tiles, vecStartPoints, endPoint, startLives, startMana, waveManager, backgroundType);
    }

    /*
     * helper functions for loading maps and error handling
     */

    // gets the next non-commented and non-whitespace line
    private static String getNextValidLine(BufferedReader reader) throws IOException {
        String line;
        do {
            line = reader.readLine();

            if (line == null) {
                throw new IOException("End of file reached");
            }
            // remove all whitespace
            line = line.replaceAll("\\s+", "");
        } while (line.startsWith("//") || line.length() == 0);
        return line;
    }

    // extracts the value from a line in the KEY=VALUE format as a String
    private static String getValue(BufferedReader reader, String key) throws IOException {
        String line = getNextValidLine(reader);

        if (!line.startsWith(key)) {
            throw new IOException("Expected line " + key + "=value | line provided: " + line);
        }

        int equalsSignIndex = line.indexOf("=");
        if (equalsSignIndex == -1) {
            throw new IOException("No = sign provided after key " + key);
        }

        if (equalsSignIndex != key.length()) {
            throw new IOException("Expected key " + key + " | key provided: " + line.substring(0, equalsSignIndex));
        }

        if (equalsSignIndex == line.length() - 1) {
            throw new IOException("No value specified after key " + key);
        }

        return line.substring(equalsSignIndex + 1);
    }

    // extracts an integer from a line in the KEY=VALUE format
    private static int getInteger(BufferedReader reader, String key) throws IOException {
        String value = getValue(reader, key);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IOException("Expected an integer value | value provided: " + value);
        }
    }

    // extracts a decimal from a line in the KEY=VALUE format as a float
    private static float getDecimal(BufferedReader reader, String key) throws IOException {
        String value = getValue(reader, key);

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new IOException("Expected a decimal value | value provided: " + value);
        }
    }

    // checks that the next line is equal to a given line
    private static void expectLine(BufferedReader reader, String line) throws IOException {
        String nextLine = getNextValidLine(reader);
        if (!nextLine.equals(line)) {
            throw new IOException("Expected line " + line + " | line provided: " + nextLine);
        }
    }

}

class StartPoint {
    int start;
    int x;
    int y;
    public StartPoint(int start, int x, int y) {
        this.start = start;
        this.x = x;
        this.y = y;
    }
}
