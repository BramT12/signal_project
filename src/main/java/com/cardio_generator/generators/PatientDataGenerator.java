package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
//@param patientID takes a certain patient, @param outputStrategy determines what data is needed
//interaface produces the needed patient data
public interface PatientDataGenerator {
    void generate(int patientId, OutputStrategy outputStrategy);
}
