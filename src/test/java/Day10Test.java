import org.junit.Test;
import utils.Direction;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class Day10Test {

    @Test
    public void testDayTen() throws IOException {
        Day10 d = new Day10();
        Path path = new File("./src/test/resources/day_10_test_input.txt").toPath();
        String content = Files.readString(path);
        d.parseInput(content);
        assertEquals(d.startPos, new Point(0,2));
        assertNotNull(d.pipes);
        assertEquals(d.pipes.length, 5);
        assertEquals(d.pipes[0].length, 5);
        assertTrue(d.pipes[d.startPos.x][d.startPos.y].directions.contains(Direction.SOUTH));
        assertTrue(d.pipes[d.startPos.x][d.startPos.y].directions.contains(Direction.EAST));
        d.breathFirstSearch();
        long result = d.getAllPipesAsList().stream().reduce(0L, Long::max);
        assertEquals(8, result);
    }
}
