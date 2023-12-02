import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DayTwo {

    public static void main(String[] args) throws IOException {
        DayTwo solver = new DayTwo();
        Path path = new File("src/main/resources/day_two_input.txt").toPath();
        String content = Files.readString(path);
        int result1 = solver.solvePartOne(content);
        System.out.println("Day 2, Part 1 : " + result1);

        int result2 = solver.solvePartTwo(content);
        System.out.println("Day 2, Part 2 : " + result2);
    }

    private int solvePartOne(String content) {
        List<DayTwo.Game> games = parseInput(content);
        List<DayTwo.SetOfCubes> bounds = List.of(
                new DayTwo.SetOfCubes(12, "red"),
                new DayTwo.SetOfCubes(13, "green"),
                new DayTwo.SetOfCubes(14, "blue")
        );
        List<DayTwo.Game> possibleGames = games.stream().filter(x -> x.isPossible(bounds)).toList();
        return sumGameIds(possibleGames);
    }

    private int solvePartTwo(String content) {
        List<DayTwo.Game> games = parseInput(content);
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

        public int determinePower() {
            Map<String, Integer> minimalSet = findMinimalSet();
            int result = 1;
            for (var x : minimalSet.values()) result *= x;
            return result;
        }

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
