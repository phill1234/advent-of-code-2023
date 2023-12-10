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

        List<Identifier> relevantIdentifiers = new ArrayList<>();
        List<Identifier> starIdentifiers = new ArrayList<>();
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
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            StringBuilder actualIdentifier = new StringBuilder();
            for (int columnIndex = 0; columnIndex < rows.get(rowIndex).size(); columnIndex++) {
                // if character is digit
                if (Character.isDigit(rows.get(rowIndex).get(columnIndex))) {
                    // add to actualIdentifier
                    actualIdentifier.append(rows.get(rowIndex).get(columnIndex));
                    // if next character is not digit
                    try {
                        if (!Character.isDigit(rows.get(rowIndex).get(columnIndex + 1))) {
                            if (checkIfIdentifierIsRelevant(actualIdentifier.toString(), rowIndex, columnIndex + 1, rows)) {
                                // set column to start of identifier
                                Identifier identifier = new Identifier(rowIndex, columnIndex - actualIdentifier.length() + 1, actualIdentifier.toString());
                                relevantIdentifiers.add(identifier);
                            }
                            actualIdentifier = new StringBuilder();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                if (rows.get(rowIndex).get(columnIndex) == '*') {
                    starIdentifiers.add(new Identifier(rowIndex, columnIndex, "*"));
                }
            }

            // end of row
            if (!actualIdentifier.isEmpty()) {
                if (checkIfIdentifierIsRelevant(actualIdentifier.toString(), rowIndex, rows.get(rowIndex).size(), rows)) {
                    Identifier identifier = new Identifier(rowIndex, rows.get(rowIndex).size() - 1 - actualIdentifier.length(), actualIdentifier.toString());
                    relevantIdentifiers.add(identifier);
                }
            }
        }

        // PART 2 Extension
        // connect star sum when two identifiers are connected by a star identifier
        relevantIdentifiers = handleStarOperators(starIdentifiers, relevantIdentifiers);

        for (Identifier relevantIdentifier : relevantIdentifiers) {
            sum += relevantIdentifier.getValue();
        }


        System.out.println("Sum: " + sum);
    }

    private static List<Identifier> handleStarOperators(List<Identifier> starIdentifiers, List<Identifier> relevantIdentifiers) {
        List<Identifier> relevantIdentifiersForPart2 = new ArrayList<>();
        for (Identifier starIdentifier : starIdentifiers) {
            List<Identifier> connectedIdentifiers = new ArrayList<>();
            for (Identifier relevantIdentifier : relevantIdentifiers) {
                // first check if row is above
                boolean rowIsTouching = checkIfRowIsTouching(starIdentifier, relevantIdentifier);
                boolean columnIsTouching = checkIfColumnIsTouching(starIdentifier, relevantIdentifier);

                if (rowIsTouching && columnIsTouching) {
                    connectedIdentifiers.add(relevantIdentifier);
                }
            }
            if (connectedIdentifiers.size() > 1) {
                Identifier firstIdentifier = connectedIdentifiers.get(0);
                Identifier secondIdentifier = connectedIdentifiers.get(1);
                int newValue = firstIdentifier.getValue() * secondIdentifier.getValue();
                firstIdentifier.setValue(newValue);
                relevantIdentifiersForPart2.add(firstIdentifier);
                // remove every other identifier
                for (int i = 1; i < connectedIdentifiers.size(); i++) {
                    relevantIdentifiers.remove(connectedIdentifiers.get(i));
                }
                System.out.println("Found two connected identifiers: " + firstIdentifier.getIdentifier() + " and " + secondIdentifier.getIdentifier());
            }
        }
        return relevantIdentifiersForPart2;
    }

    private static boolean checkIfRowIsTouching(Identifier starIdentifier, Identifier relevantIdentifier) {
        boolean isSame = relevantIdentifier.getRow() == starIdentifier.getRow();
        boolean isTop = relevantIdentifier.getRow() - 1 == starIdentifier.getRow();
        boolean isBottom = relevantIdentifier.getRow() + 1 == starIdentifier.getRow();
        return isSame || isTop || isBottom;
    }

    private static boolean checkIfColumnIsTouching(Identifier starIdentifier, Identifier relevantIdentifier) {
        // check if column is left
        boolean isBefore = relevantIdentifier.getColumn() - 1 == starIdentifier.getColumn();
        boolean isAfter = relevantIdentifier.getColumn() + relevantIdentifier.getLength().length() == starIdentifier.getColumn();
        boolean isBetween = relevantIdentifier.getColumn() <= starIdentifier.getColumn() && relevantIdentifier.getColumn() + relevantIdentifier.getLength().length() > starIdentifier.getColumn();
        return isBefore || isAfter || isBetween;
    }

    private static boolean checkIfIdentifierIsRelevant(String actualIdentifier, int actualRowIndex, int actualColumnIndex, List<List<Character>> rows) {
        // check the actual row
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