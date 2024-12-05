package net.origamimarie.adentofcode2024;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day05 {

    public static int sumMidPagesOfErrata(String fileName, boolean sumCorrectErrata) throws IOException {
        List<String> fileLines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        List<Pair<Integer, Integer>> pageOrderPairs = new ArrayList<>();
        List<List<Integer>> erratumOrders = new ArrayList<>();
        for (String line : fileLines) {
            if (line.isBlank()) {
                continue;
            }
            if (line.contains("|")) {
                String[] pages = line.split("\\|");
                pageOrderPairs.add(Pair.of(Integer.parseInt(pages[0]), Integer.parseInt(pages[1])));
            } else if (line.contains(",")) {
                erratumOrders.add(new ArrayList<>(Arrays.stream(line.split(",")).map(Integer::parseInt).toList()));
            }
        }
        Map<Integer, List<Integer>> pageOrderBeforeToAfter = new HashMap<>();
        for (Pair<Integer, Integer> pair : pageOrderPairs) {
            pageOrderBeforeToAfter.computeIfAbsent(pair.getLeft(), k -> new ArrayList<>());
            pageOrderBeforeToAfter.get(pair.getLeft()).add(pair.getRight());
        }
        int goodMiddlePageTotal = 0;
        List<List<Integer>> badErratumOrders = new ArrayList<>();
        for (List<Integer> erratum : erratumOrders) {
            if (erratumIsGoodOrAddToList(erratum, badErratumOrders, pageOrderBeforeToAfter)) {
                int middlePage = erratum.get(erratum.size() / 2);
                goodMiddlePageTotal += middlePage;
            }
        }
        if (sumCorrectErrata) {
            return goodMiddlePageTotal;
        }

        int correctedMiddlePageTotal = 0;
        List<List<Integer>> stillBadErrata = new ArrayList<>();
        for (List<Integer> badErratum : badErratumOrders) {
            List<Integer> sortedInput = new ArrayList<>(badErratum);
            Collections.sort(sortedInput);
            performOneBubbleSortPass(badErratum, pageOrderBeforeToAfter);
            if (!erratumIsGoodOrAddToList(badErratum, stillBadErrata, pageOrderBeforeToAfter)) {
                System.out.println("Bad erratum: " + Arrays.toString(badErratum.toArray()));
            }
            List<Integer> sortedOutput = new ArrayList<>(badErratum);
            Collections.sort(sortedOutput);
            correctedMiddlePageTotal += badErratum.get(badErratum.size() / 2);
            if (!sortedInput.equals(sortedOutput)) {
                System.out.println("woops");
            }
        }
        return correctedMiddlePageTotal;
    }

    private static boolean erratumIsGoodOrAddToList(List<Integer> erratum, List<List<Integer>> badErrata, Map<Integer, List<Integer>> pageOrderBeforeToAfter) {
        Set<Integer> seenPages = new HashSet<>();
        for (Integer page : erratum) {
            List<Integer> pagesThatShouldBeAfter = pageOrderBeforeToAfter.get(page);
            seenPages.add(page);
            if (pagesThatShouldBeAfter != null) {
                for (Integer p : pagesThatShouldBeAfter) {
                    if (seenPages.contains(p)) {
                        badErrata.add(erratum);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void performOneBubbleSortPass(List<Integer> erratum, Map<Integer, List<Integer>> pageOrderBeforeToAfter) {
        for (int i = 0; i < erratum.size(); i++) {
            int page = erratum.get(i);
            List<Integer> pagesThatShouldBeAfter = pageOrderBeforeToAfter.get(page);
            if (pagesThatShouldBeAfter != null) {
                for (int j = 0; j < i; j++) {
                    if (pagesThatShouldBeAfter.contains(erratum.get(j))) {
                        erratum.remove(i);
                        erratum.add(j, page);
                        break;
                    }
                }
            }
        }
    }
}










