package Week7.Strategy;

import java.util.Map;

public class OxygenSaturationStrategy implements AlertStrategy {
    @Override
    public String checkAlert(Map<String, Object> data) {
        int oxygenSaturation = (int) data.get("oxygen_saturation");
        if (oxygenSaturation < 90) {
            return "Critical drop in oxygen saturation!";
        } else if (oxygenSaturation < 95) {
            return "Low oxygen saturation alert!";
        } else {
            return "Oxygen saturation is normal.";
        }
    }
}
