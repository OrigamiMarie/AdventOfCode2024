package net.origamimarie.adentofcode2024.util;

import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ListPairFileReader {

    public static void readListsFromResourceFile(String fileName, List<String> leftList, List<String> rightList) throws IOException {
        List<String> lines = IOUtils.readLines(new FileReader(Resources.getResource(fileName).getFile()));
        System.out.println(lines);
        for (String line : lines) {
            while (line.contains("  ")) {
                line = line.replaceAll("  ", " ");
            }
            String[] tokens = line.split(" ");
            leftList.add(tokens[0]);
            rightList.add(tokens[1]);
        }
    }
}
