import java.security.InvalidParameterException;

public class Point {
    // INSTANCE FIELDS
    private String type;
    private int currRow; // current row
    private int currCol;

    // CONSTRUCTOR
    public Point() {
        this.type = "_"; // sets the default/empty cell
    }

    public Point(String type, int currRow, int currCol) {
        setType(type);
        this.currRow = currRow;
        this.currCol = currCol;
    }

    // TO-STRING
    public String toString() {
        String myString = String.format("Piece type: %s\nCoordinates: [%d,%d]", this.type, currRow, currCol);

        return myString;
    }

    // ACCESSOR METHODS

    public void setType(String type) {
        if (type.equalsIgnoreCase("K") || type.equalsIgnoreCase("P") || type.equalsIgnoreCase("*")) {
            this.type = type;
        } else {
            throw new InvalidParameterException("Invalid type; can only be K or P");
        }

    }

    public void setCoords(int row, int col) {
        this.currRow = row;
        this.currCol = col;
    }

    public String getType() {
        return this.type;
    }

    public int getCol() {
        return this.currCol;
    }

    public int getRow() {
        return this.currRow;
    }

    // AUXILLARY METHODS
}
