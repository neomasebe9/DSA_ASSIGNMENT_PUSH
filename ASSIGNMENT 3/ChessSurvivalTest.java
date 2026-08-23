import java.util.Scanner;

public class ChessSurvivalTest {

    public static Point initializePlayer(Grid grid) {
        Point kingPoint = new Point("K", 7, 3);

        grid.setPosition(kingPoint, kingPoint.getRow(), kingPoint.getCol());
        return kingPoint;
    }

    public static Point initializePawn(Grid grid) {

        int randomCol;
        do {
            randomCol = (int) (Math.random() * grid.getNumCols());
        } while (!grid.isEmpty(0, randomCol));

        Point pawn = new Point("P", 0, randomCol);

        grid.setPosition(pawn, pawn.getRow(), pawn.getCol());
        return pawn;
    }

    public static Point initializeTreasure(Grid grid) {
        int randomCol;
        int randomRow;
        do {
            randomCol = (int) (Math.random() * grid.getNumCols());
            randomRow = (int) (Math.random() * grid.getNumRows());
        } while (!grid.isEmpty(randomRow, randomCol));

        Point treasure = new Point("*", randomRow, randomCol);

        grid.setPosition(treasure, treasure.getRow(), treasure.getCol());
        return treasure;
    }

    public static void runGame(Scanner scanner) {
        // CONSTANTS
        int NUM_ROWS = 8;
        int NUM_COLUMNS = 8;
        Grid GRID = new Grid(NUM_COLUMNS, NUM_ROWS);

        // INITIALIZING
        MyArrayList<Point> PAWNS = new MyArrayList<>();
        MyArrayList<Point> TREASURES = new MyArrayList<>();
        int[] SCORE = new int[] { 0 };

        Point kingPoint = initializePlayer(GRID);
        Point firstPawn = initializePawn(GRID);
        PAWNS.add(0, firstPawn);

        Point treasure1 = initializeTreasure(GRID);
        TREASURES.add(0, treasure1);
        Point treasure2 = initializeTreasure(GRID);
        TREASURES.add(1, treasure2);

        String keyInput;

        // PROCESSING
        System.out.println("Current Score: " + SCORE[0]);
        System.out.println(GRID);

        do {

            if (!GRID.getGridLife()) {
                break;
            }

            System.out.print("Enter direction to move [U, D, L, R] (X to exit): ");
            keyInput = scanner.next();

            if (keyInput.equalsIgnoreCase("U") || keyInput.equalsIgnoreCase("D") || keyInput.equalsIgnoreCase("R")
                    || keyInput.equalsIgnoreCase("L")) {

                boolean successMove = GRID.movePiece(kingPoint, keyInput, kingPoint.getRow(), kingPoint.getCol(), SCORE,
                        PAWNS, TREASURES);

                if (!GRID.getGridLife()) {
                    break;
                }

                // The for-loop moves all PAWNS downwards if king move was valid
                if (successMove) {

                    for (int index = 0; index < PAWNS.size(); index++) {
                        Point pawn = PAWNS.get(index);
                        int oldRow = pawn.getRow();
                        int oldCol = pawn.getCol();

                        if (oldRow + 1 >= GRID.getNumRows()) {
                            // Pawn marches off the board
                            GRID.setPosition(GRID.getDefaultPoint(), oldRow, oldCol);
                            PAWNS.remove(index);
                            index--; // adjust index after removal
                        } else {
                            // Check if moving down collides with King
                            Point targetSquare = GRID.getPoint(oldRow + 1, oldCol);
                            if (targetSquare.getType().equals("K")) {
                                System.out.println("PAWN CAPTURED THE KING!");
                                System.out.println("GAME OVER!");
                                GRID.detectCollision(pawn, targetSquare, oldRow + 1, oldCol, SCORE, PAWNS, TREASURES);
                                break;
                            } else if (targetSquare.getType().equals("*")) {
                                // Pawn steps on treasure, overwrites it
                                GRID.setPosition(pawn, oldRow + 1, oldCol);
                                GRID.setPosition(GRID.getDefaultPoint(), oldRow, oldCol);
                            } else {
                                GRID.setPosition(pawn, oldRow + 1, oldCol);
                                GRID.setPosition(GRID.getDefaultPoint(), oldRow, oldCol);
                            }
                        }
                    }

                    if (!GRID.getGridLife()) {
                        break;
                    }

                    // ADDS a new pawn to the grid top
                    Point otherPawn = initializePawn(GRID);
                    PAWNS.add(PAWNS.size(), otherPawn);

                    System.out.println("Current Score: " + SCORE[0]);
                    System.out.println(GRID);
                }

            } else {

                if (!keyInput.equalsIgnoreCase("X")) {
                    System.out.println("Invalid move. Use either [D], [U], [L], [R]");
                }

            }

        } while (!keyInput.equalsIgnoreCase("X"));

        System.out.println("FINAL SCORE: " + SCORE[0]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            runGame(scanner);

            System.out.print("Play again? (Y/N): ");
            choice = scanner.next();

        } while (choice.equalsIgnoreCase("Y"));

        System.out.println("Thanks for playing!");
    }
}