package com.data_management;

import java.io.IOException;
import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebSocketDataReader extends WebSocketClient implements DataReader {

    private DataStorage dataStorage;

    public WebSocketDataReader(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Opened connection");
    }

    @Override
    public void onMessage(String message) {
        // Parse the message and update dataStorage
        // Example: Assuming message is a comma-separated string: patientId,measurementValue,recordType,timestamp
        String[] parts = message.split(",");
        int patientId = Integer.parseInt(parts[0]);
        double measurementValue = Double.parseDouble(parts[1]);
        String recordType = parts[2];
        long timestamp = Long.parseLong(parts[3]);
        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        this.connect();
    }
}
