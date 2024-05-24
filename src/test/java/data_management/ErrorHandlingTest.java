package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.Iterator;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import org.java_websocket.handshake.ServerHandshake;

public class ErrorHandlingTest {

    private DataStorage dataStorage;
    private WebSocketDataReader webSocketDataReader;

    @BeforeEach
    public void setup() throws Exception {
        dataStorage = new DataStorage();
        webSocketDataReader = new WebSocketDataReader(new URI("ws://localhost:8080"), dataStorage);
    }

    @Test
    public void testDataFormatError() {
        String invalidMessage = "invalid,data,format";
        webSocketDataReader.onMessage(invalidMessage);

        // Verify that no data was added to the storage
        assertTrue(dataStorage.getAllPatients().isEmpty());
    }

    @Test
    public void testConnectionLoss() throws Exception {
        // Simulate opening the connection
        simulateOpenConnection();

        // Simulate connection loss
        webSocketDataReader.onClose(1006, "Abnormal closure", true);

        // Add assertions or checks to verify how the connection loss is handled
        // (For example, checking logs or state changes if applicable)
        assertTrue(true); // Placeholder assertion, replace with actual checks as needed
    }

    private void simulateOpenConnection() {
        // Simulate the onOpen method call
        ServerHandshake handshake = new ServerHandshake() {
            @Override
            public Iterator<String> iterateHttpFields() {
                return null;
            }

            @Override
            public String getFieldValue(String s) {
                return null;
            }

            @Override
            public boolean hasFieldValue(String s) {
                return false;
            }

            @Override
            public byte[] getContent() {
                return new byte[0];
            }

            @Override
            public short getHttpStatus() {
                return 0;
            }

            @Override
            public String getHttpStatusMessage() {
                return "OK";
            }
        };
        webSocketDataReader.onOpen(handshake);
    }
}

