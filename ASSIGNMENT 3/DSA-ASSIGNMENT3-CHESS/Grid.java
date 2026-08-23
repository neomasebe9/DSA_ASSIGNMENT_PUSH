import java.util.Scanner;

public class Grid {
    // instance fields
    private int numCols;
    private int numRows;
    private Point defaultPoint;
    private Point[][] grid;
    private boolean gridLife;

    // constructors
    public Grid(int columns, int rows) {

        this.numCols = columns;
        this.numRows = rows;
        this.defaultPoint = new Point(); // Will be of type ".." or "0"
        grid = new Point[rows][columns];
        gridLife = true;

        // set all null spots to defaultPoint
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            Point[] row = grid[rowIndex];
            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                if (row[colIndex] == null) {
                    row[colIndex] = defaultPoint;
                }
            }
        }

    }

    // toString
    public String toString() {
        String underLine = "___".repeat(numCols) + "\n";
        String myString = "";

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {

            myString = myString + "| ";
            Point[] row = grid[rowIndex];

            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                String elem = row[colIndex].getType();
                myString = myString + elem + " | ";
            }

            myString = myString + "\n";
            // myString += underLine;
        }

        return myString;
    }

    // Accessor methods

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public boolean getGridLife() {
        return this.gridLife;
    }

    public Point getDefaultPoint() {
        return this.defaultPoint;
    }

    public Point getPoint(int row, int col) {
        return grid[row][col];
    }

    public void setPosition(Point val, int row, int col) {

        if (col >= getNumCols() || row >= getNumRows() || col < 0 || row < 0) {
            throw new IndexOutOfBoundsException("GRID OUT OF BOUNDS");
        }

        grid[row][col] = val;

        // Update Point coordinates
        if (val != defaultPoint) {
            val.setCoords(row, col);
        }
    }

    public boolean isEmpty(int row, int col) {
        if (grid[row][col] == defaultPoint) {
            return true;
        }
        return false;
    }

    public void clearGrid() {
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                grid[rowIndex][colIndex] = defaultPoint;
            }
        }
        this.gridLife = true;
    }

    // auxillary methods

    public boolean movePiece(Point target, String direction, int rowPos, int colPos, int[] score,
            MyArrayListChessGame<Point> pawns, MyArrayListChessGame<Point> treasures) {
        /*
         * (U = up,
         * L = left,
         * D = down,
         * R = right).
         */

        boolean successMove = true;

        switch (direction.toUpperCase()) {
            case "U":

                if (rowPos == 0) {
                    System.out.println("CANNOT MOVE UP!");
                    successMove = false;
                } else {
                    int nextRow = rowPos - 1;
                    int nextCol = colPos;
                    if (isEmpty(nextRow, nextCol)) {
                        // SET NEW POS
                        setPosition(target, nextRow, nextCol);

                        // REMOVE OLD POS
                        setPosition(defaultPoint, rowPos, colPos);
                    } else {
                        detectCollision(target, grid[nextRow][nextCol], nextRow, nextCol, score, pawns, treasures);
                    }

                }

                break;
            case "D":

                if (rowPos == numRows - 1) {
                    System.out.println("CANNOT MOVE DOWN!");
                    successMove = false;
                } else {
                    int nextRow = rowPos + 1;
                    int nextCol = colPos;
                    if (isEmpty(nextRow, nextCol)) {
                        // SET NEW POS
                        setPosition(target, nextRow, nextCol);

                        // REMOVE OLD POS
                        setPosition(defaultPoint, rowPos, colPos);
                    } else {
                        detectCollision(target, grid[nextRow][nextCol], nextRow, nextCol, score, pawns, treasures);
                    }

                }

                break;
            case "L":

                int nextColL = colPos - 1;
                if (nextColL < 0) {
                    nextColL = numCols - 1; // WRAPPING
                }

                if (isEmpty(rowPos, nextColL)) {
                    // SET NEW POS
                    setPosition(target, rowPos, nextColL);

                    // REMOVE OLD POS
                    setPosition(defaultPoint, rowPos, colPos);
                } else {
                    detectCollision(target, grid[rowPos][nextColL], rowPos, nextColL, score, pawns, treasures);
                }

                break;
            case "R":
                // LEFT AND RIGHT MOVEMENTS IMPLEMENT WRAPPING
                int nextColR = colPos + 1;
                if (nextColR >= numCols) {
                    nextColR = 0; // WRAPPING
                }

                if (isEmpty(rowPos, nextColR)) {
                    // SET NEW POS
                    setPosition(target, rowPos, nextColR);

                    // REMOVE OLD POS
                    setPosition(defaultPoint, rowPos, colPos);
                } else {

                    detectCollision(target, grid[rowPos][nextColR], rowPos, nextColR, score, pawns, treasures);
                }

                break;
        }

        return successMove;
    }

    public void detectCollision(Point target, Point otherPiece, int rowPos, int colPos, int[] score,
            MyArrayListChessGame<Point> pawns, MyArrayListChessGame<Point> treasures) {
        String targetPieceType = target.getType();
        String otherPieceType = otherPiece.getType();

        if (targetPieceType.equals("K")) {

            switch (otherPieceType) {
                case "P":
                    // King captured a pawn
                    System.out.println("KING CAPTURED A PAWN! +5 points");
                    score[0] += 5;

                    // Remove pawn from MyArrayList
                    for (int i = 0; i < pawns.size(); i++) {
                        if (pawns.get(i) == otherPiece) {
                            pawns.remove(i);
                            break;
                        }
                    }

                    setPosition(target, rowPos, colPos);
                    setPosition(defaultPoint, target.getRow(), target.getCol());
                    break;

                case "*":
                    // King collected treasure
                    System.out.println("TREASURE COLLECTED! +10 points");
                    score[0] += 10;

                    // Remove treasure from MyArrayList
                    for (int i = 0; i < treasures.size(); i++) {
                        if (treasures.get(i) == otherPiece) {
                            treasures.remove(i);
                            break;
                        }
                    }

                    System.out.println("Setting: " + target.getType() + " to coords: (" + rowPos + ", " + colPos + ")");
                    setPosition(defaultPoint, target.getRow(), target.getCol());
                    System.out.println(
                            "Setting: " + defaultPoint.getType() + " to coords: (" + target.getRow() + ", " + target
                                    .getCol() + ")");
                    setPosition(target, rowPos, colPos);

                    // Spawn replacement treasure
                    Point newTreasure = ChessSurvivalTest.initializeTreasure(this);
                    treasures.add(treasures.size(), newTreasure);
                    break;
            }
        } else if (targetPieceType.equals("P")) {
            if (otherPieceType.equals("K")) {
                System.out.println(target.getType() + " and " + otherPiece.getType() + ": COLLISION OCCURED");
                System.out.println("GAME OVER!");
                this.gridLife = false;
            }

            if (otherPieceType.equals("*")) {
                System.out.println(target.getType() + " and " + otherPiece.getType() + ": COLLISION OCCURED");

            }
        } else {
            if (otherPieceType.equals("P")) {
                System.out.println(target.getType() + " and " + otherPiece.getType() + ": COLLISION OCCURED");

                // Remove treasure from MyArrayList
                for (int i = 0; i < treasures.size(); i++) {
                    if (treasures.get(i) == otherPiece) {
                        treasures.remove(i);
                        break;
                    }
                }

                // Spawn replacement treasure
                Point newTreasure = ChessSurvivalTest.initializeTreasure(this);
                treasures.add(treasures.size(), newTreasure);
            }

        }
    }

} // END CLASS