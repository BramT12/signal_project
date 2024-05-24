package Week7.Factory;

public class ECGAlert extends Alert {
    public ECGAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void triggerAlert() {
        System.out.println("ECG Alert for patient " + patientId + " at " + timestamp + " due to " + condition);
    }
}
