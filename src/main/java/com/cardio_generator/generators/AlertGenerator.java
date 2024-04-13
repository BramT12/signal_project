package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    // Changed variable name to camelCase and made it private
    private static final Random randomGenerator = new Random();
    private boolean[] alertStates; // false = resolved, true = pressed

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // Introduced a constant for probability threshold
                final double RESOLVE_THRESHOLD = 0.9;
                if (randomGenerator.nextDouble() < RESOLVE_THRESHOLD) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Introduced a constant for average rate of alerts
                final double LAMBDA = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                // Changed exponential calculation to use Math.exp instead of Math.expm1
                double p = 1 - Math.exp(-LAMBDA); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            // Changed error handling to catch specific exceptions or handle them at a higher level
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
