import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * Solves <a href="https://adventofcode.com/2023/day/4">Advent of Code Day 4</a>
 */
public class DayFour {

    public static void main(String[] args) throws IOException {
        DayFour solver= new DayFour();
        Path path = new File("src/main/resources/day_four_input.txt").toPath();
        String content = Files.readString(path);
        int points = solver.solvePartOne(content);
        System.out.println("Day 3, Part 1 : " + points);

        int numberOfScratchcards = solver.solvePartTwo(content);
        System.out.println("Day 3, Part 2 : " + numberOfScratchcards);
    }

    private int solvePartTwo(String content) {
        List<DayFour.Card> cards = parseCards(content);
        int[] cardsWithCopies = createCopies(cards);
        return Arrays.stream(cardsWithCopies).sum();
    }

    private int solvePartOne(String content) {
        List<Card> cards = parseCards(content);
        return getPointsCount(cards);
    }


    public List<Card> parseCards(String content) {
        List<String> lines = content.lines().toList();
        List<Card> cards = new ArrayList<>();
        for (String line : lines){
            cards.add(Card.fromLine(line));

        }
        return cards;
    }

    public int getPointsCount(List<Card> cards) {
        return cards.stream().map(Card::getPoints).reduce(0, Integer::sum);
    }

    //solves the problem with dynamic programming
    public int[] createCopies(List<Card> cards) {
        int[] result = new int[cards.size()];
        Arrays.fill(result, 1); //initialise with one copy of each card
        result[0] = 1;
        for(int i=0; i< cards.size(); i++){
            Card c =  cards.get(i);
            assert(i == c.id()-1);
            int count = result[i];
            long matches = c.getMatches();
            if (matches > 0){
                for (int j=1; j <= matches; j++){
                    result[i+j] += count;
                }
            }

        }
        return result;
    }

    public record Card (int id, Set<Integer> winningNumbers, List<Integer> numbers){

        public static Card fromLine(String line){
            String[] leftAndRight = line.split(":");
            List<String> leftPart = Arrays.stream(leftAndRight[0].split(" ")).filter(x -> !x.equals(" ")).toList();
            int id = Integer.parseInt(leftPart.getLast());
            String[] cardNumbers = leftAndRight[1].split("\\|");
            Set<Integer> winningNumbers = new HashSet<>();
            for (String x : cardNumbers[0].split(" ")){
                try {
                    int n = Integer.parseInt(x);
                    winningNumbers.add(n);
                } catch (Exception e){}
            }

            List<Integer> numbers = new ArrayList<>();
            for (String x : cardNumbers[1].split(" ")) {
                try {
                    int n = Integer.parseInt(x);
                    numbers.add(n);
                } catch (Exception e){}
            }
            return new Card(id, winningNumbers, numbers);
        }
        public int getPoints(){
            long matches = getMatches();
            if (matches == 0) return 0;
            int result = 1;
            for (int i = 1; i < matches; i++ ){
                result *= 2;
            }
            return result;
        }

        public long getMatches() {
            return numbers.stream().filter(winningNumbers::contains).count();
        }
    }
}
