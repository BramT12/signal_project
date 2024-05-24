package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        // DataReader reader
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1928347839207L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1928347839208L);

        List<PatientRecord> records = storage.getRecords(1, 1928347839207L, 1928347839208L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue()); // First check
        assertEquals(200.0, records.get(1).getMeasurementValue()); // Second check
    }
}