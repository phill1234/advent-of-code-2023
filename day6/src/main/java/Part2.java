import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {
        String filePath = "src/resources/input.txt";
        Path path = Path.of(filePath);
        int result = 1;
        long time = 0;
        long distance = 0;

        for (String line : Files.readAllLines(path)) {
            if (line.startsWith("Time")) {
                time = getNumber(line);
            } else if (line.startsWith("Distance")) {
                distance = getNumber(line);
            }
        }

        List<Long> winningDistances = getWinningDistances(time, distance);
        result = result * winningDistances.size();
        System.out.println(result);
    }

    private static List<Long> getWinningDistances(long time, long distance) {
        List<Long> winningDistances = new ArrayList<>();
        for (int speed = 0; speed <= time; speed++) {
            long timeYouCanTravel = time - speed;
            long distanceYouCanTravel = timeYouCanTravel * speed;
            if (distanceYouCanTravel > distance) {
                winningDistances.add(distanceYouCanTravel);
            }
        }
        return winningDistances;
    }

    private static long getNumber(String line) {
        String[] numbers = line.replaceAll("[^0-9]", " ").trim().split(" +");
        java.lang.StringBuilder number = new java.lang.StringBuilder();
        for (String num : numbers) {
            number.append(num);
        }
        return Long.parseLong(number.toString());
    }
}