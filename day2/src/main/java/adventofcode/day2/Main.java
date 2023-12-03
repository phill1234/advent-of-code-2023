package adventofcode.day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/input.txt";
        // Given: 12 red cubes, 13 green cubes, and 14 blue cubes
        int red = 12;
        int green = 13;
        int blue = 14;

        int sumOfPossibleGameIds = 0;
        int sumOfPowers = 0;

        // read games
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                Game game = parseGame(line);
                if (game.isPossible(red, green, blue)) {
                    sumOfPossibleGameIds += game.id;
                }
                // Part 2
                sumOfPowers += game.getPower();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Sum of possible game ids: " + sumOfPossibleGameIds);
        System.out.println("Sum of powers: " + sumOfPowers);
    }

    private static Game parseGame(String line) {
        // Example of a line Game 1: 4 red, 5 blue, 4 green; 7 red, 8 blue, 2 green; 9 blue, 6 red; 1 green, 3 red, 7 blue; 3 green, 7 red
        Pattern pattern = Pattern.compile("Game (\\d+): (.*)");
        Matcher matcher = pattern.matcher(line);
        Game game = null;

        if (matcher.matches()) {
            game = new Game(Integer.parseInt(matcher.group(1)));
            String[] rounds = matcher.group(2).split("; ");
            for (String round : rounds) {
                Game.Round gameRound = parseRound(round);
                game.addRound(gameRound);
            }
        }
        return game;
    }

    private static Game.Round parseRound(String round) {
        // Example of a round: 4 red, 5 blue, 4 green
        Game.Round gameRound = new Game.Round();
        Pattern pattern = Pattern.compile("(\\d+) (red|blue|green)");
        Matcher matcher = pattern.matcher(round);

        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2);
            gameRound.setResult(amount, color);
        }

        return gameRound;
    }
}
