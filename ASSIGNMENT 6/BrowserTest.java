public class BrowserTest {
    
    public static void main(String[] args) {
        BrowserHistory<String> history = new BrowserHistory<>();
        MyArrayList<String> pages = new MyArrayList();

        pages.add(0, "Google");
        pages.add(1, "Facebook");
        pages.add(2, "YouTube");
        pages.add(3, "Instagram");
        
        history.visitPages(pages, 0);
        history.displayHistory();

    }
}
