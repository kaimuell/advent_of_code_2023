import utils.Direction;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.Direction.*;

public class Day16 {

    private TileElement[][] tiles;
    boolean[][] illuminated;
    Set<Direction>[][] visitedFromDirection;

    public static void main(String[] args) throws IOException {
        Day16 solver = new Day16();
        Path path = new File("src/main/resources/day_16_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        solver.illuminateFrom(0,0, EAST);
        long result1 = solver.countIlluminatedTiles();
        System.out.println("Day 16, Part 1 : " + result1);
        solver.reset();
        long result2 = solver.maxOfAllConfigurations();
        System.out.println("Day 16, Part 2 : " + result2);

        
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        TileElement[][] tiles = new TileElement[lines.size()][lines.getFirst().length()];
        Set<Direction>[][] visitedFromDirection = new Set[lines.size()][lines.getFirst().length()];
        TileElementFactory factory = new TileElementFactory();
        this.illuminated = new boolean[tiles.length][tiles[0].length];
        for (int i = 0; i< lines.size(); i++){
            for (int j = 0; j < lines.get(i).length(); j++){
                tiles[i][j] = factory.createTileELement(
                        lines.get(i).charAt(j)
                );
                visitedFromDirection[i][j] = new HashSet<>();
            }
        }
        this.tiles = tiles;
        this.visitedFromDirection = visitedFromDirection;
    }

    public void illuminateFrom(int row, int col, Direction direction) {
        if (!isInbounds(row,col, tiles)) return;
        if (visitedFromDirection[row][col].contains(direction))return; //already visited - prevent loops
        illuminated[row][col] = true;
        visitedFromDirection[row][col].add(direction);
        Set<Direction> nextDirections = tiles[row][col].changeDirection(direction);
        for (var d : nextDirections){
            Point nextCoordinate = d.addPoint(new Point(col, row));
            illuminateFrom(nextCoordinate.y, nextCoordinate.x, d);
        }
    }

    public long countIlluminatedTiles(){
        long result = 0L;
            for (var row : illuminated){
                for (var isTileIlluminated : row){
                    if (isTileIlluminated) result++;
                }
            }
        return result;
    }

    private boolean isInbounds(int row, int col, TileElement[][] tiles) {
        return row >= 0 && col >= 0 && row < tiles.length && col < tiles[0].length;
    }

    public long maxOfAllConfigurations() {
        List<Long> results = new ArrayList<>();
        //Upper Tiles
        for (int i = 0; i< tiles[0].length; i++){
            reset();
            illuminateFrom(0,i, SOUTH);
            results.add(countIlluminatedTiles());
        }
        //Left Tiles
        for (int i = 0; i< tiles.length; i++){
            reset();
            illuminateFrom(i,0, EAST);
            results.add(countIlluminatedTiles());
        }
        //Bottom Tiles
        for (int i = 0; i< tiles[0].length; i++){
            reset();
            illuminateFrom(tiles.length-1, i, NORTH);
            results.add(countIlluminatedTiles());
        }
        //Right Tiles
        for (int i = 0; i< tiles.length; i++){
            reset();
            illuminateFrom(i,tiles[0].length-1, WEST);
            results.add(countIlluminatedTiles());
        }

        return results.stream().reduce(0L, Long::max).longValue();
    }

    private void reset() {
        this.illuminated = new boolean[tiles.length][tiles[0].length];
        Set<Direction>[][] visitedFromDirection = new Set[tiles.length][tiles[0].length];
        for (int i = 0; i < visitedFromDirection.length; i++) {
            for (int j = 0; j < visitedFromDirection[i].length; j++) {
                visitedFromDirection[i][j] = new HashSet<>();
            }
        }
        this.visitedFromDirection = visitedFromDirection;
    }

    static class TileElementFactory{

        //use flyweight-pattern with one class for each tiletype
        private static final Empty empty = new Empty();
        private static final Splitter horizontalSplitter = new Splitter(Set.of(NORTH, Direction.SOUTH),
                Set.of(Direction.EAST, Direction.WEST), "Horizontal splitter");
        private static final Splitter verticalSplitter = new Splitter(Set.of(Direction.EAST, Direction.WEST),
                Set.of(NORTH, Direction.SOUTH), "Vertical splitter");
        private static final Mirror rightMirror = new Mirror(true, "Right Mirror : /");
        private static final Mirror leftMirror = new Mirror(false, "Left Mirror : \\");

        TileElement createTileELement(char c){
            switch (c){
                case '.' -> {
                    return  empty;
                }
                case '-' -> {
                    return horizontalSplitter;
                }
                case '|' -> {
                    return verticalSplitter;
                }
                case '/' -> {
                    return rightMirror;
                }
                case '\\' -> {
                    return leftMirror;
                }
            }
            throw new IllegalArgumentException("Unexpected Char " + c);
        }
    }

    interface TileElement{
        Set<Direction> changeDirection(Direction direction);
        String name();
    }

    static class Splitter implements TileElement{
        private final Set<Direction> returnedDirections;
        private final Set<Direction> acceptedDirections;
        private String name;

        public Splitter(Set<Direction> acceptedDirections, Set<Direction> returnedDirections, String name) {
            this.acceptedDirections = acceptedDirections;
            this.returnedDirections = returnedDirections;
            this.name = name;
        }

        @Override
        public Set<Direction> changeDirection(Direction direction) {
            if(acceptedDirections.contains(direction)) return returnedDirections;
            return Set.of(direction);
        }

        @Override
        public String name() {
            return this.name;
        }
    }

    static class Mirror implements TileElement {

        private final boolean isRightMirror;
        private final String name;

        public Mirror(boolean isRightMirror, String name) {
            this.isRightMirror = isRightMirror;
            this.name = name;
        }

        @Override
        public Set<Direction> changeDirection(Direction direction) {
            if (isRightMirror) {
                switch (direction) {
                    case EAST -> {
                        return Set.of(NORTH);
                    }
                    case WEST -> {
                        return Set.of(SOUTH);
                    }
                    case SOUTH -> {
                        return Set.of(WEST);
                    }
                    case NORTH -> {
                        return Set.of(EAST);
                    }
                }
            } else {
                switch (direction) {
                    case EAST -> {
                        return Set.of(SOUTH);
                    }
                    case WEST -> {
                        return Set.of(NORTH);
                    }
                    case SOUTH -> {
                        return Set.of(EAST);
                    }
                    case NORTH -> {
                        return Set.of(WEST);
                    }

                }
            }
            throw new RuntimeException();
        }

        @Override
        public String name() {
            return this.name;
        }
    }

    static class Empty implements TileElement{

        @Override
        public Set<Direction> changeDirection(Direction direction) {
            return Set.of(direction);
        }

        @Override
        public String name() {
            return "Empty";
        }
    }
}
