import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * solves <a href="https://adventofcode.com/2023/day/8">Advent of Code 2023, Day 8</a>
 */

public class Day08 {
    String pathInstruction;
    Map<String, PossibleWays> wayMap;


    public static void main(String[] args) throws IOException {
        Day08 solver = new Day08();
        Path path = new File("src/main/resources/day_08_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);

        long result1 = solver.solvePartOne();
        System.out.println(result1);

        long result2 = solver.solvePartTwo();
        System.out.println(result2);
    }

    public void parseInput(String content) {
        String[] s = content.split("\n\n");
        this.pathInstruction = s[0];
        this.wayMap = parseToMap(s[1]);
    }

    private Map<String, PossibleWays> parseToMap(String s) {
        Map<String, PossibleWays> map = new HashMap<>();
        s.lines().forEach(x -> {
            String[] parts = x.split(" = ");
            map.put(parts[0], PossibleWays.fromString(parts[1]));
        });
        return map;
    }

    public long solvePartOne() {
        long counter = 0L;
        String pos = "AAA";
        while (true) {
            for (int i = 0; i < pathInstruction.length(); i++) {
                if (pathInstruction.charAt(i) == 'L') {
                    pos = wayMap.get(pos).left();
                } else {
                    pos = wayMap.get(pos).right();
                }
                counter++;
                if (pos.equals("ZZZ")) {
                    return counter;
                }
            }
        }
    }

    public long solvePartTwo() {
        List<String> startingPoints = wayMap.keySet().stream().filter(x -> x.endsWith("A")).toList();
        List<Long> stepsTakenFromStartingPoints = startingPoints.stream().map(this::takeGhostPaths).toList();
        return stepsTakenFromStartingPoints.stream().reduce(1L, Day08::leastCommonMultiple);
    }

    private Long takeGhostPaths(String x) {
        long counter = 0L;
        String pos = x;
        while (true) {
            for (int i = 0; i < pathInstruction.length(); i++) {
                if (pathInstruction.charAt(i) == 'L') {
                    pos = wayMap.get(pos).left();
                } else {
                    pos = wayMap.get(pos).right();
                }
                counter++;
                if (pos.endsWith("Z")) {
                    return counter;
                }
            }
        }
    }

    /**
     * Determines the least common multiple of 2 Numbers
     * <a href="http://www.programming-algorithms.net/article/42865/Least-common-multiple">Code Source</a>
     *
     * @param a the first number
     * @param b the second number
     * @return the least common multiple
     */
    public static long leastCommonMultiple(long a, long b) {
        if (a == 0L || b == 0L) return 0L;
        return (a * b) / greatestCommonDivisor(a, b);
    }

    public static long greatestCommonDivisor(long a, long b) {
        if (a < 1 || b < 1) {
            throw new IllegalArgumentException("a or b is less than 1");
        }
        long remainder;
        do {
            remainder = a % b;
            a = b;
            b = remainder;
        } while (b != 0);
        return a;
    }

    record PossibleWays(String left, String right) {
        public static PossibleWays fromString(String part) {
            String[] lr = part.split(", ");
            String l = lr[0].substring(1);
            String r = lr[1].substring(0, 3);
            return new PossibleWays(l, r);
        }
    }
}
