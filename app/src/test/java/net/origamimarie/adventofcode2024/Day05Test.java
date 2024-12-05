package net.origamimarie.adventofcode2024;

import net.origamimarie.adentofcode2024.Day05;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day05Test {

    @Test
    public void problem01() throws IOException {
        Assertions.assertEquals(5208, Day05.sumMidPagesOfErrata("day05fullData", true));
    }

    @Test
    public void problem02() throws IOException {
        Assertions.assertEquals(6732, Day05.sumMidPagesOfErrata("day05fullData", false));
    }
}
