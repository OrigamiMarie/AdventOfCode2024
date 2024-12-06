package net.origamimarie.adentofcode2024;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day06 {
    private static final char OBSTRUCTION = '#';
    private static final Character TRAVELED_SQUARE = '@';
    private static final char STARTING_SQUARE = '^';
    private static final char INFINITE_PATH_SEGMENT_LIMIT = 1000;
    private static final CoordinatePair NORTH = new CoordinatePair(0, -1);
    private static final CoordinatePair EAST = new CoordinatePair(1, 0);
    private static final CoordinatePair SOUTH = new CoordinatePair(0, 1);
    private static final CoordinatePair WEST = new CoordinatePair(-1, 0);
    private static final Map<CoordinatePair, CoordinatePair> NEXT_DIRECTION = Map.of(
            NORTH, EAST,
            EAST, SOUTH,
            SOUTH, WEST,
            WEST, NORTH
    );

    public static int countTraveledSquares(String fileName) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        int width = fileLines.getFirst().length();
        int height = fileLines.size();
        char[][] grid = readLinesIntoGrid(fileLines, width, height);
        populateVisitedPathSquares(grid, width, height, findStartPoint(grid, width, height));
        System.out.println(prettyPrint(grid));
        System.out.println("\n" + prettyPrint(grid));

        return countTraveledSquares(grid);
    }

    public static int countWaysToCauseInfiniteLoop(String fileName) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        int width = fileLines.getFirst().length();
        int height = fileLines.size();
        char[][] originalGrid = readLinesIntoGrid(fileLines, width, height);
        char[][] additionalObstructionLocations = new char[width][height];
        fill(additionalObstructionLocations, '.');

        CoordinatePair startPoint = findStartPoint(originalGrid, width, height);

        int blockingOptionCount = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!startPoint.equals(new CoordinatePair(i, j))) {
                    char[][] gridWithAdditionalObstruction = copyGrid(originalGrid);
                    gridWithAdditionalObstruction[i][j] = OBSTRUCTION;
                    boolean hitSegmentLimit = populateVisitedPathSquares(gridWithAdditionalObstruction, width, height, startPoint);
                    if (hitSegmentLimit) {
                        blockingOptionCount++;
                        additionalObstructionLocations[i][j] = 'X';
//                        System.out.println("\n" + prettyPrint(gridWithAdditionalObstruction));
                    }
                }
            }
        }
        System.out.println(prettyPrint(additionalObstructionLocations));
        return blockingOptionCount;
    }

    private static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, copy[i], 0, grid[i].length);
        }
        return copy;
    }

    private static void fill(char[][] grid, char c) {
        for (char[] chars : grid) {
            Arrays.fill(chars, c);
        }
    }

    private static CoordinatePair findStartPoint(char[][] grid, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] == STARTING_SQUARE) {
                    return new CoordinatePair(i, j);
                }
            }
        }
        return new CoordinatePair(-1, -1);
    }

    private static char[][] readLinesIntoGrid(List<String> fileLines, int width, int height) {
        char[][] grid = new char[width][height];
        for (int j = 0; j < height; j++) {
            String currentLine = fileLines.get(j);
            for (int i = 0; i < width; i++) {
                grid[i][j] = currentLine.charAt(i);
            }
        }
        return grid;
    }

    // returns true if we got into an infinite traversal loop
    private static boolean populateVisitedPathSquares(char[][] grid, int width, int height, CoordinatePair nextPoint) {
        CoordinatePair currentDirection = NORTH;
        CoordinatePair currentPoint = new CoordinatePair(-1, -1);
        int pathSegments = 0;
        while (!currentPoint.equals(nextPoint) && pathSegments < INFINITE_PATH_SEGMENT_LIMIT) {
            currentPoint = nextPoint;
            nextPoint = travelToObstruction(grid, width, height, currentPoint, currentDirection);
            currentDirection = NEXT_DIRECTION.get(currentDirection);
            pathSegments++;
        }
        return pathSegments == INFINITE_PATH_SEGMENT_LIMIT;
    }

    private static CoordinatePair travelToObstruction(char[][] grid, int width, int height,
                                                      CoordinatePair currentPoint, CoordinatePair direction) {
        while(true) {
            grid[currentPoint.x][currentPoint.y] = TRAVELED_SQUARE;
            CoordinatePair nextLocation = currentPoint.add(direction);
            if (isWallAdjacent(currentPoint, width, height) || grid[nextLocation.x][nextLocation.y] == OBSTRUCTION) {
                return currentPoint;
            }
            currentPoint = nextLocation;
        }
    }

    private static boolean isWallAdjacent(CoordinatePair point, int width, int height) {
        return point.x == 0 || point.y == 0 || point.x == width - 1 || point.y == height - 1;
    }

    private static String prettyPrint(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static int countTraveledSquares(char[][] grid) {
        int totalTraveledSquares = 0;
        for (char[] chars : grid) {
            for (char aChar : chars) {
                if (aChar == TRAVELED_SQUARE) {
                    totalTraveledSquares++;
                }
            }
        }
        return totalTraveledSquares;
    }

    private record CoordinatePair(int x, int y) {
        public CoordinatePair add(CoordinatePair other) {
            return new CoordinatePair(x + other.x, y + other.y);
        }
    }

}
