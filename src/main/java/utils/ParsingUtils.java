package utils;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    public static List<Long> tryToParseAllNumbersAsLong(String[] s) {

        List<Long> numbers = new ArrayList<>();
        for (String x : s) {
            try {
                long n = Long.parseLong(x);
                numbers.add(n);
            } catch (Exception e) {}
        }
        return numbers;
    }

    public static List<Integer> tryToParseAllNumbersAsInteger(String[] s) {

        List<Integer> numbers = new ArrayList<>();
        for (String x : s) {
            try {
                int n = Integer.parseInt(x);
                numbers.add(n);
            } catch (Exception e) {}
        }
        return numbers;
    }
}
