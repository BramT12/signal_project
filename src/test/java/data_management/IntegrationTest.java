package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.List;
import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import com.alerts.AlertGenerator;

public class IntegrationTest {

    private DataStorage dataStorage;
    private WebSocketDataReader webSocketDataReader;
    private AlertGenerator alertGenerator;

    @BeforeEach
    public void setup() throws Exception {
        dataStorage = new DataStorage();
        webSocketDataReader = new WebSocketDataReader(new URI("ws://localhost:8080"), dataStorage);
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    public void testRealTimeDataProcessing() throws Exception {
        webSocketDataReader.connect();

        // Simulate sending a message to the WebSocketDataReader
        webSocketDataReader.onMessage("1,98.6,HeartRate,1700000000000");
        webSocketDataReader.onMessage("1,120,SystolicPressure,1700000000000");
        webSocketDataReader.onMessage("1,80,DiastolicPressure,1700000000000");
        webSocketDataReader.onMessage("1,90,Saturation,1700000000000");

        // Verify that the data was processed and stored correctly
        assertFalse(dataStorage.getRecords(1, 1700000000000L, 1800000000000L).isEmpty());

        // Verify that the alert generator evaluates the data correctly
        for (Patient patient : dataStorage.getAllPatients()) {
            alertGenerator.evaluatePatientData(patient);
        }

        // first check
        List<PatientRecord> records = dataStorage.getRecords(1, 1700000000000L, 1800000000000L);
        assertEquals(4, records.size(), "Should contain 4 records for patient 1");

        Patient patient = dataStorage.getAllPatients().get(0);
        assertNotNull(patient, "Patient should not be null");


    }
}

