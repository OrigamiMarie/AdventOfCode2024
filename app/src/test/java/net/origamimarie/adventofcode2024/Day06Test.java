package net.origamimarie.adventofcode2024;

import net.origamimarie.adentofcode2024.Day06;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day06Test {

    @Test
    public void problem01() throws IOException {
        Assertions.assertEquals(5409, Day06.countTraveledSquares("day06fullData"));
    }

    @Test
    public void problem02() throws IOException {
        // 1796 is too low
        Assertions.assertEquals(6, Day06.countWaysToCauseInfiniteLoop("day06fullData"));
    }
}
