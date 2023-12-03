package adventofcode.day1;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int sum = 0;
        String filePath = "src/main/resources/input.txt";

        Map<String, Integer> digitMap = new HashMap<>();
        digitMap.put("one", 1);
        digitMap.put("two", 2);
        digitMap.put("three", 3);
        digitMap.put("four", 4);
        digitMap.put("five", 5);
        digitMap.put("six", 6);
        digitMap.put("seven", 7);
        digitMap.put("eight", 8);
        digitMap.put("nine", 9);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // special regex with positive lookahead to match overlapping patterns like "oneight"
                Pattern pattern = Pattern.compile("(?=(one|two|three|four|five|six|seven|eight|nine|\\d+))");
                Matcher matcher = pattern.matcher(line);

                StringBuilder numbers = new StringBuilder();
                System.out.println(line);
                while (matcher.find()) {
                    String matchedString = matcher.group(1);
                    if (matchedString.matches("-?\\d+")) {
                        numbers.append(matchedString);
                    } else {
                        numbers.append(digitMap.get(matchedString));
                    }
                }
                int number = buildNumberFromFirstAndLastCharacter(numbers.toString());
                sum += number;
            }

            System.out.println("Total Sum: " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int buildNumberFromFirstAndLastCharacter(String numberString) {
        int firstDigit = Character.getNumericValue(numberString.charAt(0));
        int lastDigit = Character.getNumericValue(numberString.charAt(numberString.length() - 1));
        String firstAndLastDigit = firstDigit + "" + lastDigit;
        System.out.println("First and Last Digit: " + firstAndLastDigit + "\n");
        return Integer.parseInt(firstAndLastDigit);
    }
}
