import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class Day08Test {

    @Test
    public void testPartOne() throws IOException {
        Day08 day = new Day08();
        Path path = new File("src/test/resources/day_08_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        assertEquals(day.wayMap.get("AAA").left(), "BBB");
        assertEquals(day.pathInstruction, "LLR");

        assertEquals(day.solvePartOne(), 6L);

        assertEquals(6L, day.solvePartTwo());
    }
}
