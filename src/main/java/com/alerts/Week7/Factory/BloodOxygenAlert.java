package com.alerts.Week7.Factory;

public class BloodOxygenAlert extends Alert {
    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void triggerAlert() {
        System.out.println("Blood Oxygen Alert for patient " + patientId + " at " + timestamp + " due to " + condition);
    }
}
