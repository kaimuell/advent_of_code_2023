import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class Day20Test {

    @Test
    public void testDay20() throws IOException {
        Day20 d = new Day20();
        Path path = new File("src/test/resources/day_20_test_input.txt").toPath();
        String content = Files.readString(path);
        d.parseInput(content);
        d.button();
        assertEquals(8, d.counter.low);
        assertEquals(4, d.counter.high);
        d.counter.reset();
        d.pushButtonXTimes(1000);
        assertEquals(32000000L, d.counter.high*d.counter.low);



        d = new Day20();
        path = new File("src/test/resources/day_20_test_input2.txt").toPath();
        content = Files.readString(path);
        d.parseInput(content);
        assertEquals(((Day20.Conjuction) d.elementMap.get("con")).inputs.values().size(), 2);
        d.pushButtonXTimes(1000);
        assertEquals(4250L, d.counter.low);
        assertEquals(2750L, d.counter.high);
        assertEquals(11687500L, d.counter.high*d.counter.low);


    }
}
