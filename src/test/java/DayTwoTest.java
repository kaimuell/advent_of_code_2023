import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DayTwoTest {

    @Test
    public void testTwoOne() throws IOException {
        DayTwo d = new DayTwo();
        Path path = new File("src/test/resources/day_two_test_input.txt").toPath();
        String content = Files.readString(path);
        List<DayTwo.Game> games = d.parseInput(content);
        List<DayTwo.SetOfCubes> bounds = List.of(
                new DayTwo.SetOfCubes(12, "red"),
                new DayTwo.SetOfCubes(13, "green"),
                new DayTwo.SetOfCubes(14, "blue")
                );
        List<DayTwo.Game> possibleGames = games.stream().filter(x -> x.isPossible(bounds)).toList();
        assertEquals(3, possibleGames.size());
        int result1 = d.sumGameIds(possibleGames);
        assertEquals(8,result1);
    }

    @Test
    public void testTwoTwo() throws IOException {
        DayTwo d = new DayTwo();
        Path path = new File("src/test/resources/day_two_test_input.txt").toPath();
        String content = Files.readString(path);
        List<DayTwo.Game> games = d.parseInput(content);
        List<Integer> powers = games.stream().map(DayTwo.Game::determinePower).toList();
        assertEquals(48, powers.getFirst().intValue());
        assertEquals(36, powers.getLast().intValue());
        int power = powers.stream().reduce(0, Integer::sum);
        assertEquals(2286, power);
    }

}
