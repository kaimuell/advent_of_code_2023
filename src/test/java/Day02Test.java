import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day02Test {

    @Test
    public void testTwoOne() throws IOException {
        Day02 d = new Day02();
        Path path = new File("src/test/resources/day_02_test_input.txt").toPath();
        String content = Files.readString(path);
        List<Day02.Game> games = d.parseInput(content);
        List<Day02.SetOfCubes> bounds = List.of(
                new Day02.SetOfCubes(12, "red"),
                new Day02.SetOfCubes(13, "green"),
                new Day02.SetOfCubes(14, "blue")
                );
        List<Day02.Game> possibleGames = games.stream().filter(x -> x.isPossible(bounds)).toList();
        assertEquals(3, possibleGames.size());
        int result1 = d.sumGameIds(possibleGames);
        assertEquals(8,result1);
    }

    @Test
    public void testTwoTwo() throws IOException {
        Day02 d = new Day02();
        Path path = new File("src/test/resources/day_02_test_input.txt").toPath();
        String content = Files.readString(path);
        List<Day02.Game> games = d.parseInput(content);
        List<Integer> powers = games.stream().map(Day02.Game::determinePower).toList();
        assertEquals(48, powers.getFirst().intValue());
        assertEquals(36, powers.getLast().intValue());
        int power = powers.stream().reduce(0, Integer::sum);
        assertEquals(2286, power);
    }

}
