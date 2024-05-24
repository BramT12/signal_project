package Week7.Strategy;

import java.util.Map;

public interface AlertStrategy {
    String checkAlert(Map<String, Object> data);
}
