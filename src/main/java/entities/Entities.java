package entities;

import associations.Associations;
import measurementTimestamps.MeasurementTimestamp;
import measurementTimestamps.MeasurementTimestamps;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Entities {

    private ArrayList<Entity> entities;
    private Associations associations;

    public Entities(MeasurementTimestamps measurementTimestamps) {
        this.entities = new ArrayList<>();
        for (MeasurementTimestamp measurementTimestamp: measurementTimestamps.getMeasurementTimestamps()) {
            entities.add(new Entity(measurementTimestamp));
        }
    }

    public void createAssociations(MeasurementTimestamps measurementTimestamps, Timestamp timestamp) {
        associations = new Associations(entities, measurementTimestamps);
        associations.addMeasurementAndCreateNewEntitiesAndDeleteNotUpdated(timestamp);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
