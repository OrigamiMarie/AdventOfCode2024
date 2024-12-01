package net.origamimarie.adventofcode2024;

import net.origamimarie.adentofcode2024.Day01;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day01Test {

    @Test
    public void problem01() throws IOException {
        Assertions.assertEquals(1938424, Day01.totalDistanceBetweenNumberListsInFile("day01problemsInputList"));
    }

    @Test
    public void problem02() throws IOException {
        Assertions.assertEquals(22014209, Day01.multipliedSimilarityScore("day01problemsInputList"));
    }
}
