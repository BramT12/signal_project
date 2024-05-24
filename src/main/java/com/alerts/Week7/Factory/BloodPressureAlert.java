package Week7.Factory;

public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void triggerAlert() {
        System.out.println("Blood Pressure Alert for patient " + patientId + " at " + timestamp + " due to " + condition);
    }
}
