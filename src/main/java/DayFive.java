import utils.ParsingUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DayFive {

    public List<Long> seeds;
    public List<List<MapEntry>> maps;

    public static void main(String[] args) throws IOException {
        DayFive solver= new DayFive();
        Path path = new File("src/main/resources/day_five_input.txt").toPath();
        String content = Files.readString(path);
        solver.parseInput(content);
        Long result = solver.solvePartOne();
        System.out.println(result);
    }

    private Long solvePartOne() {
        Optional<Long> result = findClosestLocation();
        return result.get();
    }

    public void parseInput(String content) {
        List<String> lines = content.lines().toList();
        String[] firstLine = lines.get(0).split(" ");
        List<Long> seeds = ParsingUtils.tryToParseAllNumbers(firstLine);
        List<List<MapEntry>> maps = new ArrayList<>();
        List<MapEntry> entries = null;
        for (int i = 1 ; i < lines.size(); i++){
            if (lines.get(i).isEmpty()){
                if (entries!= null) maps.add(entries);
                entries = new ArrayList<>();
                i++;
            } else {
                List<Long> number = ParsingUtils.tryToParseAllNumbers(lines.get(i).split(" "));
                MapEntry entry = new MapEntry(number.get(0), number.get(1), number.get(2));
                entries.add(entry);
            }
        }
        maps.add(entries);
        this.seeds = seeds;
        this.maps = maps;
    }

    public Optional<Long> findClosestLocation() {
        List<Long> seeds = this.seeds;
        //Check every iteration of maps
        for (var map : maps){
            List<Long> grownSeeds = new ArrayList<>();
            for (Long seed : seeds){
                // find the map which changes the value, otherwise keep the value
                Long possibleSeed = tryToChangeValuethroughMaps(map, seed);
                grownSeeds.add(possibleSeed);
            }
            seeds = grownSeeds;
        }
        return seeds.stream().reduce(Long::min);
    }

    private static Long tryToChangeValuethroughMaps(List<MapEntry> map, Long seed) {
        Long possibleSeed = seed;
        for (MapEntry e : map){
            Long mappedSeed = e.mapNumber(seed);
            if (!mappedSeed.equals(seed)) possibleSeed = mappedSeed;
        }
        return possibleSeed;
    }

    public record MapEntry(Long destinationRangeStart, Long sourceRangeStart, Long rangeLength){
        public Long mapNumber(Long x){
            if (sourceRangeStart <= x && x <= sourceRangeStart+rangeLength){
                long difference  = x-sourceRangeStart;
                return destinationRangeStart + difference;
            }
            return x;
        }

    }
}
