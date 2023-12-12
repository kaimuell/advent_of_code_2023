import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day06Test {

    @Test
    public void testPartOne() throws IOException {
        Day06 day = new Day06();
        Path path = new File("src/test/resources/day_06_test_input.txt").toPath();
        String content = Files.readString(path);
        day.parseInput(content);
        Assertions.assertEquals(3, day.distances.size());
        Assertions.assertEquals(3, day.times.size());
        long wins = day.computePossibleWins(day.times.get(0), day.distances.get(0));
        Assertions.assertEquals(4L, wins);
        long winPossibilities = day.computeAllWinPossibilities();
        Assertions.assertEquals(288, winPossibilities);

        long p2 = day.computePossibleWins(71530L, 940200L);
        Assertions.assertEquals(71503L, p2);
    }
}
