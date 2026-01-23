import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargingStationTest {

    @Test
    void shouldReturnFormattedStringWithStationData() {
        ChargingStation charge1 = new ChargingStation("SDH-978", 544);
        String actualResult = charge1.getDetails();
        String expectedResult = "Station SDH-978 (544 KW)";
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
}