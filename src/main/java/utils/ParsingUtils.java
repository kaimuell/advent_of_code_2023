package utils;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    public static List<Long> tryToParseAllNumbers(String[] s) {

        List<Long> numbers = new ArrayList<>();
        for (String x : s) {
            try {
                long n = Long.parseLong(x);
                numbers.add(n);
            } catch (Exception e) {}
        }
        return numbers;
    }
}
