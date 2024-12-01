package net.origamimarie.adventofcode2024.util;

import net.origamimarie.adentofcode2024.util.ListPairFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListPairFileReaderTest {

    @Test
    public void testFileReader() throws IOException {
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();
        ListPairFileReader.readListsFromResourceFile("fileReaderTest", left, right);
        Assertions.assertLinesMatch(List.of("3", "4", "2", "1", "3", "3"), left);
        Assertions.assertLinesMatch(List.of("4", "3", "5", "3", "9", "3"), right);
    }
}
