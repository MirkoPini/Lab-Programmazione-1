public class TeslaModelS extends ElectricVehicle implements Autonomus{
    public TeslaModelS(String model, int batteryLevel) {
        super(model, batteryLevel);
    }

    @Override
    public boolean canActivateAutopilot() {
        return getBatteryLevel() >= MIN_BATTERY_FOR_AUTOPILOT;
    }

    @Override
    public String drive() {
        return "Silent acceleration...";
    }
}
