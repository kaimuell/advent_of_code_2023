import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DayFourTest {

    @Test
     public void testPartOne() throws IOException {
        DayFour day= new DayFour();
        Path path = new File("src/test/resources/day_four_test_input.txt").toPath();
        String content = Files.readString(path);
        List<DayFour.Card> cards = day.parseCards(content);
        assertEquals(6, cards.size());
        assertEquals(5, cards.getFirst().winningNumbers().size());
        assertEquals(8, cards.getFirst().numbers().size());
        int points = day.getPointsCount(cards);
        assertEquals(13, points);
     }

    @Test
    public void testPartTwo() throws IOException {
        DayFour day = new DayFour();
        Path path = new File("src/test/resources/day_four_test_input.txt").toPath();
        String content = Files.readString(path);
        List<DayFour.Card> cards = day.parseCards(content);
        int[] cardsWithCopies = day.createCopies(cards);
        assertEquals(1, cardsWithCopies[0]);
        assertEquals(2, cardsWithCopies[1]);
        assertEquals(4, cardsWithCopies[2]);
        assertEquals(1, cardsWithCopies[5]);

        int result = Arrays.stream(cardsWithCopies).sum();
        assertEquals(30, result);
    }
}
