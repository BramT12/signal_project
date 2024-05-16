package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs a {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert} method. This method should define the specific
     * conditions under which an alert will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluatePatientData(Patient patient) {
        bloodpressureAlert(patient);
        bloodSaturation(patient);
        hypotensiveHypoxemia(patient);
        
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended
     * to notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff

        
        System.out.println(alert.getPatientId() + alert.getCondition() + alert.getTimestamp());}
       

    private void bloodpressureAlert(Patient patient) {
        // Extract all records
        List<PatientRecord> records = patient.getAllPatientRecords();

        // Extract individual records for systolic & diastolic
        List<PatientRecord> systolicRecords = filterRecordsByType(records, "SystolicPressure");
        List<PatientRecord> diastolicRecords = filterRecordsByType(records, "DiastolicPressure");

        // Check if there is a significant increasing or decreasing trend in systolic pressure
        bloodpressure(systolicRecords, patient, "Systolic");

        // Check if there is a significant increasing or decreasing trend in diastolic pressure
        bloodpressure(diastolicRecords, patient, "Diastolic");

        // Check if systolic pressure exceeds or drops below certain thresholds
        bloodpressurelevel(systolicRecords, patient, 180, 90, "Systolic Pressure");

        // Check if diastolic pressure exceeds or drops below certain thresholds
        bloodpressurelevel(diastolicRecords, patient, 120, 60, "Diastolic Pressure");
    }

    private void bloodpressure(List<PatientRecord> records, Patient patient, String type) {
        if (records.size() >= 3) {
            for (int i = 0; i < records.size() - 2; i++) {
                // Check if there is a significant difference in measurements between consecutive records
                if (Math.abs(records.get(i).getMeasurementValue() - records.get(i + 1).getMeasurementValue()) > 10
                        && Math.abs(records.get(i + 1).getMeasurementValue() - records.get(i + 2).getMeasurementValue()) > 10) {
                    Alert alert = new Alert(Integer.toString(patient.getPatientId()), type + " Blood Pressure anomaly", records.get(i).getTimestamp());
                    triggerAlert(alert);
                }
            }
        }
    }

    private void bloodpressurelevel(List<PatientRecord> records, Patient patient, int upperThreshold, int lowerThreshold, String type) {
        for (PatientRecord record : records) {
            // Check if the measurement exceeds the upper threshold
            if (record.getMeasurementValue() > upperThreshold) {
                Alert alert = new Alert(Integer.toString(patient.getPatientId()), type + " above " + upperThreshold, record.getTimestamp());
                triggerAlert(alert);
            }
            // Check if the measurement is below the lower threshold
            if (record.getMeasurementValue() < lowerThreshold) {
                Alert alert = new Alert(Integer.toString(patient.getPatientId()), type + " below " + lowerThreshold, record.getTimestamp());
                triggerAlert(alert);
            }
        }
    }
    //go through records to trigger alert
    private void bloodSaturation(Patient patient) {
        List<PatientRecord> records = patient.getAllPatientRecords();
    List<PatientRecord> bloodSaturationRecords = new ArrayList<PatientRecord>();

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("Saturation")) {
                bloodSaturationRecords.add(record);
            }
        }

        if (bloodSaturationRecords.isEmpty()) {
            return;
        }
        //below 92?
        for (PatientRecord record : bloodSaturationRecords) {
            if (record.getMeasurementValue() < 92) {
                Alert alert = new Alert(Integer.toString(patient.getPatientId()), "Blood Saturation Drops Below 92", record.getTimestamp());
                triggerAlert(alert);
            }
        }

        // Check 
        saturationChange(records, patient, 5, 600000);
    }

    private void saturationChange(List<PatientRecord> records, Patient patient, int dropPercentage, long interval) {
        long startTime = records.get(0).getTimestamp();
        long endTime = records.get(records.size() - 1).getTimestamp();

        for (long currentTime = startTime; currentTime < endTime; currentTime += interval) {
            List<PatientRecord> tempList = new ArrayList<PatientRecord>();
            for (PatientRecord record : records) {
                if (record.getTimestamp() >= currentTime && record.getTimestamp() <= currentTime + interval) {
                    tempList.add(record);
                }
            }

            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            for (PatientRecord record : tempList) {
                if (record.getMeasurementValue() < min) {
                    min = record.getMeasurementValue();
                }
                if (record.getMeasurementValue() > max) {
                    max = record.getMeasurementValue();
                }
            }

            // If the difference between the maximum and minimum values in the interval exceeds the drop percentage, trigger an alert
            if ((max - min) >= dropPercentage) {
                Alert alert = new Alert(Integer.toString(patient.getPatientId()), "Saturation Drops Rapidly", currentTime);
                triggerAlert(alert);
            }
        }
    }

    private void hypotensiveHypoxemia(Patient patient) {
        // Extract all records
        List<PatientRecord> records = patient.getAllPatientRecords();

        // Collect unique timestamps
        List<Long> timestamps = new ArrayList<Long>();
        for (PatientRecord record : records) {
            long timestamp = record.getTimestamp();
            if (!timestamps.contains(timestamp)) {
                timestamps.add(timestamp);
            }
        }

        for (long time : timestamps) {
            boolean systolicAlert;
            boolean saturationAlert;

            for (PatientRecord record : records) {
                if (record.getTimestamp() == time) {
                    if (record.getRecordType().equals("SystolicPressure") && record.getMeasurementValue() < 90) {
                        systolicAlert = true;
                    }
                    if (record.getRecordType().equals("Saturation") && record.getMeasurementValue() < 92) {
                        saturationAlert = true;
                    }
                }
                // If both systolic pressure and blood saturation conditions are met at the same time, trigger an alert
                if (systolicAlert && saturationAlert) {
                    Alert alert = new Alert(Integer.toString(patient.getPatientId()), "Hypotensive Hypoxemia", time);
                    triggerAlert(alert);
                    break;
                }
            }
        }
    }

    private List<PatientRecord> filterRecordsByType(List<PatientRecord> records, String type) {
        List<PatientRecord> filteredRecords = new ArrayList<PatientRecord>();
        for (PatientRecord record : records) {
            if (record.getRecordType().equals(type)) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }
}



