package net.origamimarie.adventofcode2024;

import net.origamimarie.adentofcode2024.Day04;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day04Test {

    @Test
    public void problem01() throws IOException {
        Assertions.assertEquals(2447, Day04.countXmas("day04fullData"));
    }

    @Test
    public void problem02() throws IOException {
        // more than 1825
        Assertions.assertEquals(1868, Day04.countXshapedMas("day04fullData"));
    }
}
