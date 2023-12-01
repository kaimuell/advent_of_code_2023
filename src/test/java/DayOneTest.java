import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DayOneTest {

    @Test
    public void testOneOne() throws IOException {
        DayOne day= new DayOne();
        Path path = new File("src/test/resources/day_one_test_input.txt").toPath();
        String content = Files.readString(path);
        List<Integer> numbers = day.filterDigitsAndConcatenateFirstAndLast(content);
        assertEquals(12, numbers.get(0).intValue());
        assertEquals(77, numbers.get(3).intValue());
        int result1 = day.sumNumbers(numbers);
        assertEquals(142, result1);
        assertEquals(result1, day.solveFirstQuest(path));
    }

    @Test
    public void testOneTwo() throws IOException {
        DayOne day= new DayOne();
        Path path = new File("src/test/resources/day_one_test_input2.txt").toPath();
        String content = Files.readString(path);
        List<Integer> extendedNumbers = day.filterDigitsAndConcatenateFirstAndLastExtended(content);
        assertEquals(29, extendedNumbers.get(0).intValue());
        int result = day.sumNumbers(extendedNumbers);
        assertEquals(281, result);
        assertEquals(result, day.solveSecondQuest(path));

    }
}
