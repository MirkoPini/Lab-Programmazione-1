import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartLibraryTest {

    @Test
    void shouldIncreaseSizeWhenResourceIsAdded() {
        Ebook ebook1 = new Ebook("123", 50);
        SmartLibrary Library1 = new SmartLibrary(new ArrayList<>());
        Library1.addResource(ebook1);
        int actualResult = Library1.getResourceCount();
        int expectedResult = 1;
        assertEquals(expectedResult, actualResult, "Non funziona");
    }

    @Test
    void shouldOnlyReturnDownloadableResources() {
        Ebook ebook2 = new Ebook("123", 50);
        PhysicalBook PhysicalBook1 = new PhysicalBook("123", 50, "24254");
        SmartLibrary Library1 = new SmartLibrary(new ArrayList<>());
        Library1.addResource(ebook2);
        Library1.addResource(PhysicalBook1);
        int actualResult = Library1.getDownloadableResources().size();
        int expectedResult = 1;
        assertEquals(expectedResult, actualResult, "Non funziona");
    }
}