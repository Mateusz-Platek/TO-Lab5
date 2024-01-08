package blobs;

import reflectivityMatrix.Point;
import java.util.ArrayList;
import java.util.Stack;

public class Blob {

    private ArrayList<Point> points;

    public Blob() {
        this.points = new ArrayList<>();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point, Point[][] pointsArray, int width, int height) {
        Stack<Point> stack = new Stack<>();
        stack.push(point);

        while (!stack.isEmpty()) {
            Point currentPoint = stack.pop();
            points.add(currentPoint);

            int x = currentPoint.getX();
            int y = currentPoint.getY();
            
            if (x != 0 && !points.contains(pointsArray[x - 1][y]) && pointsArray[x - 1][y].getValue() == 1) {
                stack.push(pointsArray[x - 1][y]);
            }

            if (x != width - 1 && !points.contains(pointsArray[x + 1][y]) && pointsArray[x + 1][y].getValue() == 1) {
                stack.push(pointsArray[x + 1][y]);
            }

            if (y != 0 && !points.contains(pointsArray[x][y - 1]) && pointsArray[x][y - 1].getValue() == 1) {
                stack.push(pointsArray[x][y - 1]);
            }

            if (y != height - 1 && !points.contains(pointsArray[x][y + 1]) && pointsArray[x][y + 1].getValue() == 1) {
                stack.push(pointsArray[x][y + 1]);
            }
        }
    }

    public boolean contains(Point point) {
        return points.contains(point);
    }
}
