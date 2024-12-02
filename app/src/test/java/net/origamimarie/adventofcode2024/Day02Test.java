package net.origamimarie.adventofcode2024;

import net.origamimarie.adentofcode2024.Day02;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day02Test {

    @Test
    public void problem01() throws IOException {
        Assertions.assertEquals(591, Day02.countSafeReports("day02fullData", false));
    }

    @Test
    public void problem02() throws IOException {
        Assertions.assertEquals(621, Day02.countSafeReports("day02fullData", true));
    }
}
