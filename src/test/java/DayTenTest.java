import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DayTenTest {

    @Test
    public void testDayTen() throws IOException {
        DayTen d = new DayTen();
        Path path = new File("./src/test/resources/day_ten_test_input.txt").toPath();
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
