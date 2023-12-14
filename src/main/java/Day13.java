import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Solves <a href="https://adventofcode.com/2023/day/13">Advent of Code Day 13</a>
 */
public class Day13 {
    public List<String>[] patterns;


    public static void main(String[] args) throws IOException {
        Day13 solver = new Day13();
        Path path = new File("src/main/resources/day_13_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        long result = solver.solvePartOne();
        System.out.println("Day 13, Part 1 : " + result);
    }

    public void parseInput(String content) {
        String[] arrays = content.split("\r\n\r\n");
        List<String>[] patterns = new List[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            String s = arrays[i];
            List<String> p = s.lines().toList();
            patterns[i] = p;
        }
        this.patterns = patterns;
    }

    public long solvePartOne(){
        long res = 0;
        for (var p : patterns){
            try{
                int vertline = findVerticalReflection(p);
                res += vertline;
            } catch (Exception e){
                res += findHorizontalReflection(p) * 100L;
            }
        }
        return res;
    }

    /**
     * Finds the number after which the pattern mirrors on the vertical axis
     *
     * @param pattern
     * @return the index of the reflection (from 1) or 0 if there is none
     */
    public int findVerticalReflection(List<String> pattern) {
        Set<Integer> possiblePoints = new HashSet<>();
        for (int i = 1; i < pattern.getFirst().length(); i++) {
            possiblePoints.add(i);
        }
        //filter for each row which point could be a reflection
        for (String x : pattern) {
            for (int i = 1; i < x.length(); i++) {
                if (possiblePoints.contains(i)) {
                    if (isNotReflectVertical(x, i)) possiblePoints.remove(i);
                }
            }
        }
        return possiblePoints.stream().reduce(Integer::min).orElseThrow();
    }

    private boolean isNotReflectVertical(String x, int i) {
        int offset = Math.min(i, x.length() - i);
        var left = x.substring(i - offset, i);
        var right = x.substring(i, i + offset);
        String rightReversed = new StringBuilder(right).reverse().toString();
        return !left.equals(rightReversed);
    }
    private boolean isInBounds(int index, String x) {
        return (index >= 0 && x.length() > index);
    }

    /**
     * Finds the number after which the pattern mirrors on the horizontal axis
     *
     * @param pattern
     * @return
     */
    public int findHorizontalReflection(List<String> pattern) {
        Set<Integer> possiblePoints = new HashSet<>();
        for (int i = 1; i < pattern.size(); i++) {
            possiblePoints.add(i);
        }
        //filter for each row which point could be a reflection
        for (int i = 1; i < pattern.size(); i++) {
            if (possiblePoints.contains(i)) {
                if (isNotReflectHorizontal(pattern, i)) possiblePoints.remove(i);
            }
        }
        return possiblePoints.stream().reduce(Integer::min).orElseThrow();
    }
    private boolean isNotReflectHorizontal(List<String> pattern, int index) {
        int offset = Math.min(index, pattern.size() - index -1);
        for (int i = 0; i < offset; i++) {
            int lowerIndex = index - 1 - i;
            int upperIndex = index + i;
            String lower = pattern.get(lowerIndex);
            String upper = pattern.get(upperIndex);
            if (! lower.equals(upper)) return true;
        }
        return false;
    }
}
