package adventofcode.day3;

public class Identifier {
    private int row;
    private int column;

    private String identifier;

    private int value;


    public Identifier(int row, int column, String value) {
        this.row = row;
        this.column = column;
        this.identifier = value;
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.value = 0;
        }
    }

    public String toString() {
        return "Identifier{" +
                "row=" + row +
                ", column=" + column +
                ", value='" + identifier + '\'' +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getLength() {
        return identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
