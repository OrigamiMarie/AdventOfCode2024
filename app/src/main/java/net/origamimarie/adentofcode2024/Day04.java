package net.origamimarie.adentofcode2024;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day04 {
    private static final char EMPTY_CHAR = Character.MIN_VALUE;

    public static int countXmas(String fileName) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        char[][] grid = new char[fileLines.size()][fileLines.getFirst().length()];
        int lineCount = fileLines.size();
        int lineLength = fileLines.getFirst().length();
        fillGrid(fileLines, grid, lineCount);

        int totalXmases = countXmasesInStrings(fileLines);

        List<String> verticalLines = new ArrayList<>();
        for (int j = 0; j < lineLength; j++) {
            StringBuilder verticalLine = new StringBuilder();
            for (int i = 0; i < lineCount; i++) {
                verticalLine.append(grid[i][j]);
            }
            verticalLines.add(verticalLine.toString());
        }
        totalXmases += countXmasesInStrings(verticalLines);

        totalXmases += countDiagonalXmas(grid, lineLength, lineCount);

        fillGrid(fileLines.reversed(), grid, lineCount);
        totalXmases += countDiagonalXmas(grid, lineLength, lineCount);

        return totalXmases;
    }

    private static int countDiagonalXmas(char[][] grid, int width, int height) {
        List<String> diagonalLines = new ArrayList<>();
        for (int iStart = 0; iStart < width; iStart++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; iStart + j < width; j++) {
                line.append(grid[iStart + j][j]);
            }
            diagonalLines.add(line.toString());
        }
        for (int jStart = 1; jStart < height; jStart++) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; jStart + i < height; i++) {
                line.append(grid[i][jStart + i]);
            }
            diagonalLines.add(line.toString());
        }
        return countXmasesInStrings(diagonalLines);
    }

    private static int countXmasesInStrings(List<String> lines) {
        int total = 0;
        for (String line : lines) {
            total += StringUtils.countMatches(line, "XMAS");
            total += StringUtils.countMatches(line, "SAMX");
        }
        return total;
    }

    public static int countXshapedMas(String fileName) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        char[][] grid = new char[fileLines.size()][fileLines.getFirst().length()];
        int lineCount = fileLines.size();
        int lineLength = fileLines.getFirst().length();
        System.out.println(lineCount + "x" + lineLength);
        fillGrid(fileLines, grid, lineCount);
        int totalXshapedMases = 0;
        List<char[][]> masks = new ArrayList<>();
        masks.add(new char[][] {{'M', EMPTY_CHAR, 'M'}, {EMPTY_CHAR, 'A', EMPTY_CHAR}, {'S', EMPTY_CHAR, 'S'}});
        masks.add(new char[][] {{'S', EMPTY_CHAR, 'M'}, {EMPTY_CHAR, 'A', EMPTY_CHAR}, {'S', EMPTY_CHAR, 'M'}});
        masks.add(new char[][] {{'S', EMPTY_CHAR, 'S'}, {EMPTY_CHAR, 'A', EMPTY_CHAR}, {'M', EMPTY_CHAR, 'M'}});
        masks.add(new char[][] {{'M', EMPTY_CHAR, 'S'}, {EMPTY_CHAR, 'A', EMPTY_CHAR}, {'M', EMPTY_CHAR, 'S'}});

        for (char[][] mask : masks) {
            totalXshapedMases += countMaskInGrid(grid, mask, lineLength, lineCount);
        }
        return totalXshapedMases;
    }

    private static int countMaskInGrid(char[][] grid, char[][] mask, int width, int height) {
        int maskWidth = mask.length;
        int maskHeight = mask[0].length;
        int matchCount = 0;
        int checkCount = 0;
        for (int i = 0; i < width + 1 - maskWidth; i++) {
            for (int j = 0; j < height + 1 - maskHeight; j++) {
                boolean matches = true;
                checkCount++;
                for (int maskI = 0; matches && maskI < maskWidth; maskI++) {
                    for (int maskJ = 0; matches && maskJ < maskHeight; maskJ++) {
                        if (mask[maskI][maskJ] != EMPTY_CHAR) {
                            matches = grid[i+maskI][j+maskJ] == mask[maskI][maskJ];
                        }
                    }
                }
                if (matches) {
                    matchCount++;
                }
            }
        }
        System.out.println(checkCount);
        return matchCount;
    }

    private static void fillGrid(List<String> lines, char[][] grid, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = lines.get(i).charAt(j);
            }
        }
    }

}
