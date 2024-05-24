package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataFileReader implements DataReader {

    private String filePath;

    public DataFileReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                // Read logic
                String[] split = line.split(",");
                try {
                    dataStorage.addPatientData(
                            Integer.parseInt(split[0].split(": ")[1]),
                            Double.parseDouble(split[3].split(": ")[1].split("%")[0]),
                            split[2].split(":")[1],
                            Long.parseLong(split[1].split(": ")[1])
                    );
                } catch (NumberFormatException e) {
                    System.out.println("Failed to read the file");
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read the file");
        }
    }
}
