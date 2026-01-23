public class ElectricTruck extends TeslaModelS{
    private int cargoWeight;

    public ElectricTruck(String model, int batteryLevel, int cargoWeight) {
        super(model, batteryLevel);
        this.cargoWeight = cargoWeight;
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(int cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    @Override
    public String drive() {
        return "Heavy transport moving " + getCargoWeight() + " kg...";
    }
}
