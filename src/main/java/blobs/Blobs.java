package blobs;

import measurements.Measurement;
import measurements.Measurements;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Blobs {

    private ArrayList<Blob> blobs;
    private Timestamp timestamp;

    public Blobs(ArrayList<Blob> blobs, Timestamp timestamp) {
        this.blobs = blobs;
        this.timestamp = timestamp;
    }

    public Measurements createMeasurements() {
        ArrayList<Measurement> measurements = new ArrayList<>();

        for (Blob blob: blobs) {
            measurements.add(new Measurement(blob));
        }

        return new Measurements(measurements, timestamp);
    }
}
