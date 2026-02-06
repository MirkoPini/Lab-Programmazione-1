import java.util.ArrayList;
import java.util.List;

public class SmartLibrary {
    private ArrayList<LibraryResource> book;

    public SmartLibrary(ArrayList<LibraryResource> book){
        this.book = book;
    }

    public void addResource(LibraryResource res){
        this.book.add(res);
    }

    public boolean removeResource(LibraryResource res){
        if(book.contains(res)){
            this.book.remove(res);
            return true;
        }else{
            return false;
        }
    }

    public int getResourceCount(){
        return this.book.size();
    }

    public ArrayList<LibraryResource> getDownloadableResources(){
        ArrayList<LibraryResource> downloadable = new ArrayList<>();
        for (LibraryResource e : book){
            if(e instanceof Downloadable){
                downloadable.add(e);
            }
        }
        return downloadable;
    }
}
