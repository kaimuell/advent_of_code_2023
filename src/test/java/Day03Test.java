import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class Day03Test {

    @Test
    public void testThreeOne() throws IOException {
        Day03 d = new Day03();
        Path path = new File("./src/test/resources/day_03_test_input.txt").toPath();
        String content = Files.readString(path);
        Day03.EngineSchemaElement[][] engineSchema = d.parseEngineSchema(content);
        assertEquals(4, ((Day03.ESNumber)engineSchema[0][0]).value);
        assertFalse (engineSchema[1][0] instanceof Day03.ESSymbol );
        assertTrue(engineSchema[1][0] instanceof Day03.ESNull);
        List<Day03.NumberAndPosition> numbersWithAdjacentSymbols = d.determineNumbersWithAdjacentSymbols(engineSchema);
        assertEquals(8, numbersWithAdjacentSymbols.size());
        assertEquals(4361, d.sumResult(numbersWithAdjacentSymbols));

        assertEquals(4361, d.solvePartOne(content));
    }

    @Test
    public void testThreeTwo() throws IOException {
        Day03 d = new Day03();
        Path path = new File("./src/test/resources/day_03_test_input.txt").toPath();
        String content = Files.readString(path);
        Day03.EngineSchemaElement[][] engineSchema = d.parseEngineSchema(content);
        List<Day03.NumberAndPosition> numbersWithAdjacentSymbols = d.determineNumbersWithAdjacentSymbols(engineSchema);
        List<Integer> ratios = d.checkGearRatios(engineSchema, numbersWithAdjacentSymbols);
        assertEquals(2, ratios.size());
        int result = ratios.stream().reduce(0, Integer::sum);
        assertEquals(467835, result);

    }
}
