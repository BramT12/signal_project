package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

public class FileOutputStrategy implements OutputStrategy {
<<<<<<< HEAD
=======
    // baseDirectory has to be lowerCamelCase
    private String baseDirectory;
    // fileMap should also simply be lowerCamelCase
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();
>>>>>>> ef88620fa647026f06817d9742bd6fbb0fe35eb9

    public FileOutputStrategy(String baseDirectory) {

<<<<<<< HEAD
    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {

        this.BaseDirectory = baseDirectory;
=======
        this.baseDirectory = baseDirectory;
>>>>>>> ef88620fa647026f06817d9742bd6fbb0fe35eb9
    }

    @Override
    //output method should have an explanation. It has no return value but it overrides a method and catches exceptions
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        //filePath should be lowerCamelCaSe
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}