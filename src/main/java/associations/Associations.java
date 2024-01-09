package associations;

import entities.Entity;
import measurementTimestamps.MeasurementTimestamp;
import measurementTimestamps.MeasurementTimestamps;
import measurements.Measurement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

public class Associations {

    private ArrayList<Association> associations;
    private ArrayList<Entity> entities;
    private MeasurementTimestamps measurementTimestamps;

    public Associations(ArrayList<Entity> entities, MeasurementTimestamps measurementTimestamps) {
        this.associations = new ArrayList<>();
        this.entities = entities;
        this.measurementTimestamps = measurementTimestamps;
        for (Entity entity: entities) {
            for (MeasurementTimestamp measurementTimestamp: measurementTimestamps.getMeasurementTimestamps()) {
                associations.add(new Association(entity, measurementTimestamp.getMeasurement()));
            }
        }
    }

    private ArrayList<Association> createB(ArrayList<Association> associations) {
        ArrayList<Association> winningEntity = new ArrayList<>();

        Entity entity = associations.getFirst().getEntity();

        for (Association association: associations) {
            if (association.hasEntity(entity)) {
                winningEntity.add(association);
            }
        }

        return winningEntity;
    }

    private ArrayList<Association> createC(ArrayList<Association> associations, Association association) {
        ArrayList<Association> without = new ArrayList<>();

        for (Association assoc: associations) {
            if (!assoc.hasEntity(association.getEntity()) && !assoc.hasMeasurement(association.getMeasurement())) {
                without.add(assoc);
            }
        }

        return without;
    }

    private double calcProbOfArray(ArrayList<Association> associations) {
        double prob = 1;
        for (Association association: associations) {
            prob *= association.getgXY();
        }
        return prob;
    }

    private ArrayList<Association> selectWinningArray(ArrayList<ArrayList<Association>> arrayList) {
        double prob = 0;
        ArrayList<Association> selected = null;
        int size = 0;
        for(ArrayList<Association> associations: arrayList) {
            if (calcProbOfArray(associations) > prob && associations.size() >= size) {
                prob = calcProbOfArray(associations);
                selected = associations;
                size = associations.size();
            }
        }
        return selected;
    }

    public ArrayList<Association> recursion(ArrayList<Association> associations) {
        associations.sort((o1, o2) -> Double.compare(o2.getgXY(), o1.getgXY()));

        Iterator<Association> iterator = associations.iterator();
        while(iterator.hasNext()) {
            Association association = iterator.next();
            if (association.getgXY() < 0.05) {
                iterator.remove();
            }
        }

        ArrayList<Association> winningEntity = createB(associations);

        ArrayList<ArrayList<Association>> deeperRes = new ArrayList<>();

        for (Association association: winningEntity) {
            ArrayList<Association> without = createC(associations, association);

            ArrayList<Association> copyWithout = new ArrayList<>(without);

            if (without.isEmpty()) {
                ArrayList<Association> list = new ArrayList<>();
                list.add(winningEntity.getFirst());
                deeperRes.add(list);
                continue;
            }

            Entity entWit = without.getFirst().getEntity();
            Measurement meaWit = without.getFirst().getMeasurement();

            boolean diffEnt = false;
            boolean diffMea = false;

            for (Association wi: without) {
                if (!wi.hasEntity(entWit)) {
                    diffEnt = true;
                }

                if (!wi.hasMeasurement(meaWit)) {
                    diffMea = true;
                }
            }

            if (diffEnt && diffMea) {
                ArrayList<Association> list = recursion(copyWithout);
                list.add(association);
                deeperRes.add(list);
            } else {
                without.sort(((o1, o2) -> Double.compare(o2.getgXY(), o1.getgXY())));

                ArrayList<Association> list = new ArrayList<>();
                list.add(without.getFirst());
                list.add(association);
                deeperRes.add(list);
            }
        }

        return selectWinningArray(deeperRes);
    }

    public void addMeasurementAndCreateNewEntitiesAndDeleteNotUpdated(Timestamp timestamp) {
        ArrayList<Association> selectedAssociation = recursion(associations);

        ArrayList<Entity> entitiesToNotBeRemoved = new ArrayList<>();

        for (Association association: selectedAssociation) {
            entitiesToNotBeRemoved.add(association.getEntity());
            association.getEntity().getPairs().put(timestamp, association.getMeasurement());
            association.getEntity().calcVectors();
        }

        entities.removeIf(entity -> !entitiesToNotBeRemoved.contains(entity));

        ArrayList<Measurement> measurements = new ArrayList<>();

        for (Association association: selectedAssociation) {
            measurements.add(association.getMeasurement());
        }

        for (MeasurementTimestamp measurementTimestamp: measurementTimestamps.getMeasurementTimestamps()) {
            if (!measurements.contains(measurementTimestamp.getMeasurement())) {
                entities.add(new Entity(measurementTimestamp));
            }
        }
    }
}
