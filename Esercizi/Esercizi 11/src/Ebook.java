public class Ebook extends LibraryResource implements Downloadable{
    public Ebook(String title, int popularityIndex) {
        super(title, popularityIndex);
    }

    @Override
    public boolean isAvailableForOffline() {
        if(getPopularityIndex() >= MIN_POPULARITY_FOR_DOWNLOAD){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getUsageTerms() {
        return "Digital license: single user access.";
    }
}
