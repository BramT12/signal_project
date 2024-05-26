package com.alerts.Week7.Strategy;

import java.util.Map;

public class HealthMonitor {
    private AlertStrategy strategy;

    public void setStrategy(AlertStrategy strategy) {
        this.strategy = strategy;
    }

    public String checkCurrentAlert(Map<String, Object> data) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        return strategy.checkAlert(data);
    }
}
