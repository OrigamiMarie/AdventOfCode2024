package net.origamimarie.adentofcode2024;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public static int countSafeReports(String fileName, boolean dampen) throws IOException {
        List<String> lines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        int safeReportCount = 0;
        for (String report : lines) {
            List<Integer> numbers = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            boolean safeReport = isReportSafe(numbers);
            if (dampen) {
                for (int indexToRemove = 0; indexToRemove < numbers.size() && !safeReport; indexToRemove++) {
                    List<Integer> numbersCopy = new ArrayList<>(numbers);
                    numbersCopy.remove(indexToRemove);
                    safeReport = isReportSafe(numbersCopy);
                }
            }
            if (safeReport) {
                safeReportCount++;
            }
        }
        return safeReportCount;
    }

    private static boolean isReportSafe(List<Integer> numbers) {
        if (numbers.size() < 2) {
            return true;
        }
        if (numbers.get(0).equals(numbers.get(1))) {
            return false;
        }
        int reportDirection = (numbers.get(1) - numbers.get(0));
        reportDirection = reportDirection / Math.abs(reportDirection);
        for (int i = 0; i < numbers.size() - 1; i++) {
            int currentNumber = numbers.get(i);
            int nextNumber = numbers.get(i + 1);
            int absoluteDifference = Math.abs(currentNumber - nextNumber);
            if (absoluteDifference > 3 || absoluteDifference == 0) {
                return false;
            }
            int direction = (nextNumber - currentNumber) / absoluteDifference;
            if (direction != reportDirection) {
                return false;
            }
        }
        return true;
    }
}
