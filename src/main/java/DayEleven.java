import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Solves <a href="https://adventofcode.com/2023/day/11">Advent of Code 2023, Day 11</a>
 */
public class DayEleven {
    List<GalaxiePoint> galaxies = new ArrayList<>();
    Set<Integer> linesWithoutGalaxies = new HashSet<>();
    Set<Integer> colsWithoutGalaxies = new HashSet<>();

    public static void main(String[] args) throws IOException {
        DayEleven solver= new DayEleven();
        Path path = new File("src/main/resources/day_eleven_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        solver.expandGalaxies();
        long result1 = solver.sumAllManhattanDistances();
        System.out.println("Day 11, Part 1: " + result1);
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        for (int i = 0; i< lines.size();i++){
            String line = lines.get(i);
            boolean lineHasNoGalxies = true;
            for (int j = 0; j < line.length(); j++){
                if (line.charAt(j) == '#'){
                    this.galaxies.add(new GalaxiePoint(j,i));
                    lineHasNoGalxies = false;
                }
            }
            if (lineHasNoGalxies){
                this.linesWithoutGalaxies.add(i);
            }
        }
        findColsWithoutGalaxies(lines);
    }

    private void findColsWithoutGalaxies(List<String> lines) {
        for (int j = 0; j < lines.get(0).length(); j++) {
            boolean rowHasNoGalaxy = true;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).charAt(j) == '#') {
                    rowHasNoGalaxy = false;
                    break;
                }
            }
            if (rowHasNoGalaxy) {
                this.colsWithoutGalaxies.add(j);
            }
        }
    }

    public void expandGalaxies() {
        expandCols();
        expandRows();

    }

    private void expandRows() {
        for (int yPos : linesWithoutGalaxies) {
            for (var point: galaxies) {
                if (point.y > yPos) {
                    point.expandedY++;
                }
            }
        }
    }

    private void expandCols() {
        for (int xPos : colsWithoutGalaxies){
            for (var point : galaxies){
                if (point.x > xPos) point.expandedX++;
            }
        }
    }

    public long sumAllManhattanDistances() {
        long result = 0L;
        for (int i=0; i < galaxies.size(); i++){
            for (int j = i+1; j < galaxies.size(); j++){
                result += manhattandistance(galaxies.get(i), galaxies.get(j));
            }
        }
        return result;
    }

    public long manhattandistance(GalaxiePoint p1, GalaxiePoint p2) {
        return Math.abs(p1.getXAfterExpansion()-p2.getXAfterExpansion()) + Math.abs(p1.getYAfterExpansion()-p2.getYAfterExpansion());

    }

    public class GalaxiePoint extends Point{
        int expandedX = 0;
        int expandedY = 0;

        public GalaxiePoint(int x, int y) {
            super(x, y);
        }

        public int getXAfterExpansion() {
            return this.x + expandedX;
        }
        public int getYAfterExpansion(){
            return this.y + expandedY;
        }

    }
}
