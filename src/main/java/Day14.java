import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

    char[][] rocks;
    Map<String, Integer> states = new HashMap<>();

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

    public void rollCycles(long cycles){
        for (long i = 0; i< cycles; i++){
            shiftNorth();
            shiftWest();
            shiftSouth();
            shiftEast();
            String s = utils.ArrayUtils.toString(rocks);
            if (states.get(s) != null) {
                System.out.println("FOUND STATE " + states.get(s));
                break;
            }

        }
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

    private void shiftSouth() {
        int l = rocks.length;
        for (int i = rocks.length -2; i >= 0; i--){
            for (int j = 0 ; j < rocks[i].length; j++){
                if (rocks[i][j] == 'O') rollSouthTillObstacle(i, j);
            }
        }
    }

    private void shiftWest() {
        for (int j = 1 ; j < rocks[0].length; j++){
            for (int i = 0; i < rocks.length; i++){
                if (rocks[i][j] == 'O') rollWestTillObstacle(i, j);
            }
        }
    }

    private void rollWestTillObstacle(int row, int col) {
        while (col > 0){
            if (rocks[row][col] != 'O') throw new IllegalStateException();
            if (rocks[row][col-1] == '.') {
                rocks[row][col-1] = 'O';
                rocks[row][col] = '.';
                col--;
            } else {
                return;
            }
        }
    }


    private void rollSouthTillObstacle(int row, int col) {
        while (row < rocks.length-1){
            if (rocks[row][col] != 'O') throw new IllegalStateException();
            if (rocks[row+1][col] == '.') {
                rocks[row+1][col] = 'O';
                rocks[row][col] = '.';
                row++;
            } else {
                return;
            }
        }
    }

    public void shiftEast() {
        for (int j = rocks[0].length -2  ; j >= 0; j--){
            for (int i = 1; i < rocks.length; i++){
                if (rocks[i][j] == 'O') rollEastTillObstacle(i, j);
            }
        }
    }

    private void rollEastTillObstacle(int row, int col) {
        while (col < rocks[0].length-1){
            if (rocks[row][col] != 'O') throw new IllegalStateException();
            if (rocks[row][col+1] == '.') {
                rocks[row][col+1] = 'O';
                rocks[row][col] = '.';
                col++;
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
            }
        }
        return result;
    }
}
