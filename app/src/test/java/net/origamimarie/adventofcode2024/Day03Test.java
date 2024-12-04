package net.origamimarie.adventofcode2024;

import net.origamimarie.adentofcode2024.Day03;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Day03Test {

    @Test
    public void problem01() throws IOException {
        Assertions.assertEquals(165225049, Day03.addMultiplesFromCorruptedMemory("day03fullData", false));
    }

    @Test
    public void problem02() throws IOException {
        Assertions.assertEquals(108830766, Day03.addMultiplesFromCorruptedMemory("day03fullData", true));
    }
}
