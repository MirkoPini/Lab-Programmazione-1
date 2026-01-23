public abstract class ElectricVehicle {
    private String model;
    private int batteryLevel;

    public ElectricVehicle(String model, int batteryLevel){
        this.model = model;
        if(batteryLevel >= 0 || batteryLevel <= 100){
            this.batteryLevel = batteryLevel;
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }


    public int charge(int amount){
        if((batteryLevel + amount) <= 100){
            if(amount > 0) {
                this.batteryLevel += amount;
            }
        }else{
            this.batteryLevel = 100;
        }
        return getBatteryLevel();
    }

    public abstract String drive();
}
