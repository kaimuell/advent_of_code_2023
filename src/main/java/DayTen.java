import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

import static utils.ArrayUtils.isInBounds;

/**
 * solves <a href="https://adventofcode.com/2023/day/10">Advent of Code Day 10</a>
 */

public class DayTen {
    Point startPos;
    PipeTile[][] pipes;
    long[][] costs;

    public static void main(String[] args) throws IOException {
        DayTen solver = new DayTen();
        Path input = new File("src/main/resources/day_ten_input.txt").toPath();
        String content = Files.readString(input);
        solver.parseInput(content);
        solver.breathFirstSearch();
        long result1 = solver.getAllPipesAsList().stream().reduce(0L, Long::max);

        System.out.println("Day 10, Part1 " + result1);
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        PipeTile[][] pipes = new PipeTile[lines.getFirst().length()][lines.getFirst().length()];

        for (int i=0; i < lines.size(); i++){
            for (int j = 0; j < lines.get(i).length(); j++){
                if (lines.get(i).charAt(j) == 'S') this.startPos = new Point(j,i);
                pipes[j][i] = PipeTile.fromChar(lines.get(i).charAt(j),j,i);
            }
        }
        initStartPoint(pipes);
        this.pipes = pipes;
        this.costs = initCostArray(pipes.length, pipes[0].length);
    }

    private long[][] initCostArray(int length, int length1) {
        long[][] costs = new long[length][length1];
        for (int i = 0; i < costs.length; i++){
            Arrays.fill(costs[i], -1L); // -1 = is not visited
        }
        return costs;
    }

    private void initStartPoint(PipeTile[][] pipes) {
        List<Direction> possibleDirections = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        List<Point> neighborsOfStart = possibleDirections.stream().map(x -> x.addPoint(startPos)).toList();
        for (var n : neighborsOfStart){
            if (isInBounds(pipes,n)){
                for (var d : pipes[n.x][n.y].directions){
                    if (d.addPoint(n).equals(startPos)) {
                        pipes[startPos.x][startPos.y].directions.add(d.getOppositeDirection());
                    }
                }
            }
        }
    }

    void breathFirstSearch(){
        List<PipeTile> tilesToVisit = List.of(pipes[startPos.x][startPos.y]);
        long counter = 0L;

        while (!tilesToVisit.isEmpty()){
            List<PipeTile> unvisitedNeighbors = new ArrayList<>();
            for (PipeTile tile: tilesToVisit) {
                costs[tile.position.x][tile.position.y] = counter;
            for (var d : tile.directions){
                    Point targetPosition = d.addPoint(tile.position);
                    if (isInBounds(pipes, targetPosition)
                            && costs[targetPosition.x][targetPosition.y] == -1L // is Not visited
                            && pipes[targetPosition.x][targetPosition.y].directions.contains(d.getOppositeDirection())
                    ) unvisitedNeighbors.add(pipes[targetPosition.x][targetPosition.y]);
                }
            }
            counter++;
            tilesToVisit = unvisitedNeighbors;
        }
    }

    public List<Long> getAllPipesAsList() {
        List<Long> l = new ArrayList<>();
        for (var col : costs){
            for (var c : col){
                l.add(c);
            }
        }
        return l;
    }
}

class PipeTile {
    Set<Direction> directions;
    Point position;

    public static PipeTile fromChar(char c, int x, int y) {
        PipeTile pipeTile = new PipeTile();
        pipeTile.position = new Point(x,y);
        switch (c){
            case '|' -> pipeTile.directions = Set.of(Direction.NORTH, Direction.SOUTH);
            case '-' -> pipeTile.directions = Set.of(Direction.EAST, Direction.WEST);
            case 'L' -> pipeTile.directions = Set.of(Direction.EAST, Direction.NORTH);
            case 'J' -> pipeTile.directions = Set.of(Direction.WEST, Direction.NORTH);
            case '7' -> pipeTile.directions = Set.of(Direction.WEST, Direction.SOUTH);
            case 'F' -> pipeTile.directions = Set.of(Direction.EAST, Direction.SOUTH);
            case '.' -> pipeTile.directions = Set.of();
            case 'S' -> pipeTile.directions = new HashSet<>();
            default -> throw new IllegalStateException("Unexpected Character : " + c);
        }
        return pipeTile;
    }
}

enum Direction{
    NORTH(0,-1), SOUTH(0,1), WEST(-1,0), EAST(1,0);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
    Direction getOppositeDirection(){
        switch (this){
            case NORTH -> {
                return SOUTH;
            }
            case SOUTH -> {
                return NORTH;
            }
            case EAST -> {
                return WEST;
            }
            case WEST -> {
                return EAST;
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }
    Point addPoint(Point p){
        return new Point(p.x + this.x, p.y + this.y);
    }

}
