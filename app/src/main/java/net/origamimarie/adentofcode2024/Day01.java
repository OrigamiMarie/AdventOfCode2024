package net.origamimarie.adentofcode2024;

import net.origamimarie.adentofcode2024.util.ListPairFileReader;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 {

    public static long totalDistanceBetweenNumberListsInFile(String fileName) throws IOException {
        Pair<List<Integer>, List<Integer>> pairOfLists = sortedListsFromFile(fileName);
        List<Integer> leftNumbers = pairOfLists.getLeft();
        List<Integer> rightNumbers = pairOfLists.getRight();
        long totalDifference = 0;
        for (int i = 0; i < leftNumbers.size(); i++) {
            totalDifference += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
        }
        return totalDifference;
    }

    public static long multipliedSimilarityScore(String fileName) throws IOException {
        Pair<List<Integer>, List<Integer>> pairOfLists = sortedListsFromFile(fileName);
        List<Integer> leftList = pairOfLists.getLeft();
        List<Integer> rightList = pairOfLists.getRight();
        Map<Integer, Integer> rightListCounts = new HashMap<>();
        for (int number : rightList) {
            rightListCounts.put(number, rightListCounts.getOrDefault(number, 0) + 1);
        }
        long totalDifference = 0;
        for (int number : leftList) {
            totalDifference += number * rightListCounts.getOrDefault(number, 0);
        }
        return totalDifference;
    }

    private static Pair<List<Integer>, List<Integer>> sortedListsFromFile(String fileName) throws IOException {
        List<String> leftList = new ArrayList<>();
        List<String> rightList = new ArrayList<>();
        ListPairFileReader.readListsFromResourceFile(fileName, leftList, rightList);
        List<Integer> leftNumbers = new ArrayList<>(leftList.stream().map(Integer::parseInt).toList());
        List<Integer> rightNumbers = new ArrayList<>(rightList.stream().map(Integer::parseInt).toList());
        Collections.sort(leftNumbers);
        Collections.sort(rightNumbers);
        return Pair.of(leftNumbers, rightNumbers);
    }
}
