package Week7.Decorator;

public abstract class AlertDecorator extends Alert {
    protected Alert decoratedAlert;

    public AlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert.patientId, decoratedAlert.condition, decoratedAlert.timestamp);
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public void triggerAlert() {
        decoratedAlert.triggerAlert();
    }
}