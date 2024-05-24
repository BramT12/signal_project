package Week7.Singleton;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static DataStorage instance;
    private Map<String, String> data;

    private DataStorage() {
        data = new HashMap<>();
    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void saveData(String key, String value) {
        data.put(key, value);
    }

    public String getData(String key) {
        return data.get(key);
    }
}
