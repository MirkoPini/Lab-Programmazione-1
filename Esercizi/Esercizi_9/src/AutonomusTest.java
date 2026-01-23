import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutonomusTest {

    @Test
    void shouldReturnFalseWhenBatteryIsBelowThreshold() {
        TeslaModelS tesla2 = new TeslaModelS("Tesla SUS", 19);
        boolean actualResult = tesla2.canActivateAutopilot();
        boolean expectedResult = false;
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
    @Test
    void shouldReturnTrueWhenBatteryIsAtThreshold(){
        TeslaModelS tesla2 = new TeslaModelS("Tesla SUS", 20);
        boolean actualResult = tesla2.canActivateAutopilot();
        boolean expectedResult = true;
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
}