package com.alerts.Week7.Decorator;

public class RepeatedAlertDecorator extends AlertDecorator {
    public RepeatedAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    @Override
    public void triggerAlert() {
        super.triggerAlert();
        System.out.println("This alert will be repeated at regular intervals.");
    }
}
