import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day01 {



    public static void main(String[] args) throws IOException {
        Day01 solver = new Day01();
        Path input = new File("src/main/resources/day_01_input.txt").toPath();

        int result1 = solver.solveFirstQuest(input);
        System.out.println("Day One, Quest One : " + result1);

        int result2 = solver.solveSecondQuest(input);
        System.out.println("Day One, Quest Two : " + result2);
    }

    /**
     * Filters the digits in a string and concatenates the first and last one
     * @param content
     * @return
     */
    public List<Integer> filterDigitsAndConcatenateFirstAndLast(String content) {
        List<Integer> numbers = new ArrayList<>();
        List<String> lines = content.lines().toList();
        for (String line : lines){
            int[] digits = line.chars()
                    .filter(Character::isDigit)
                    .map(Character::getNumericValue)
                    .toArray();

            //first digit concatenated with last digit
            int number = (digits[0] * 10) + digits[digits.length-1];
            numbers.add(number);
        }
        return numbers;
    }

    public int sumNumbers(List<Integer> numbers) {
        return numbers.stream().reduce(0, Integer::sum);
    }

    /**
     * Solves the Quest at  https://adventofcode.com/2023/day/1#part1
     * @param resourcePath the path to the puzzle input
     * @return the solution
     * @throws IOException
     */
    public int solveFirstQuest(Path resourcePath) throws IOException {
        String content = Files.readString(resourcePath);
        List<Integer> numbers = this.filterDigitsAndConcatenateFirstAndLast(content);
        return this.sumNumbers(numbers);
    }

    /**
     * Filters the digits in a string and concatenates the first and last one
     * also checks for written out representations of the digits 1-9
     * @param content the string
     * @return the List of concatenated numbers
     */
    public List<Integer> filterDigitsAndConcatenateFirstAndLastExtended(String content) {
        List<Integer> numbers = new ArrayList<>();
        List<String> lines = content.lines().toList();
        for (String line : lines) {
            List<Integer> digits = convertToDigits(line);
            int number = (digits.getFirst() * 10) + digits.getLast() ;
            numbers.add(number);
        }
        return numbers;
    }

    /**
     * Converts Digits and written out representations of digits from a String to  a list of digits
     * @param content the String
     * @return the List of digits
     */
    private static List<Integer> convertToDigits(String content) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < content.length(); i++){
            char c = content.toLowerCase().charAt(i);
            if (Character.isDigit(c)){
                numbers.add(Character.getNumericValue(c));
                continue;
            }
            switch (c){
                case 'o' -> {
                    if(content.startsWith("one", i)) numbers.add(1);
                }
                case 't' -> {
                    if(content.startsWith("two", i)) numbers.add(2);
                    if(content.startsWith("three", i)) numbers.add(3);
                }
                case 'f' -> {
                    if(content.startsWith("four", i)) numbers.add(4);
                    if(content.startsWith("five", i)) numbers.add(5);
                }
                case 's' -> {
                    if(content.startsWith("six", i)) numbers.add(6);
                    if(content.startsWith("seven", i)) numbers.add(7);
                }
                case 'e' -> {
                    if(content.startsWith("eight", i)) numbers.add(8);
                }
                case 'n' -> {
                    if(content.startsWith("nine", i)) numbers.add(9);
                }
                default -> {}
            }
        }
        return numbers;
    }

    /**
     * Solves the Quest at  https://adventofcode.com/2023/day/1#part2
     * @param resourcePath the path to the puzzle input
     * @return the solution
     * @throws IOException
     */

    public int solveSecondQuest(Path resourcePath) throws IOException {
        String content = Files.readString(resourcePath);
        List<Integer> numbers = this.filterDigitsAndConcatenateFirstAndLastExtended(content);
        return this.sumNumbers(numbers);
    }
}
