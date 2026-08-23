public class Audiobook extends DigiLibrary {

    // DATA FIELDS
    private String narratorID;
    private int audioLength; // Audiolength in seconds

    // CONSTRUCTORS
    public Audiobook() {
        // default
    }

    public Audiobook(String bookName, String genre, String ISBN, double basePrice, String narratorID, int audioLength) {
        super(bookName, genre, ISBN, basePrice);
        this.narratorID = narratorID;
        this.audioLength = audioLength;
    }

    // ACCESSOR METHODS

    // SETTERS

    public void setnarratorID(String narratorID) {
        this.narratorID = narratorID;
    }

    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }

    // GETTERS
    public String getnarratorID() {
        return this.narratorID;
    }

    public int getAudioLength() {
        return this.audioLength;
    }

    // AUXILLARY METHODS

    @Override
    public String toString() {
        String superString = super.toString();

        return superString + "\nNarratorID: " + this.narratorID
                + "\nAudio Length: " + this.audioLength;
    }

    @Override
    public double calcRoyalties() {
        // Calculates royalties per Audiobook based on genreRate, narratorID and audioaudioLength;
        double narratorRate = 0.20;

        return (narratorRate + genreRate()) * (audioLength/60) - super.getBasePrice();
    }

}
