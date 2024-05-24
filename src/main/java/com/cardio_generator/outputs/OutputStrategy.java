package com.cardio_generator.outputs;

/**
 * The OutputStrategy interface defines a contract for classes that provide output strategies for health data simulation.
 * Implementations of this interface handle the output of simulated health data for a given patient.
 */
public interface OutputStrategy {
    
    /**
     * Outputs simulated health data for a patient.
     * 
     * @param patientId The ID of the patient for whom the data is being simulated.
     * @param timestamp The timestamp of the simulated data.
     * @param label     The label or type of the simulated data.
     * @param data      The simulated data to be output.
     */
    void output(int patientId, long timestamp, String label, String data);
}
