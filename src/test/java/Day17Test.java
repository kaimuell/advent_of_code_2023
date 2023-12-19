import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class Day17Test {

    @Test
    public void testDay17() throws IOException {
        Day17 d = new Day17();
        Path path = new File("src/test/resources/day_17_test_input.txt").toPath();
        String content = Files.readString(path);
        assertEquals(5, Character.getNumericValue('5'));
        d.parseInput(content);
        assertEquals(102, d.walkThroughGrid(3,1));

        d.parseInput(content);
        assertEquals(94, d.walkThroughGrid(10,4));
    }
}