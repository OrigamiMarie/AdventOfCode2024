package net.origamimarie.adentofcode2024;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    public static int addMultiplesFromCorruptedMemory(String fileName, boolean excludeDonts) throws IOException {
        List<String> lines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        String data = String.join("\n", lines);
        if (excludeDonts) {
            data = excludeDonts(data);
        }
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\(\\d+?,\\d+?\\)");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        int total = 0;
        pattern = Pattern.compile("\\d+");
        for (String mulInstruction : matches) {
            matcher = pattern.matcher(mulInstruction);
            int leftNumber = 0;
            if (matcher.find()) {
                leftNumber = Integer.parseInt(matcher.group());
            }
            int rightNumber = 0;
            if (matcher.find()) {
                rightNumber = Integer.parseInt(matcher.group());
            }
            total += leftNumber * rightNumber;
        }
        return total;
    }

    private static String excludeDonts(String data) {
        StringBuilder dataSansExclusions = new StringBuilder();
        int substringStart = 0;
        int substringEnd = 0;
        while (substringStart != -1 && substringEnd != data.length()) {
            substringEnd = data.indexOf("don't()", substringStart);
            if (substringEnd == -1) {
                substringEnd = data.length();
            }
            dataSansExclusions.append(data, substringStart, substringEnd);
            if (substringEnd != data.length()) {
                substringStart = data.indexOf("do()", substringEnd);
            }
        }
        return dataSansExclusions.toString();
    }
}
