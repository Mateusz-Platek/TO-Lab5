package measurements;

import blobs.Blob;
import reflectivityMatrix.Point;
import entities.Vector;

import java.util.ArrayList;

public class Measurement {

    private double myX;
    private double sigmaX;
    private double myY;
    private double sigmaY;

    public double getMyX() {
        return myX;
    }

    public double getMyY() {
        return myY;
    }

    private double calcMyX(ArrayList<Point> points) {
        double sum = 0;
        for (Point point: points) {
            sum += point.getX();
        }
        return sum / points.size();
    }

    private double calcMyY(ArrayList<Point> points) {
        double sum = 0;
        for (Point point: points) {
            sum += point.getY();
        }
        return sum / points.size();
    }

    private double calcSigmaX(ArrayList<Point> points, double myX) {
        double sum = 0;
        for (Point point: points) {
            sum += Math.pow(point.getX() - myX, 2);
        }
        return Math.sqrt(sum / points.size());
    }

    private double calcSigmaY(ArrayList<Point> points, double myY) {
        double sum = 0;
        for (Point point: points) {
            sum += Math.pow(point.getY() - myY, 2);
        }
        return Math.sqrt(sum / points.size());
    }

    public Measurement(Blob blob) {
        this.myX = calcMyX(blob.getPoints());
        this.sigmaX = calcSigmaX(blob.getPoints(), myX);
        this.myY = calcMyY(blob.getPoints());
        this.sigmaY = calcSigmaY(blob.getPoints(), myY);
    }

    public Measurement(Measurement measurement) {
        this.myX = measurement.myX;
        this.sigmaX = measurement.sigmaX;
        this.myY = measurement.myY;
        this.sigmaY = measurement.sigmaY;
    }

    public double calcGX(double x) {
        return Math.exp(-Math.pow(x - myX, 2) / (20 * Math.pow(sigmaX, 2)));// / (sigmaX * Math.sqrt(2 * Math.PI));
    }

    public double calcGY(double y) {
        return Math.exp(-Math.pow(y - myY, 2) / (20 * Math.pow(sigmaY, 2)));// / (sigmaY * Math.sqrt(2 * Math.PI));
    }

    public double calcGXY(Measurement measurement) {
        return calcGX(measurement.myX) * calcGY(measurement.myY);
    }

    public void addVector(Vector vector) {
        this.myX += vector.getX();
        this.myY += vector.getY();
    }
}
