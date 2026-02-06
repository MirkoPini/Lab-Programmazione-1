import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryResourceTest {

    @Test
    public void shouldIncreasePopularityWhenPointsArePositive() {
        Ebook ebook1 = new Ebook("123", 10);
        int actualResult = ebook1.boostPopularity(10);
        int expectedResult = 20;
        assertEquals(expectedResult, actualResult, "Non funziona");
    }

    @Test
    public void shouldCapPopularityAtOneHundred() {
        Ebook ebook2 = new Ebook("123", 90);
        int actualResult = ebook2.boostPopularity(20);
        int expectedResult = 100;
        assertEquals(expectedResult, actualResult, "Non funziona");
    }

    @Test
    public void shouldNotChangePopularityWhenPointsAreNegative() {
        Ebook ebook3 = new Ebook("123", 10);
        int actualResult = ebook3.boostPopularity(-10);
        int expectedResult = 10;
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
}