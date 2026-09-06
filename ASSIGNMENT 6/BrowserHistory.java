public class BrowserHistory<E> {

    // INSTANCE FIELDS
    StackAsMyLinkedList<E> historyStack;

    // CONSTRUCTOR
    public BrowserHistory(){
        this.historyStack = new StackAsMyLinkedList<>();
    }
    
    // TO-STRING

    // METHODS
    
    public void visitPages(MyArrayList<E> pages, int index){
        if (index == pages.size()){
            System.out.println();
            return;
        }

        historyStack.push((E)pages.get(index));

        visitPages(pages, index + 1);
    }

    public void goBack(MyArrayList<String> pages){

    }

    public void displayHistory(){
        boolean current = true;
        int count = 0;
        MyArrayList<E> temp = new MyArrayList();

        while (historyStack.peek() != null){
            E data = historyStack.pop();
            if (current == true){
                System.out.println( count + 1 + ". " + data + " (current).");
                current = false;
            } else {
                System.out.println( count + 1 + ". " + data);
                
            }

            temp.add(count, data);
            count++;
        }
        
        visitPages(temp, 0);
    }
}
