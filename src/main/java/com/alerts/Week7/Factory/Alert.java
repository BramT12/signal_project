package Week7.Factory;
public abstract class Alert {
    protected String patientId;
    protected String condition;
    protected long timestamp;

    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public abstract void triggerAlert();
}
