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
public class Day11 {
    List<GalaxiePoint> galaxies = new ArrayList<>();
    Set<Integer> linesWithoutGalaxies = new HashSet<>();
    Set<Integer> colsWithoutGalaxies = new HashSet<>();

    public static void main(String[] args) throws IOException {
        Day11 solver= new Day11();
        Path path = new File("src/main/resources/day_11_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        solver.expandGalaxies();
        long result1 = solver.sumAllManhattanDistances();
        System.out.println("Day 11, Part 1: " + result1);
        long result2 = solver.sumAllManhattanDistancesExpansion(1000000L);
        System.out.println("Day 11, Part 2 " + result2);
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        for (int i = 0; i< lines.size();i++){
            String line = lines.get(i);
            boolean lineHasNoGalxies = true;
            for (int j = 0; j < line.length(); j++){
                if (line.charAt(j) == '#'){
                    this.galaxies.add(new GalaxiePoint(j, i));
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
            for (String line : lines) {
                if (line.charAt(j) == '#') {
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
        return sumAllManhattanDistancesExpansion(2L);
    }
    public long sumAllManhattanDistancesExpansion(long offset) {
        long result = 0L;
        for (int i=0; i < galaxies.size(); i++){
            for (int j = i+1; j < galaxies.size(); j++){
                result += manhattanDistance(galaxies.get(i), galaxies.get(j), offset);
            }
        }
        return result;
    }


    public long manhattanDistance(GalaxiePoint p1, GalaxiePoint p2, long offset) {
        return Math.abs(p1.getXAfterExpansion(offset)-p2.getXAfterExpansion(offset))
                + Math.abs(p1.getYAfterExpansion(offset)-p2.getYAfterExpansion(offset));
    }

    public static class GalaxiePoint extends Point{
        long expandedX = 0;
        long expandedY = 0;

        public GalaxiePoint(int x, int y) {
            super(x, y);
        }

        public long getXAfterExpansion(long offset) {
            return (long) this.x + (expandedX * (offset-1));
        }
        public long getYAfterExpansion(Long offset){
            return (long) this.y + (expandedY * (offset-1));
        }
    }
}
