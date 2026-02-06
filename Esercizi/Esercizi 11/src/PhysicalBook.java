public class PhysicalBook extends LibraryResource {
    private String shelfLocation;

    public PhysicalBook(String title, int popularityIndex, String shelfLocation) {
        super(title, popularityIndex);
        this.shelfLocation = shelfLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    @Override
    public String getUsageTerms() {
        return "Physical copy ot location: " + getShelfLocation() + ".";
    }
}