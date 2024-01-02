import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws IOException {
        String filePath = "src/resources/input.txt";
        Path path = Path.of(filePath);
        int result = 1;
        int[] times = null;
        int[] distances = null;

        for (String line : Files.readAllLines(path)) {
            if (line.startsWith("Time")) {
                times = getNumbersInLine(line);
            } else if (line.startsWith("Distance")) {
                distances = getNumbersInLine(line);
            }
        }

        if (times == null || distances == null) {
            throw new RuntimeException("Times or distances are null");
        }
        List<Integer> winningDistances;
        for (int i = 0; i < times.length; i++) {
            winningDistances = getWinningDistances(times[i], distances[i]);
            result = result * winningDistances.size();
        }
        System.out.println(result);
    }

    private static List<Integer> getWinningDistances(int time, int distance) {
        List<Integer> winningDistances = new ArrayList<>();
        for (int speed = 0; speed <= time; speed++) {
            int timeYouCanTravel = time - speed;
            int distanceYouCanTravel = timeYouCanTravel * speed;
            if (distanceYouCanTravel > distance) {
                winningDistances.add(distanceYouCanTravel);
            }
        }
        return winningDistances;
    }

    private static int[] getNumbersInLine(String line) {
        String[] numbers = line.replaceAll("[^0-9]", " ").trim().split(" +");
        int[] intNumbers = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            intNumbers[i] = Integer.parseInt(numbers[i]);
        }
        return intNumbers;
    }
}