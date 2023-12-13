import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Solves <a href="https://adventofcode.com/2023/day/12">Advent of Code 2023, Day 12</a>
 * ATTENTION it takes really long for part 2
 */

public class Day12 {
    List<SpringCondition> springConditions;

    public static void main(String[] args) throws IOException {
        Day12 solver = new Day12();
        Path path = new File("src/main/resources/day_12_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        long result = solver.bruteForcePossibilities();
        System.out.println("Day 12, Part 1 : " + result);

        solver.unfoldAllSpringConditions();
        long result2 = solver.bfRecursive();
        System.out.println("Day 12, Part 2 : " + result2);
    }
    public void parseInput(String content) {
        this.springConditions = content.lines().map(SpringCondition::fromLine).toList();
    }

    public boolean checkIsValid(String s, List<Integer> springs) {
        int listIndex = 0;
        for (int i = 0; i< s.length(); i++) {
            if (listIndex == springs.size()) return !s.substring(i).contains("#"); // all springs correct or there are some not in the List
            if (s.charAt(i) == '#'){
                int length = springs.get(listIndex);
                if (i+length > s.length()) return false; // not enough characters left
                String s2 = "#".repeat(Math.max(0, length)); // the right amount of #'s
                if (!(s.substring(i, i+length).equals(s2) && (i+length == s.length() || s.charAt(i+length) == '.')))
                    return false; // not the right amount of #'s
                i += length; // jump to end of #'s
                listIndex++; // check next item in list
            }
        }
        return listIndex == springs.size();
    }

    public boolean checkIsValidTill(String s, List<Integer> springs, int index){
        int listIndex = 0;
        for (int i = 0; i< index+1; i++) {
            if (listIndex == springs.size()) return !s.substring(i).contains("#");
            if (s.charAt(i) == '#') {
                int length = springs.get(listIndex);
                if (i + length > s.length()) return false; // not enough characters left
                if (i + length >= index) return true; //still possible
                String s2 = "#".repeat(Math.max(0, length)); // the right amount of #'s
                if (!(s.substring(i, i + length).equals(s2)
                        && (i + length == s.length()
                        || s.charAt(i + length) != '#'
                )))
                    return false; // not the right amount of #'s
                i += length; // jump to end of #'s
                listIndex++;
            }
        }
        return true;
    }

    public long bruteForcePossibilities(){
        long result = 0;
        for (var condition : springConditions){
            List<String> allPossibleConditions = expandConditions(condition.springs, condition.sizes);
            result += allPossibleConditions.stream()
                    .filter(x-> checkIsValid(x, condition.sizes()))
                    .count();
        }
        return result;
    }

    public long bfRecursive(){
        return springConditions.parallelStream()
                .map(x-> expandRecursive(x.springs(), x.sizes()))
                .reduce(Long::sum).orElseThrow();
    }

    private List<String> expandConditions(String springs, List<Integer> sizes) {
        List<String> act = List.of(springs);
        if (!springs.contains("?")) return List.of(springs);
        int maxHastags = sizes.stream().reduce(Integer::sum).orElseThrow();
        while (true) {
            List<String> nextIter = new ArrayList<>();
            int actchar = 0;
            for (String x : act){
                for (int i = actchar; i < x.length(); i++){
                    if (x.charAt(i) == '?'){
                        actchar = i;
                        nextIter.add(x.substring(0,i+1).replace('?', '.')
                                + (i<x.length()-1 ? x.substring(i+1) : ""));
                        nextIter.add(x.substring(0,i+1).replace('?', '#')
                                + (i<x.length()-1 ? x.substring(i+1) : ""));
                        break;
                    }
                }
            }
            if (nextIter.stream().noneMatch(x-> x.contains("?"))) return nextIter;
            int finalActchar = actchar;
            nextIter = nextIter.stream()
                    .filter(x-> hasToManyHashTags(x, maxHastags))
                    .filter(x-> checkIsValidTill(x, sizes, finalActchar))
                    .toList();
            act = nextIter;
        }
    }

    public long expandRecursive(String springs, List<Integer> sizes){
        if (!springs.contains("?")) return checkIsValid(springs, sizes) ? 1L : 0L;
        long count = 0L;
        int firstquestionmark = 0;
        for (int i= 0; i< springs.length(); i++){
            if (springs.charAt(i) == '?') {
                firstquestionmark = i;
                break;
            }
        }
        String left = springs.substring(0, firstquestionmark + 1).replace('?', '.')
                + (firstquestionmark < springs.length() - 1 ? springs.substring(firstquestionmark + 1) : "");

        String right = springs.substring(0, firstquestionmark + 1).replace('?', '#')
                + (firstquestionmark < springs.length() - 1 ? springs.substring(firstquestionmark + 1) : "");

        if (checkIsValidTill(left, sizes, firstquestionmark)){
            count += expandRecursive(left, sizes);
        }
        if (checkIsValidTill(left, sizes, firstquestionmark)){
            count += expandRecursive(right, sizes);
        }
        return count;
    }
    private boolean hasToManyHashTags(String s, int maxHashtags) {
        int count = (int) s.chars().filter(x -> x == '#').count();
        int questionMarks = (int) s.chars().filter(x -> x == '?').count();
        if (count > maxHashtags) return false; //to many hashtags
        return count + questionMarks >= maxHashtags; // to many dots
    }

    public void unfoldAllSpringConditions() {
        springConditions = springConditions.stream().map(SpringCondition::toUnfolded).toList();
    }


    record SpringCondition(String springs, List<Integer> sizes){
        static SpringCondition fromLine(String line){
            String[] parts = line.split(" ");
            return new SpringCondition(parts[0], utils.ParsingUtils.tryToParseAllNumbersAsInteger(parts[1].split(",")));
        }
        SpringCondition toUnfolded(){
            StringBuilder sb = new StringBuilder();
            sb.append(springs);
            List<Integer> unfoldedSizes = new ArrayList<>(sizes);
            for (int i = 1 ; i < 5; i++){
                unfoldedSizes.addAll(sizes);
                sb.append("?");
                sb.append(springs);
            }
            return new SpringCondition(sb.toString(), unfoldedSizes);
        }
    }
}

