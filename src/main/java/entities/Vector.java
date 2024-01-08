package entities;

import measurements.Measurement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Vector {

    private double x;
    private double y;

    public Vector(Measurement measurement1, Timestamp timestamp1, Measurement measurement2, Timestamp timestamp2) {
        this.x = (measurement2.getMyX() - measurement1.getMyX()) /
                (timestamp2.getTime() - timestamp1.getTime());
        this.y = (measurement2.getMyY() - measurement1.getMyY()) /
                (timestamp2.getTime() - timestamp1.getTime());
    }

    public Vector(ArrayList<Vector> vectors) {
        double x = 0;
        double y = 0;
        int vecNum = 2;

        if (vectors.size() >= vecNum) {
            int index = vectors.size() - vecNum;

            for (int i = index; i < vectors.size(); i++) {
                x += vectors.get(i).x;
                y += vectors.get(i).y;
            }

            this.x = x / vecNum;
            this.y = y / vecNum;
        } else {
            for (Vector vector: vectors) {
                x += vector.getX();
                y += vector.getY();
            }

            this.x = x / vectors.size();
            this.y = y / vectors.size();
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
