import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day02 {

    public static void main(String[] args) throws IOException {
        Day02 solver = new Day02();
        Path path = new File("src/main/resources/day_02_input.txt").toPath();
        String content = Files.readString(path);
        int result1 = solver.solvePartOne(content);
        System.out.println("Day 2, Part 1 : " + result1);

        int result2 = solver.solvePartTwo(content);
        System.out.println("Day 2, Part 2 : " + result2);
    }

    /**
     * Solves https://adventofcode.com/2023/day/2#part1
     * @param content the puzzle input
     * @return the result
     */
    private int solvePartOne(String content) {
        List<Day02.Game> games = parseInput(content);
        List<Day02.SetOfCubes> bounds = List.of(
                new Day02.SetOfCubes(12, "red"),
                new Day02.SetOfCubes(13, "green"),
                new Day02.SetOfCubes(14, "blue")
        );
        List<Day02.Game> possibleGames = games.stream().filter(x -> x.isPossible(bounds)).toList();
        return sumGameIds(possibleGames);
    }
    /**
     * Solves https://adventofcode.com/2023/day/2#part2
     * @param content the puzzle input
     * @return the result
     */
    private int solvePartTwo(String content) {
        List<Day02.Game> games = parseInput(content);
        return games.stream().map(Game::determinePower).reduce(0, Integer::sum);
    }


    public List<Game> parseInput(String content) {
        List<String> lines = content.lines().toList();

        List<Game> games = new ArrayList<>();
        for (String line : lines){
            List<List<SetOfCubes>> setOfCubes = new ArrayList<>();
            String[] parts = line.split(":");
            int gameindex = Integer.parseInt(parts[0].split(" ")[1]);
            String[] plays = parts[1].split(";");
            for (String x: plays){
                String[] p = x.split(",");
                List<SetOfCubes> cubes = new ArrayList<>();
                for (String y : p) cubes.add(detemineSetofCubes(y));
                setOfCubes.add(cubes);
            }
            games.add(new Game(gameindex, setOfCubes));
            }
            return games;
        }

    private SetOfCubes detemineSetofCubes(String s) {
        String[] pair = s.split(" ");
        int n = Integer.parseInt(pair[1].trim());
        String color = pair[2].trim();
        return new SetOfCubes(n, color);
    }

    public int sumGameIds(List<Game> possibleGames) {
        return possibleGames.stream().map(Game::id).reduce(0, Integer::sum);
    }

    record SetOfCubes(int count, String color){}

    public record Game(int id, List<List<SetOfCubes>> plays) {
        public boolean isPossible(List<SetOfCubes> bounds) {
            for (var b : bounds){
                for (List<SetOfCubes> cubes : this.plays){
                    int sumOfColor = cubes.stream()
                            .filter(x-> x.color.equals(b.color))
                            .map(SetOfCubes::count)
                            .reduce(0, Integer::sum);
                    if (sumOfColor > b.count()) return false;
                }
            }
            return true;
        }

        /**
         * determines the Power of the Minimal Set of Cubes
         * @return the power
         */
        public int determinePower() {
            Map<String, Integer> minimalSet = findMinimalSet();
            int result = 1;
            for (var x : minimalSet.values()) result *= x;
            return result;
        }

        /**
         * finds the minimal Set of cubes for the game
         * @return tthe minimal set
         */
        private Map<String, Integer> findMinimalSet() {
            Map<String, Integer> minColors = new HashMap<>();
            for (var p : plays){
                for (SetOfCubes c : p){
                    if (!minColors.containsKey(c.color()) || minColors.get(c.color) < c.count()){
                            minColors.put(c.color(), c.count());

                    }
                }
            }
            return minColors;
        }
    }

}
