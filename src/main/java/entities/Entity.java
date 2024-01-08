package entities;

import measurementTimestamps.MeasurementTimestamp;
import measurements.Measurement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;

public class Entity {

    private TreeMap<Timestamp, Measurement> pairs;
    private ArrayList<Vector> vectors;
    private Vector stormMotionVector;

    public Entity(MeasurementTimestamp measurementTimestamp) {
        this.pairs = new TreeMap<>();
        this.pairs.put(measurementTimestamp.getTimestamp(), measurementTimestamp.getMeasurement());
    }

    public TreeMap<Timestamp, Measurement> getPairs() {
        return pairs;
    }

    public void calcVectors() {
        vectors = new ArrayList<>();
        for (int i = 1; i < pairs.keySet().size(); i++) {
            vectors.add(new Vector(
                    pairs.get((Timestamp) pairs.keySet().toArray()[i]),
                    (Timestamp) pairs.keySet().toArray()[i],
                    pairs.get((Timestamp) pairs.keySet().toArray()[i - 1]),
                    (Timestamp) pairs.keySet().toArray()[i - 1]
            ));
        }
        stormMotionVector = new Vector(vectors);
    }

    public Measurement getPredictedMeasurement() {
        Measurement measurement = pairs.get(pairs.lastKey());
        Measurement predictedMeasurement = new Measurement(measurement);
        if (stormMotionVector != null) {
            predictedMeasurement.addVector(stormMotionVector);
        }
        return predictedMeasurement;
    }
}
