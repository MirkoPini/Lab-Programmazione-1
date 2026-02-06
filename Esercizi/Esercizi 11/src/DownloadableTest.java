import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DownloadableTest {

    @Test
    void shouldReturnFalseWhenPopularityIsBelowThreshold(){
        Ebook ebook1 = new Ebook("123", 29);
        boolean actualResult = ebook1.isAvailableForOffline();
        boolean expectedResult = false;
        assertEquals(expectedResult, actualResult, "Non funziona");
    }

    @Test
    void shouldReturnTrueWhenPopularityIsAtThreshold(){
        Ebook ebook2 = new Ebook("123", 30);
        boolean actualResult = ebook2.isAvailableForOffline();
        boolean expectedResult = true;
        assertEquals(expectedResult, actualResult, "Non funziona");
    }
}