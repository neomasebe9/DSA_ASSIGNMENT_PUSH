public class BrowserHistory<E> {

    // INSTANCE FIELDS
    private StackAsMyLinkedList<E> historyStack;
    private int size;

    // CONSTRUCTOR
    public BrowserHistory() {
        this.historyStack = new StackAsMyLinkedList<>();
        this.size = 0;
    }

    // METHODS

    public void visitPages(MyArrayList<E> pages, int index) {
        if (pages == null || index >= pages.size()) {
            return;
        }

        E page = pages.get(index);
        historyStack.push(page);
        size++;
        System.out.println("Visited: " + page);

        visitPages(pages, index + 1);
    }


    public void goBack(int steps) {

        if (steps <= 0 || historyStack.peek() == null) {
            return;
        }

        if (size <= 1) {
            System.out.println("Cannot go back further. At initial page: " + historyStack.peek());
            return;
        }

        // Pop current page
        E popped = historyStack.pop();
        size--;
        System.out.println("Went back from: " + popped);

        // Recursive call for remaining steps
        goBack(steps - 1);
    }

    
    public void displayHistory() {
        if (historyStack.peek() == null) {
            System.out.println("Browser history is empty.");
            return;
        }

        MyArrayList<E> temp = new MyArrayList<>();
        int count = 1;

        System.out.println("\n--- Browser History ---");
        while (historyStack.peek() != null) {
            E page = historyStack.pop();
            if (count == 1) {
                System.out.println(count + ". " + page + " (current)");
            } else {
                System.out.println(count + ". " + page);
            }
            temp.add(temp.size(), page);
            count++;
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            historyStack.push(temp.get(i));
        }
        System.out.println("-----------------------\n");
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return historyStack.toString();
    }
}