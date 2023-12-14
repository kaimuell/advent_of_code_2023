import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class Day14Test{

    @Test
    public void testDay() throws IOException {
        Day14 day = new Day14();
        Path path = new File("src/test/resources/day_14_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        assertEquals(day.rocks.length,10);
        day.shiftNorth();
        assertEquals('O', day.rocks[0][2]);
        utils.ArrayUtils.print(day.rocks);
        assertEquals(136, day.countWeight());

    }
}
