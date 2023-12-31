import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Day11Test {
    @Test
    public void testDay() throws IOException {

        Day11 day = new Day11();
        Path path = new File("src/test/resources/day_11_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        assertTrue(day.galaxies.contains(new Point(0,2)));
        assertTrue(day.galaxies.contains(new Point(1,5)));
        assertTrue (day.colsWithoutGalaxies.contains(2));
        assertTrue (day.linesWithoutGalaxies.contains(3));
        day.expandGalaxies();
        assertEquals(374, day.sumAllManhattanDistances());
        assertEquals(1030, day.sumAllManhattanDistancesExpansion(10L));
        assertEquals(8410, day.sumAllManhattanDistancesExpansion(100L));
    }
}
