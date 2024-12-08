package net.origamimarie.adentofcode2024;

import com.google.common.io.Resources;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day06 {
    private static final TextColor FOREGROUND_COLOR = new TextColor.RGB(0, 128, 255);
    private static final char OBSTRUCTION = '#';
    private static final char NEW_WALL = 'O';
    private static final Character TRAVELED_SQUARE = '@';
    private static final char STARTING_SQUARE = '^';
    private static final char BACKGROUND_SYMBOL = '.';
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
    private static final Map<Character, TextColor> CHARACTER_COLORS = Map.of(
            BACKGROUND_SYMBOL, new TextColor.RGB(63, 63, 63),
            OBSTRUCTION, new TextColor.RGB(191, 0, 0),
            NEW_WALL, new TextColor.RGB(255, 128, 0),
            TRAVELED_SQUARE, new TextColor.RGB(0, 127, 255),
            STARTING_SQUARE, new TextColor.RGB(255, 255, 0));
    private static final TextColor CURRENT_SPOT = new TextColor.RGB(255, 255, 0);

    public static int countTraveledSquares(String fileName) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        int width = fileLines.getFirst().length();
        int height = fileLines.size();
        char[][] grid = readLinesIntoGrid(fileLines, width, height);
        populateVisitedPathSquares(grid, width, height, findStartPoint(grid, width, height));
        System.out.println(prettyPrint(grid));

        return countTraveledSquares(grid);
    }

    public static int countWaysToCauseInfiniteLoop(String fileName) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        int width = fileLines.getFirst().length();
        int height = fileLines.size();
        char[][] originalGrid = readLinesIntoGrid(fileLines, width, height);
        char[][] additionalObstructionLocations = copyGrid(originalGrid);

        CoordinatePair startPoint = findStartPoint(originalGrid, width, height);

        int blockingOptionCount = 0;
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (!startPoint.equals(new CoordinatePair(col, row))) {
                    char[][] gridWithAdditionalObstruction = copyGrid(originalGrid);
                    gridWithAdditionalObstruction[col][row] = OBSTRUCTION;
                    boolean hitAWall = populateVisitedPathSquares(gridWithAdditionalObstruction, width, height,
                            startPoint);
                    if (!hitAWall) {
                        blockingOptionCount++;
                        additionalObstructionLocations[col][row] = NEW_WALL;
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
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (grid[col][row] == STARTING_SQUARE) {
                    return new CoordinatePair(col, row);
                }
            }
        }
        return new CoordinatePair(-1, -1);
    }

    private static char[][] readLinesIntoGrid(List<String> fileLines, int width, int height) {
        char[][] grid = new char[width][height];
        for (int row = 0; row < height; row++) {
            String currentLine = fileLines.get(row);
            for (int col = 0; col < width; col++) {
                grid[col][row] = currentLine.charAt(col);
            }
        }
        return grid;
    }

    // true = hit a wall, false = infinite loop
    private static boolean populateVisitedPathSquares(char[][] grid, int width, int height, CoordinatePair currentPoint) throws IOException {
        CoordinatePair currentDirection = NORTH;

        /*Font font = new Font("Consolas", Font.BOLD, 30);
        Font font = new Font("Consolas", Font.BOLD, 6);
        AWTTerminalFontConfiguration fontConfiguration = AWTTerminalFontConfiguration.newInstance(font);
        AWTTerminalFrame terminal = new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF8")).setTerminalEmulatorFontConfiguration(fontConfiguration).createAWTTerminal();
        terminal.setMinimumSize(new Dimension(600, 1200));
        terminal.setCursorVisible(false);
        terminal.show();

        TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setForegroundColor(FOREGROUND_COLOR);*/
        Set<Pair<CoordinatePair, CoordinatePair>> visitedSquareDirections = new HashSet<>();
        while (true) {
            Pair<CoordinatePair, Boolean> nextPointAndIsFinished = travelToObstruction(grid, width, height, currentPoint, currentDirection/*, terminal, textGraphics*/);
            currentPoint = nextPointAndIsFinished.getLeft();
            boolean finished = nextPointAndIsFinished.getRight();
            if (finished) {
                return true;
            }
            Pair<CoordinatePair, CoordinatePair> squareDirection = Pair.of(currentPoint, currentDirection);
            if (visitedSquareDirections.contains(squareDirection)) {
                return false;
            }
            visitedSquareDirections.add(squareDirection);
            currentDirection = NEXT_DIRECTION.get(currentDirection);
        }
    }

    // true = hit a wall
    private static Pair<CoordinatePair, Boolean> travelToObstruction(char[][] grid, int width, int height,
                                                                     CoordinatePair currentPoint, CoordinatePair direction/*,
                                                                     Terminal terminal, TextGraphics textGraphics*/) throws IOException {
        while(true) {
            grid[currentPoint.x][currentPoint.y] = TRAVELED_SQUARE;
            CoordinatePair nextLocation = currentPoint.add(direction);

//            printToScreen(grid, width, height, currentPoint, terminal, textGraphics);
//            terminal.readInput();
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException ignored) {}
            if (isWallAdjacent(currentPoint, width, height)) {
                return Pair.of(currentPoint, true);
            }
            if (grid[nextLocation.x][nextLocation.y] == OBSTRUCTION) {
                 return Pair.of(currentPoint, false);
            }
            currentPoint = nextLocation;
        }
    }

    private static boolean isWallAdjacent(CoordinatePair point, int width, int height) {
        return point.x == 0 || point.y == 0 || point.x == width - 1 || point.y == height - 1;
    }

    private static String prettyPrint(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < grid[0].length; row++) {
            for (int col = 0; col < grid.length; col++) {
                sb.append(grid[col][row]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static void printToScreen(char[][] grid, int width, int height,
                                      CoordinatePair currentPoint,
                                      Terminal terminal, TextGraphics textGraphics) throws IOException {
        for (int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                textGraphics.setForegroundColor(CHARACTER_COLORS.get(grid[col][row]));
                textGraphics.setCharacter(col, row, grid[col][row]);
            }
        }
        textGraphics.setForegroundColor(CURRENT_SPOT);
        textGraphics.setCharacter(currentPoint.x, currentPoint.y, TRAVELED_SQUARE);
        terminal.flush();

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
