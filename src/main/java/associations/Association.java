package associations;

import entities.Entity;
import measurements.Measurement;

public class Association {

    private Entity entity;
    private Measurement measurement;
    private double gXY;

    public Association(Entity entity, Measurement measurement) {
        this.entity = entity;
        this.measurement = measurement;
        this.gXY = entity.getPredictedMeasurement().calcGXY(measurement);
    }

    public Entity getEntity() {
        return entity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public double getgXY() {
        return gXY;
    }

    public boolean hasEntity(Entity entity) {
        return this.entity.equals(entity);
    }

    public boolean hasMeasurement(Measurement measurement) {
        return this.measurement.equals(measurement);
    }
}
