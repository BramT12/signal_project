package Week7.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HealthDataSimulator {
    private static HealthDataSimulator instance;
    private Map<String, Integer> simulationData;

    private HealthDataSimulator() {
        simulationData = new HashMap<>();
    }

    public static synchronized HealthDataSimulator getInstance() {
        if (instance == null) {
            instance = new HealthDataSimulator();
        }
        return instance;
    }

    public void generateData(String metric) {
        Random random = new Random();
        simulationData.put(metric, random.nextInt(101) + 50);
    }

    public Integer getSimulatedData(String metric) {
        return simulationData.get(metric);
    }
}
