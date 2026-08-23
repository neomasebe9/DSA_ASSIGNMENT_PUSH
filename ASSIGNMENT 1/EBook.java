public class EBook extends DigiLibrary {

    // DATA FIELDS
    private String author;
    private int pageNum;

    // CONSTRUCTORS
    public EBook() {
        // default
    }

    public EBook(String bookName, String genre, String ISBN, double basePrice, String author, int pageNum) {
        super(bookName, genre, ISBN, basePrice);
        this.author = author;
        this.pageNum = pageNum;
    }

    // ACCESSOR METHODS

    // SETTERS

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    // GETTERS
    public String getAuthor() {
        return this.author;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    // AUXILLARY METHODS

    @Override
    public String toString() {
        String superString = super.toString();

        return superString + "\nAuthor: " + this.author
        + "\nNumber of Pages: " + this.pageNum;
    }

    @Override
    public double calcRoyalties() {
        // Calculates royalties per eBook based on genre multiplier and number of pages
        return (super.genreRate() * pageNum) - super.getBasePrice();
    }

}
