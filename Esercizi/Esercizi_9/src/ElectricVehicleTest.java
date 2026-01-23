import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricVehicleTest {
    @Test
    void shouldIncreaseBatteryLevelWhenAmountIsPositive() {
        TeslaModelS tesla1 = new TeslaModelS("Model S", 67);
        int actualResult = tesla1.charge(20);
        int expectedResult = 87;
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
    @Test
    void shouldCapBatteryAtOneHundredWhenOvercharged() {
        TeslaModelS tesla1 = new TeslaModelS("Model S", 67);
        int actualResult = tesla1.charge(40);
        int expectedResult = 100;
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
    @Test
    void shouldNotChangeBatteryLevelWhenAmountIsNegative(){
        TeslaModelS tesla1 = new TeslaModelS("Model S", 67);
        int actualResult = tesla1.charge(-7);
        int expectedResult = 67;
        assertEquals(expectedResult, actualResult, "Non funzionna");
    }
}