import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class Day12Test {
    @Test
    public void testDay12() throws IOException {
        Day12 day = new Day12();
        Path path = new File("src/test/resources/day_12_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        assertEquals(6, day.springConditions.size());
        assertEquals(7, day.springConditions.getFirst().springs().length());
        assertEquals(3, day.springConditions.getFirst().sizes().size());
        assertTrue(day.checkIsValid("#.#.###", List.of(1,1,3)));
        assertTrue(day.checkIsValid(".#...#....###.", List.of(1,1,3)));
        assertFalse(day.checkIsValid(".#.###.#.####.#", List.of(1,3,1,6)));
        assertTrue(day.checkIsValid(".#.###.#.######", List.of(1,3,1,6)));
        assertTrue(day.checkIsValid("####.#...#...", List.of(4,1,1)));
        assertFalse(day.checkIsValid("####.#...#..#", List.of(4,1,1)));
        assertEquals(21, day.bruteForcePossibilities());
        Day12.SpringCondition sc = new Day12.SpringCondition("#.#.###", List.of(1,1,3));
        Day12.SpringCondition scuf = sc.toUnfolded();
        assertEquals(scuf.sizes().size(), 3*5);
        assertEquals(scuf.springs().length(), sc.springs().length()*5 +4);

        Day12.SpringCondition sc1 = new Day12.SpringCondition("???.###", List.of(1,1,3));
        Day12.SpringCondition sc1f = sc1.toUnfolded();
        assertEquals("???.###????.###????.###????.###????.###", sc1f.springs());
        assertEquals(List.of(1,1,3,1,1,3,1,1,3,1,1,3,1,1,3), sc1f.sizes());
        day.unfoldAllSpringConditions();
        assertEquals(525152, day.bfRecursive());
    }
}
