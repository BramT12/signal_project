package Week7.Strategy;

import java.util.Map;

public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public String checkAlert(Map<String, Object> data) {
        int systolic = (int) data.get("systolic");
        int diastolic = (int) data.get("diastolic");
        if (systolic > 180 || diastolic > 120) {
            return "Critical blood pressure levels detected!";
        } else if (systolic > 140 || diastolic > 90) {
            return "High blood pressure alert!";
        } else {
            return "Blood pressure is normal.";
        }
    }
}
