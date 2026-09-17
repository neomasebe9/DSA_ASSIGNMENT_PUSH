import java.util.Scanner;

public class BrowserTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BrowserHistory<String> history = new BrowserHistory<>();
        MyArrayList<String> pages = new MyArrayList<>();

        System.out.print("Enter the number of web pages to visit: ");
        int numPages = Integer.parseInt(scanner.nextLine().trim());

        for (int i = 0; i < numPages; i++) {
            System.out.print("Enter page " + (i + 1) + " URL/Name: ");
            String page = scanner.nextLine().trim();
            pages.add(pages.size(), page);
        }

        System.out.println("\n--- Visiting Pages (Pushing onto Stack) ---");
        history.visitPages(pages, 0);

        history.displayHistory();

        System.out.print("How many times do you want to press the Back button? ");
        int steps = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("\n--- Navigating Backwards (Popping from Stack) ---");
        history.goBack(steps);


        history.displayHistory();

        scanner.close();
    }
}