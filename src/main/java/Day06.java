import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static utils.ParsingUtils.tryToParseAllNumbersAsLong;


/**
 * Solves <a href="https://adventofcode.com/2023/day/6">Advent of Code Day 6</a>
 */

public class Day06 {
    public List<Long> times;
    public List<Long> distances;

    public static void main(String[] args) throws IOException {
        Day06 solver = new Day06();
        Path path = new File("src/main/resources/day_06_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);

        long result1 = solver.computeAllWinPossibilities();
        System.out.println("Day 6, Part 1 : " + result1);

        long result2 = solver.computePossibleWins(62737565L, 644102312401023L);
        System.out.println("Day 6, Part 2 : " + result2);
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        this.times = tryToParseAllNumbersAsLong(lines.get(0).split(" "));
        this.distances = tryToParseAllNumbersAsLong(lines.get(1).split(" "));
    }

    public long computePossibleWins(Long time, Long distance) {
        long wins = 0L;
        boolean reachedFirst = false;
        for (long i = 1; i < time; i++){
            long distanceTraveled = (time-i)*i;
            if (distanceTraveled > distance) {
                wins ++;
                reachedFirst = true;
            } else if (reachedFirst) {
                break;
            }
        }
        return wins;
    }

    public long computeAllWinPossibilities() {
        long possibilities = 1;
        for (int i=0; i < times.size(); i++){
            possibilities *= computePossibleWins(times.get(i), distances.get(i));
        }
        return possibilities;
    }
}
