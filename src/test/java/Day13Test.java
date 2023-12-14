import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class Day13Test {

    @Test
    public void testDay13() throws IOException {
        Day13 day = new Day13();
        Path path = new File("src/test/resources/day_13_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        assertEquals(2, day.patterns.length);
        assertEquals(5, day.findVerticalReflection(day.patterns[0]));
        assertEquals(4, day.findHorizontalReflection(day.patterns[1]));
        assertEquals(405L, day.solvePartOne());
    }
}
