public abstract class LibraryResource {
    private String title;
    private int popularityIndex;

    public LibraryResource(String title, int popularityIndex){
        this.title = title;
        if(popularityIndex >= 0 && popularityIndex <= 100){
            this.popularityIndex = popularityIndex;
        }else{
            this.popularityIndex = 0;
        }
    }

    public String getTitle() {
        return title;
    }

    public int getPopularityIndex() {
        return popularityIndex;
    }

    public int boostPopularity(int points){
        if((popularityIndex + points) < 100 && points >= 0){
            this.popularityIndex += points;
            return popularityIndex;
        }
        else{
            if((popularityIndex + points) > 100){
                this.popularityIndex = 100;
                return popularityIndex;
            }else{
                return popularityIndex;
            }
        }
    }

    public abstract String getUsageTerms();
}
