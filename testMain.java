public class testMain {
    public static void main(String[] args) {
        EBook ebook1 = new EBook("The Hobbit", "Fantasy", "0261102217", 150.00, "J.R.R. Tolkien", 310);
        Audiobook audio1 = new Audiobook("Atomic Habits", "Non-Fiction", "1847941831", 200.00, "N-104", 19800);

        // EXPLICIT toString() Call:
        System.out.println("--- Explicit Call (.toString()) ---");
        String explicitOutput = ebook1.toString();
        System.out.println(explicitOutput);

        System.out.println("\n-----------------------------------\n");

        // IMPLICIT toString() Call:
        System.out.println("--- Implicit Call (passing object directly) ---");
        System.out.println(audio1);

        System.out.println("=== 2. DEMONSTRATING POLYMORPHISM ===");

        DigiLibrary[] catalog = new DigiLibrary[3];

        catalog[0] = new EBook("Dune", "Fantasy", "0441172719", 180.00, "Frank Herbert", 412);
        catalog[1] = new Audiobook("Becoming", "Non-Fiction", "1524763138", 220.00, "N-882", 21600);
        catalog[2] = new EBook("Pride and Prejudice", "Romance", "0141439518", 120.00, "Jane Austen", 279);

        for (DigiLibrary item : catalog) {
            System.out.println("----Details: ----");
            System.out.println(item);
            System.out.printf("Royalties Calculated (per book): R%.2f\n", item.calcRoyalties());
            System.out.println("-----------------------------------");
        }
    }
}
