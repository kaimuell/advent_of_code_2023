import utils.ParsingUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Solves <a href="https://adventofcode.com/2023/day/9">Advent of Code 2023 - Day Nine</a>
 */
public class DayNine {
    List<SensorReading> readings;

    public static void main(String[] args) throws IOException {
        DayNine solver = new DayNine();
        Path path = new File("src/main/resources/day_nine_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);

        long result1 = solver.solvePartOne();
        System.out.println("Day 9, Part 1 : " + result1);

        long result2 = solver.sumFirstNumbers();
        System.out.println("Day 9, Part 2 : " + result2);
    }

    private long solvePartOne() {
        for (var x: readings){
            x.expand();
        }
        return sumLastNumbers();
    }

    Long sumLastNumbers() {
        long result = 0L;
        for (SensorReading x : readings) {
            Long last = x.sequences.getFirst().getLast();
            result = result + last;
        }
        return result;
    }


    public void parseInput(String content) {
        this.readings = content.lines()
                .map(SensorReading::fromLine)
                .toList();
    }

    public long sumFirstNumbers() {
        long result = 0L;
        for (SensorReading x : readings) {
            Long last = x.sequences.getFirst().getFirst();
            result = result + last;
        }
        return result;
    }
}

    class SensorReading{
        public List<List<Long>> sequences = new ArrayList<>();
        public static SensorReading fromLine(String s) {
            SensorReading sr = new SensorReading();
            List<Long> l = ParsingUtils.tryToParseAllNumbers(s.split(" "));
            sr.sequences.add(l);
            return sr;
        }

        public void expand(){
            extractDifferences();
            predictNextNumber();
            predictFormerNumber();
        }

        private void predictNextNumber() {
            sequences.getLast().add(0L);
            for (int i = sequences.size()-2; i >= 0; i--){
                Long div = sequences.get(i+1).getLast();
                Long n = sequences.get(i).getLast();
                sequences.get(i).add(n+div);
            }
        }

        private void predictFormerNumber(){
            reverseAllSequeces();
            sequences.getLast().add(0L);
            for (int i = sequences.size()-2; i >= 0; i--){
                Long div = sequences.get(i+1).getLast();
                Long n = sequences.get(i).getLast();
                sequences.get(i).add(n-div);
            }
            reverseAllSequeces();
        }



        private void reverseAllSequeces() {
            for (var x : sequences){
                Collections.reverse(x);
            }
        }

        private void extractDifferences() {
            boolean allZeroes = sequences
                    .getFirst()
                    .stream()
                    .allMatch(x -> x == 0L);

            while (!allZeroes) {
                var actList = sequences.getLast();
                List<Long> l = new ArrayList<>();
                for (int i = 0; i< actList.size()-1; i++){
                    l.add(actList.get(i+1) - actList.get(i));
                }
                sequences.add(l);
                allZeroes = l.stream().allMatch(x -> x == 0L);
            }
        }


    }
