import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Solves <a href="https://adventofcode.com/2023/day/15">Advent of Code 2023, Day 15</a>
 */

public class Day15Test {

    private final String HASH_TESTSTRING = "HASH";
    private final String TEST_INPUT = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

    @Test
    public void testDay15(){
        Day15 d = new Day15();
        assertEquals(72, HASH_TESTSTRING.charAt(0));
        long hash = d.hash(HASH_TESTSTRING);
        assertEquals(52, hash);

        String[] inputs = d.parseCSV(TEST_INPUT);
        assertEquals(11, inputs.length);

        long res = d.sumHashes(inputs);
        assertEquals(1320, res);
    }
}
