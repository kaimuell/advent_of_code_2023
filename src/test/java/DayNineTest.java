import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class DayNineTest {

    @Test
    public void testPartOne() throws IOException {
        DayNine d = new DayNine();
        Path path = new File("src/test/resources/day_nine_test_input.txt").toPath();
        String content = Files.readString(path);
        d.parseInput(content);

        assertEquals(d.readings.size(), 3);
        assertEquals(d.readings.getFirst().sequences.getFirst().size(), 6);
        assertEquals(d.readings.getFirst().sequences.getFirst().getLast().intValue(),
                15L);

        for (var x: d.readings){
            x.expand();
        }
        assertEquals(d.readings.getFirst().sequences.size(), 3);
        assertEquals(d.readings.getFirst().sequences.getFirst().getLast().intValue(),
                18L);
        assertEquals(d.readings.getLast().sequences.getFirst().getLast().intValue(),
                68L);

        assertEquals(114L , d.sumLastNumbers().longValue());
        assertEquals(d.readings.getFirst().sequences.getFirst().getFirst().longValue()
                , -3L);

        assertEquals(2, d.sumFirstNumbers());
    }
}
