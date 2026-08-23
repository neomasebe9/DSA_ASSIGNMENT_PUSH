public abstract class DigiLibrary {

    // INSTANCE/DATA FIELDS
    private String bookName;
    private String genre;
    private String ISBN;
    private double basePrice;

    // CONSTRUCTORS
    public DigiLibrary() {
        // default constructor
    }

    public DigiLibrary(String bookName, String genre, String ISBN, double basePrice) {
        this.bookName = bookName;
        this.genre = genre;
        this.ISBN = ISBN;
        this.basePrice = basePrice;
    }

    // ACCESSOR METHODS

        // SETTERS

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

        // GETTERS

    public String getBookName() {
        return this.bookName;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getISBN() {
        return this.ISBN;
    }

    public double getBasePrice() {
        return this.basePrice;
    }

    // AUXILLARY METHODS

    @Override
    public String toString() {
        return "Name: " + this.bookName
                + "\nGenre: " + this.genre
                + "\nISBN: " + this.ISBN
                + "\nBase Price: R" + this.basePrice;
    }
    
    public double genreRate() {
        double genreRate;
        if (this.genre.equalsIgnoreCase("Non-Fiction")) {
            genreRate = 0.60;
        } else if (this.genre.equalsIgnoreCase("Fantasy")) {
            genreRate = 0.75;
        } else if (this.genre.equalsIgnoreCase("Romance")) {
            genreRate = 0.55;
        } else {
            genreRate = 0.50;
        }

        return genreRate;
    }

    public abstract double calcRoyalties();

}
