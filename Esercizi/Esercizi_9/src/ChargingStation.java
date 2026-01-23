public class ChargingStation {
    private String stationId;
    private int powerOutput;

    public ChargingStation(String stationId, int powerOutput){
        this.stationId = stationId;
        this.powerOutput = powerOutput;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getPowerOuntput() {
        return powerOutput;
    }

    public void setPowerOuntput(int powerOuntput) {
        this.powerOutput = powerOuntput;
    }

    public String getDetails(){
        return "Station " + getStationId() + " (" + getPowerOuntput() + " KW)";
    }
}
