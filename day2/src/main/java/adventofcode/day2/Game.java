package adventofcode.day2;

import java.util.ArrayList;
import java.util.List;

public class Game {
    int id;
    List<Round> rounds = new ArrayList<>();

    public Game(int id) {
        this.id = id;
    }

    public void addRound(Round round) {
        this.rounds.add(round);
    }

    public boolean isPossible(int red, int green, int blue) {
        HighestValues highestValues = getHighestValues();

        if (highestValues.red > red) {
            return false;
        }
        if (highestValues.green > green) {
            return false;
        }
        return highestValues.blue <= blue;
    }

    public int getPower() {
        HighestValues highestValues = getHighestValues();
        return highestValues.red * highestValues.green * highestValues.blue;
    }

    private HighestValues getHighestValues() {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (Round round : rounds) {
            if (round.red > red) {
                red = round.red;
            }
            if (round.green > green) {
                green = round.green;
            }
            if (round.blue > blue) {
                blue = round.blue;
            }
        }

        return new HighestValues(red, green, blue);
    }

    public static class Round {
        int green;
        int red;
        int blue;

        public void setResult(int amount, String color) {
            switch (color) {
                case "red":
                    this.red = amount;
                    break;
                case "green":
                    this.green = amount;
                    break;
                case "blue":
                    this.blue = amount;
                    break;
            }
        }

    }

    record HighestValues(int red, int green, int blue) {
    }
}
