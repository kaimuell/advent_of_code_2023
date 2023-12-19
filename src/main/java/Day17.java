import utils.Direction;
import utils.Rotation;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;


/**
 * Solves <a href="https://adventofcode.com/2023/day/17">Advent of Code 2023, Day 17</a>
 */

public class Day17 {
    private Point destination;
    private Map<Point, Integer> grid;
    private Map<Point, Set<StepsAndDirection>> tilesVisitedFrom;

    public static void main(String[] args) throws IOException {
        Day17 solver = new Day17();
        Path path = new File("src/main/resources/day_17_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);

        int result1 = solver.walkThroughGrid(3,1);
        System.out.println("Day 17, Part 1 : " + result1);

        solver.parseInput(content);
        int result2 = solver.walkThroughGrid(10,4);
        System.out.println("Day 17, Part 2 : " + result2);
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        var grid = new HashMap<Point,Integer>();
        Map<Point, Set<StepsAndDirection>> tilesVisitedFrom = new HashMap<>();
        for (int row = 0; row < lines.size(); row++) {
            for (int col = 0; col < lines.get(0).length(); col++) {
                Point point = new Point(col, row);
                grid.put(
                        point,
                        Character.getNumericValue(lines.get(row).charAt(col))
                );
                tilesVisitedFrom.put(point, new HashSet<>());
            }
        }
        this.grid = grid;
        this.tilesVisitedFrom = tilesVisitedFrom;
        this.destination = new Point(lines.getFirst().length()-1, lines.size()-1);
    }

    public int walkThroughGrid(int maxStepsInDirection, int minStepsInDirection) {
        PriorityQueue<Agent> queue = initialiseQueueWithAgents();
        while (!queue.isEmpty()) {
            Agent agent = queue.poll();

            if (hasReachedDestination(agent))  return agent.cost();

            if (checkForLoops(agent)) continue;

            if (agent.numberStepsInDirection < maxStepsInDirection) {
                walkInSameDirection(agent, queue);
            }
            if (agent.numberStepsInDirection >= minStepsInDirection) {
                WalkInChangedDirection(agent, queue);
            }
        }
        throw new IllegalStateException("No Solution Found");
    }

    private boolean hasReachedDestination(Agent agent) {
        return agent.pos().equals(destination);
    }

    private boolean checkForLoops(Agent agent) {
        Set<StepsAndDirection> visited = tilesVisitedFrom.get(agent.pos);
        StepsAndDirection currentStepsAndDirection =
                new StepsAndDirection(agent.numberStepsInDirection, agent.direction);
        if (visited.contains(currentStepsAndDirection)) return true;
        visited.add(currentStepsAndDirection);
        return false;
    }

    private void WalkInChangedDirection(Agent agent, PriorityQueue<Agent> queue) {
        List<Direction> possibleNextDirections = determineNextDirection(agent);
        for (Direction nextDirection : possibleNextDirections) {
            Point nextPosInDirection = nextDirection.addPoint(agent.pos());
            if (grid.containsKey(nextPosInDirection)) {
                queue.add(new Agent(
                        nextPosInDirection,
                        nextDirection,
                        agent.cost() + grid.get(nextPosInDirection),
                        1
                ));
            }
        }
    }

    private static List<Direction> determineNextDirection(Agent agent) {
        return List.of(
                agent.direction.rotate(Rotation.CLOCKWISE),
                agent.direction.rotate(Rotation.COUNTERCLOCKWISE)
        );
    }

    private void walkInSameDirection(Agent agent, PriorityQueue<Agent> queue) {
        Point nextPosInDirection = agent.direction().addPoint(agent.pos());
        if (grid.containsKey(nextPosInDirection)) {
            queue.add(new Agent(
                    nextPosInDirection,
                    agent.direction(),
                    agent.cost() + grid.get(nextPosInDirection),
                    agent.numberStepsInDirection() + 1
            ));
        }
    }

    private static PriorityQueue<Agent> initialiseQueueWithAgents() {
        PriorityQueue<Agent> queue = new PriorityQueue<>(Comparator.comparingInt(agent -> agent.cost));
        Point start = new Point(0, 0);
        queue.add(new Agent(start, Direction.EAST, 0, 0));
        queue.add(new Agent(start, Direction.SOUTH, 0, 0));
        return queue;
    }

    record Agent(Point pos, Direction direction, int cost, int numberStepsInDirection) {
    }
    record StepsAndDirection(int steps, Direction direction){}
}
