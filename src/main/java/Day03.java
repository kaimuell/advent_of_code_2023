import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.getNumericValue;


/**
 * Solution to https://adventofcode.com/2023/day/3
 */
public class Day03 {

    public static void main(String[] args) throws IOException {
        Day03 solver = new Day03();
        Path path = new File("src/main/resources/day_03_input.txt").toPath();
        String input = Files.readString(path);

        int result1 = solver.solvePartOne(input);
        System.out.println("Day 3 , Part 1: " + result1);

        int result2 = solver.solvePartTwo(input);
        System.out.println("Day 3 Part 2 : " + result2);

    }

    public int solvePartOne(String input) {
        EngineSchemaElement[][] engineSchema = this.parseEngineSchema(input);
        List<NumberAndPosition> numbersWithAdjacentSymbols = this.determineNumbersWithAdjacentSymbols(engineSchema);
        return this.sumResult(numbersWithAdjacentSymbols);
    }

    public int solvePartTwo(String input) {
        EngineSchemaElement[][] engineSchema = this.parseEngineSchema(input);
        List<NumberAndPosition> numbersWithAdjacentSymbols = this.determineNumbersWithAdjacentSymbols(engineSchema);
        List<Integer> gearRatios = checkGearRatios(engineSchema, numbersWithAdjacentSymbols);
        return gearRatios.stream().reduce(0, Integer::sum);
    }

    public EngineSchemaElement[][] parseEngineSchema(String content) {
        List<String> lines = content.lines().toList();
        EngineSchemaElement[][] engineSchema = new EngineSchemaElement[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++){
            for (int j = 0; j < lines.getFirst().length(); j++){
                engineSchema[i][j] = determineFieldElement(lines.get(i).charAt(j));
            }
        }
        return engineSchema;
    }

    private EngineSchemaElement determineFieldElement(char c) {
        if (c == '.') return new ESNull();
        if (Character.isDigit(c)) return new ESNumber(getNumericValue(c));
        return new ESSymbol(c);
    }


    public List<NumberAndPosition> determineNumbersWithAdjacentSymbols(EngineSchemaElement[][] engineSchema) {
        List<NumberAndPosition> numbers = new ArrayList<>();
        int currentNumber = -1; // -1 is default code for no number yet
        boolean hasNeighbor = false;
        int start = 0;
        for (int i = 0; i < engineSchema.length; i++) {
            for (int j = 0; j < engineSchema[i].length; j++) {
                if (engineSchema[i][j] instanceof ESNumber number) {
                    if (currentNumber == -1) {
                        currentNumber = number.value;
                        start = j;
                    } else {
                        currentNumber = (currentNumber * 10) + number.value;
                    }

                    hasNeighbor = checkForNeighboringSymbols(engineSchema, i, j) || hasNeighbor;
                } else {
                    if (currentNumber != -1) {
                        if (hasNeighbor) numbers.add(new NumberAndPosition(i, start, j - 1, currentNumber));
                        currentNumber = -1;
                        hasNeighbor = false;
                    }
                }
            }
        //Check if there is a number left in this line and clean upIn this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
        if (currentNumber != -1) {
            if (hasNeighbor) numbers.add(new NumberAndPosition(i, start, engineSchema[i].length - 1, currentNumber));
            currentNumber = -1;
            hasNeighbor = false;
        }
    }
        return numbers;
    }

    private boolean checkForNeighboringSymbols(EngineSchemaElement[][] engineSchema, int i, int j) {
        if (isInBounds(engineSchema, i-1, j) && engineSchema[i-1][j] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i+1, j) && engineSchema[i+1][j] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i-1, j+1) && engineSchema[i-1][j+1] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i, j+1) && engineSchema[i][j+1] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i+1, j+1) && engineSchema[i+1][j+1] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i-1, j-1) && engineSchema[i-1][j-1] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i, j-1) && engineSchema[i][j-1] instanceof ESSymbol) return true;
        if (isInBounds(engineSchema, i+1, j-1) && engineSchema[i+1][j-1] instanceof ESSymbol) return true;
        return false;
    }

    private static boolean isInBounds(EngineSchemaElement[][] engineSchema, int i, int j) {
        return i >= 0 && i < engineSchema.length && j >= 0 && j < engineSchema[i].length;
    }

    public int sumResult(List<NumberAndPosition> integerList) {
        return integerList.stream().map(NumberAndPosition::value).reduce(0, Integer::sum);
    }

    public List<Integer> checkGearRatios(EngineSchemaElement[][] engineSchema, List<NumberAndPosition> numbersWithAdjacentSymbols) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < engineSchema.length; i++) {
            for (int j = 0; j < engineSchema[i].length; j++) {
                if (engineSchema[i][j] instanceof ESSymbol es && es.symbol == '*'){
                    int finalI = i;
                    int finalJ = j;
                    List<Integer> neighboringNumbers = numbersWithAdjacentSymbols.stream()
                            .filter(x -> x.isNeighbor(finalI,finalJ))
                            .map(NumberAndPosition::value)
                            .toList();
                    if (neighboringNumbers.size() == 2)
                        result.add(neighboringNumbers.getFirst() * neighboringNumbers.getLast());
                }
            }
        }
        return result;
    }

    public interface EngineSchemaElement { }
    public static class ESNumber implements EngineSchemaElement {
        public int value;

        public ESNumber(int value) {
            this.value = value;
        }
    }

    public static class ESSymbol implements EngineSchemaElement {
        public char symbol;

        public ESSymbol(char symbol) {
            this.symbol = symbol;
        }
    }
    public static class ESNull implements EngineSchemaElement {}

    public record NumberAndPosition(int row, int start, int end, int value){
        boolean isNeighbor(int row, int col){
            if (this.row == row && (start == col+1 || end == col -1)) return true;
            if (row == this.row-1 || row == this.row+1) return start - 1 <= col && col <= end + 1;
            return false;
        }
    }

}
