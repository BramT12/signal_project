package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.util.Iterator;

public class WebSocketDataReaderTest {

    private DataStorage dataStorage;
    private WebSocketDataReader webSocketDataReader;

    @BeforeEach
    public void setup() throws Exception {
        dataStorage = new DataStorage();
        webSocketDataReader = new WebSocketDataReader(new URI("ws://localhost:8080"), dataStorage);
    }

    @Test
    public void testOnMessage() {
        String message = "1,98.6,HeartRate,1700000000000";
        webSocketDataReader.onMessage(message);

        assertFalse(dataStorage.getRecords(1, 1700000000000L, 1800000000000L).isEmpty());
    }

    @Test
    public void testOnOpen() {
        // Simulate the onOpen method call
        simulateOpenConnection();

        assertTrue(true);
    }

    @Test
    public void testOnError() {
        Exception ex = new Exception("Test Exception");
        webSocketDataReader.onError(ex);

        assertTrue(true);
    }

    @Test
    public void testOnClose() {
        webSocketDataReader.onClose(1000, "Normal closure", false);

        assertTrue(true);
    }

    private void simulateOpenConnection() {
        // Create ServerHandshake
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
                return 101; // Switching Protocols
            }

            @Override
            public String getHttpStatusMessage() {
                return "Switching Protocols";
            }
        };
        webSocketDataReader.onOpen(handshake);
    }
}

