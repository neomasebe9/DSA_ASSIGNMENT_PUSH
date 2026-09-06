public class BrowserHistory<E> {

    // INSTANCE FIELDS
    StackAsMyLinkedList<E> historyStack;

    // CONSTRUCTOR
    public BrowserHistory(){
        this.historyStack = new StackAsMyLinkedList<>();
    }
    
    // TO-STRING

    // METHODS
    
    public void visitPages(MyArrayList pages, int index){
        if (index == pages.size()){
            return;
        }

        historyStack.push((E)pages.get(index));

        visitPages(pages, index + 1);
    }

    public void goBack(){

    }

    public void displayHistory(){

    }
}
