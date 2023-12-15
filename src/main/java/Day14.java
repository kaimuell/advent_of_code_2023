import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day14 {

    char[][] rocks;

    public static void main(String[] args) throws IOException {
        Day14 solver = new Day14();
        Path path = new File("src/main/resources/day_14_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        solver.shiftNorth();
        long result1 = solver.countWeight();
        System.out.println("Day 14, Part 1 : " + result1);
    }

    void parseInput(String content){
        List<String> lines = content.lines().toList();
        char[][] rocks = new char[lines.size()][lines.getFirst().length()];

        for (int i = 0 ; i < lines.size(); i++){
            rocks[i] = lines.get(i).toCharArray();
        }
        this.rocks= rocks;
    }

    public void shiftNorth() {
        for (int i = 1; i < rocks.length; i++){
            for (int j = 0 ; j < rocks[i].length; j++){
                if (rocks[i][j] == 'O') rollNorthTillObstacle(i, j);
            }
        }
    }

    private void rollNorthTillObstacle(int row, int col) {
        while (row > 0){
            if (rocks[row][col] != 'O') throw new IllegalStateException();
            if (rocks[row-1][col] == '.') {
                rocks[row-1][col] = 'O';
                rocks[row][col] = '.';
                row--;
            } else {
                return;
            }
        }
    }

    public long countWeight() {
        long result = 0;
        for (int row = 0; row< rocks.length; row++) {
            for (int col = 0; col < rocks[0].length; col++) {
                if (rocks[row][col] == 'O')  result += rocks.length  - row;
                //}
            }
        }
        return result;
    }
}
