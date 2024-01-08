package measurementTimestamps;

import java.util.ArrayList;

public class MeasurementTimestamps {

    private ArrayList<MeasurementTimestamp> measurementTimestamps;

    public MeasurementTimestamps(ArrayList<MeasurementTimestamp> measurementTimestamps) {
        this.measurementTimestamps = measurementTimestamps;
    }

    public ArrayList<MeasurementTimestamp> getMeasurementTimestamps() {
        return measurementTimestamps;
    }

    public void add(MeasurementTimestamp measurementTimestamp) {
        measurementTimestamps.add(measurementTimestamp);
    }
}
