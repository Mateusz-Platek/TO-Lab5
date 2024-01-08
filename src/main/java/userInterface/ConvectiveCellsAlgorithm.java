package userInterface;

import measurements.Measurements;
import blobs.Blobs;
import entities.Entities;
import measurementTimestamps.MeasurementTimestamps;
import reflectivityMatrix.ReflectivityMatrix;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class ConvectiveCellsAlgorithm {

    private ReflectivityMatrix reflectivityMatrix;
    private Blobs blobs;
    private Measurements measurements;
    private MeasurementTimestamps measurementTimestamps;
    private Entities entities;

    public void loadImage(File file) throws IOException, ParseException {
        reflectivityMatrix = new ReflectivityMatrix(file);
        blobs = reflectivityMatrix.createBlobs();
        measurements = blobs.createMeasurements();
        measurementTimestamps = measurements.createMeasurementTimestamps();
        if (entities == null) {
            entities = new Entities(measurementTimestamps);
        } else {
            entities.createAssociations(measurementTimestamps, reflectivityMatrix.getTimestamp());
        }
    }

    public ReflectivityMatrix getReflectivityMatrix() {
        return reflectivityMatrix;
    }

    public Entities getEntities() {
        return entities;
    }
}
