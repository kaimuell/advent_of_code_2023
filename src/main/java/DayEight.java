import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * solves <a href="https://adventofcode.com/2023/day/8">Advent of Code 2023, Day 8</a>
 */

public class DayEight {
    String pathInstruction;
    Map<String, PossibleWays> wayMap;


    public static void main(String[] args) throws IOException {
        DayEight solver = new DayEight();
        Path path = new File("src/main/resources/day_eight_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);

        long result1 = solver.solvePartOne();
        System.out.println(result1);
    }

    public void parseInput(String content) {
        String[] s = content.split("\n\n");
        this.pathInstruction = s[0];
        this.wayMap = parseToMap(s[1]);
    }

    private Map<String,PossibleWays> parseToMap(String s) {
        Map<String,PossibleWays> map = new HashMap<>();
        s.lines().forEach(x-> {
            String[] parts = x.split(" = ");
            map.put(parts[0], PossibleWays.fromString(parts[1]));
        });
        return map;
    }

    public long solvePartOne() {
        long counter = 0L;
        String pos = "AAA";
        while (true){
            for (int i = 0; i< pathInstruction.length(); i++){
                if(pathInstruction.charAt(i) == 'L'){
                    pos = wayMap.get(pos).left();
                }else{
                    pos = wayMap.get(pos).right();
                }
                counter++;
                if (pos.equals("ZZZ")){
                    return counter;
                }
            }
        }
    }
}

record PossibleWays(String left, String right){
    public static PossibleWays fromString(String part) {
        String[] lr = part.split(", ");
        String l = lr[0].substring(1);
        String r = lr[1].substring(0, 3);
        return new PossibleWays(l,r);
    }
}
