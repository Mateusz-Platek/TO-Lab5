package measurementTimestamps;

import measurements.Measurement;
import java.sql.Timestamp;

public class MeasurementTimestamp {

    private Measurement measurement;
    private Timestamp timestamp;

    public MeasurementTimestamp(Measurement measurement, Timestamp timestamp) {
        this.measurement = measurement;
        this.timestamp = timestamp;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
