import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day15 {

    public static void main(String[] args) throws IOException {
        Day15 solver = new Day15();
        Path path = new File("src/main/resources/day_15_input.txt").toPath();
        String content = Files.readString(path);
        String[] inputs = solver.parseCSV(content);
        long res = solver.sumHashes(inputs);

        System.out.println(res);
    }


    public int hash(String input) {
        int res = 0;
        for (char c : input.toCharArray()){
            res = hashCharacter(res +c);
        }
        return res;
    }

    private int hashCharacter(int c) {
        long m = c * 17L;
        return (int) (m % 256);
    }

    public String[] parseCSV(String testInput) {
        return testInput.split(",");
    }

    public long sumHashes(String[] inputs) {
        return Arrays.stream(inputs).map(x -> (long) hash(x)).reduce(Long::sum).orElseThrow();
    }
}
