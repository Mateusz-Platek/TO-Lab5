package measurements;

import measurementTimestamps.MeasurementTimestamp;
import measurementTimestamps.MeasurementTimestamps;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Measurements {

    private ArrayList<Measurement> measurements;
    private Timestamp timestamp;

    public Measurements(ArrayList<Measurement> measurements, Timestamp timestamp) {
        this.measurements = measurements;
        this.timestamp = timestamp;
    }

    public MeasurementTimestamps createMeasurementTimestamps() {
        ArrayList<MeasurementTimestamp> measurementTimestamps = new ArrayList<>();

        for (Measurement measurement: measurements) {
            measurementTimestamps.add(new MeasurementTimestamp(measurement, timestamp));
        }

        return new MeasurementTimestamps(measurementTimestamps);
    }
}
