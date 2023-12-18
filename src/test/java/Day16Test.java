import org.junit.Test;
import utils.Direction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Day16Test {

    @Test
    public void testDay16() throws IOException {
        Day16 day = new Day16();
        Path path = new File("src/test/resources/day_16_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        assertFalse(day.illuminated[0][0]);
        day.illuminateFrom(0,0, Direction.EAST);
        System.out.println(utils.ArrayUtils.toString(day.illuminated));
        assertEquals(46L, day.countIlluminatedTiles());

        day.parseInput(content);
        day.illuminateFrom(0,3,Direction.SOUTH);
        assertEquals(51L, day.countIlluminatedTiles());
        assertEquals(51L, day.maxOfAllConfigurations());
    }
}
