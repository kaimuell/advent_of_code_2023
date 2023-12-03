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
public class DayThree {

    public static void main(String[] args) throws IOException {
        DayThree solver = new DayThree();
        Path path = new File("src/main/resources/day_three_input.txt").toPath();
        String input = Files.readString(path);

        int result1 = solver.solvePartOne(input);

        System.out.println("Day 3 , Part 1: " + result1);

    }

    public int solvePartOne(String input) {
        EngineShemaElement[][] engineSchema = this.parseEngineSchema(input);
        List<Integer> numbersWithAdjacentSymbols = this.determineNumbersWithAdjacentSymbols(engineSchema);
        return this.sumResult(numbersWithAdjacentSymbols);
    }


    public EngineShemaElement[][] parseEngineSchema(String content) {
        List<String> lines = content.lines().toList();
        EngineShemaElement[][] engineSchema = new EngineShemaElement[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++){
            for (int j = 0; j < lines.getFirst().length(); j++){
                engineSchema[i][j] = determineFieldElement(lines.get(i).charAt(j));
            }
        }
        return engineSchema;
    }

    private EngineShemaElement determineFieldElement(char c) {
        if (c == '.') return new ESNull();
        if (Character.isDigit(c)) return new ESNumber(getNumericValue(c));
        return new ESSymbol(c);
    }


    public List<Integer> determineNumbersWithAdjacentSymbols(EngineShemaElement[][] engineSchema) {
        List<Integer> numbers = new ArrayList<>();
        int currentNumber = -1; // -1 is default code for no number yet
        boolean hasNeighbor = false;
        for (int i = 0; i < engineSchema.length; i++) {
            for (int j = 0; j < engineSchema[i].length; j++) {
                switch (engineSchema[i][j]) {
                    case  ESNull esNull -> {
                        if (currentNumber != -1) {
                            if (hasNeighbor) numbers.add(currentNumber);
                            currentNumber = -1;
                            hasNeighbor = false;
                        }
                    }
                    case ESNumber number ->  {
                        if (currentNumber == -1) {
                            currentNumber = number.value;
                        } else {
                            currentNumber = (currentNumber * 10) + number.value;
                        }

                        hasNeighbor = checkForNeighboringSymbols(engineSchema, i, j) ? true : hasNeighbor;
                    }
                    case ESSymbol esSymbol-> {
                        if (currentNumber != -1) {
                            if (hasNeighbor) numbers.add(currentNumber);
                            currentNumber = -1;
                            hasNeighbor = false;
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + engineSchema[i][j]);
                }
            }
            //Check if there is a number left in this line and clean upIn this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
            if (currentNumber != -1) {
                if (hasNeighbor) numbers.add(currentNumber);
                currentNumber = -1;
                hasNeighbor = false;
            }
        }
        return numbers;
    }

    private boolean checkForNeighboringSymbols(EngineShemaElement[][] engineSchema, int i, int j) {
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

    private static boolean isInBounds(EngineShemaElement[][] engineSchema, int i, int j) {
        return i >= 0 && i < engineSchema.length && j >= 0 && j < engineSchema[i].length;
    }

    public int sumResult(List<Integer> integerList) {
        return integerList.stream().reduce(0, Integer::sum);
    }

    abstract class EngineShemaElement{ }
    class ESNumber extends EngineShemaElement {
        public int value;

        public ESNumber(int value) {
            this.value = value;
        }
    }

    class ESSymbol extends EngineShemaElement{
        public char symbol;

        public ESSymbol(char symbol) {
            this.symbol = symbol;
        }
    }
    class ESNull extends EngineShemaElement{}

}
