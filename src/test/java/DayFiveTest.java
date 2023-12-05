import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DayFiveTest {
    @Test
    public void testPartOne() throws IOException {
        DayFive day= new DayFive();
        Path path = new File("src/test/resources/day_five_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);

        assertEquals(4, day.seeds.size());
        assertEquals(2, day.maps.getFirst().size());
        DayFive.MapEntry firstMap = day.maps.getFirst().getFirst();

        assert (10L == firstMap.mapNumber(10L));
        assert (50L ==  firstMap.mapNumber(98L));
        assert (51L == firstMap.mapNumber(99L));
        assert (52L == firstMap.mapNumber(100L));
        assert (101L== firstMap.mapNumber(101L));

        Optional<Long> result = day.findClosestLocation();
        assertTrue(result.isPresent());
        assertEquals(35, result.get().intValue());
    }
}
