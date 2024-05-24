package Week7.Strategy;

import java.util.Map;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public String checkAlert(Map<String, Object> data) {
        int heartRate = (int) data.get("heart_rate");
        if (heartRate < 60) {
            return "Low heart rate detected!";
        } else if (heartRate > 100) {
            return "High heart rate detected!";
        } else {
            return "Heart rate is normal.";
        }
    }
}
