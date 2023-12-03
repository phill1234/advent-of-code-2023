package adventofcode.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/input.txt";
        List<List<Character>> rows = new ArrayList<>();
        int sum = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Character> column = new ArrayList<>();
                for (Character c : line.toCharArray()) {
                    column.add(c);
                }
                rows.add(column);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rows);
        List<String> relevantIdentifiers = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            String actualIdentifier = "";
            for (int columnIndex = 0; columnIndex < rows.get(rowIndex).size(); columnIndex++) {
                // if character is digit
                if (Character.isDigit(rows.get(rowIndex).get(columnIndex))) {
                    // add to actualIdentifier
                    actualIdentifier += rows.get(rowIndex).get(columnIndex);
                    // if next character is not digit
                    try {
                        if (!Character.isDigit(rows.get(rowIndex).get(columnIndex + 1))) {
                            System.out.println(actualIdentifier);
                            if (checkIfIdentifierIsRelevant(actualIdentifier, rowIndex, columnIndex + 1, rows)) {
                                relevantIdentifiers.add(actualIdentifier);
                            }
                            actualIdentifier = "";
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
            // end of row
            if (actualIdentifier.length() > 0) {
                System.out.println(actualIdentifier);
                if (checkIfIdentifierIsRelevant(actualIdentifier, rowIndex, rows.get(rowIndex).size(), rows)) {
                    relevantIdentifiers.add(actualIdentifier);
                }
            }
        }
        System.out.println(relevantIdentifiers);
        for (String relevantIdentifier : relevantIdentifiers) {
            sum += Integer.parseInt(relevantIdentifier);
        }
        System.out.println(sum);
    }

    private static boolean checkIfIdentifierIsRelevant(String actualIdentifier, int actualRowIndex, int actualColumnIndex, List<List<Character>> rows) {
        // check the actual row
        // end of row
        try {
            if (rows.get(actualRowIndex).get(actualColumnIndex) != '.') {
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        // start of row
        try {
            if (rows.get(actualRowIndex).get(actualColumnIndex - (actualIdentifier.length() + 1)) != '.') {
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        // check the row above
        try {
            List<Character> rowAbove = rows.get(actualRowIndex - 1);
            for (int i = actualColumnIndex; i >= actualColumnIndex - (actualIdentifier.length() + 1); i--) {
                try {
                    Character character = rowAbove.get(i);
                    if (character != '.' && !Character.isDigit(character)) {
                        return true;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        // check the row below
        try {
            List<Character> rowBelow = rows.get(actualRowIndex + 1);
            for (int i = actualColumnIndex; i >= actualColumnIndex - (actualIdentifier.length() + 1); i--) {
                try {
                    Character character = rowBelow.get(i);
                    if (character != '.' && !Character.isDigit(character)) {
                        return true;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }

}