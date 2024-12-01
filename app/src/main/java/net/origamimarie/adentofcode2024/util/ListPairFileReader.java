package net.origamimarie.adentofcode2024.util;

import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ListPairFileReader {

    public static void readListsFromResourceFile(String fileName, List<String> leftList, List<String> rightList) throws IOException {
        URL fileUrl = Resources.getResource(fileName);
        FileReader fileReader = new FileReader(fileUrl.getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            while (line.contains("  ")) {
                line = line.replaceAll("  ", " ");
            }
            String[] tokens = line.split(" ");
            leftList.add(tokens[0]);
            rightList.add(tokens[1]);
        }
    }
}
