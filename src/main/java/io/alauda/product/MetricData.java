package io.alauda.product;

public class MetricData {

    private String Timestamp;
    private String WW_supply_flow;

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getWW_supply_flow() {
        return WW_supply_flow;
    }

    public void setWW_supply_flow(String WW_supply_flow) {
        this.WW_supply_flow = WW_supply_flow;
    }

    @Override
    public String toString() {
        return "MetricData{" +
                "Timestamp='" + Timestamp + '\'' +
                ", WW_supply_flow='" + WW_supply_flow + '\'' +
                '}';
    }
}
