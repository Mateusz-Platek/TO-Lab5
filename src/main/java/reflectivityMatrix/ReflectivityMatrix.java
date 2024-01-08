package reflectivityMatrix;

import blobs.Blob;
import blobs.Blobs;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReflectivityMatrix {

    private Timestamp timestamp;
    private int width;
    private int height;
    private Point[][] points;

    private Timestamp createTimestamp(File file) throws ParseException {
        String name = file.getName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = simpleDateFormat.parse(name);
        return new Timestamp(date.getTime());
    }

    private Point[][] createPoints(BufferedImage bufferedImage) {
        Point[][] points = new Point[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int valueOfPixel = bufferedImage.getRGB(i, j);

                if (valueOfPixel == -1) {
                    points[i][j] = new Point(i, j, 1);
                } else {
                    points[i][j] = new Point(i, j, 0);
                }
            }
        }

        return points;
    }

    public ReflectivityMatrix(File file) throws IOException, ParseException {
        this.timestamp = createTimestamp(file);
        BufferedImage bufferedImage = ImageIO.read(file);
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        this.points = createPoints(bufferedImage);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Blobs createBlobs() {
        ArrayList<Blob> blobs = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (points[i][j].getValue() == 1) {
                    boolean added = false;

                    for (Blob blob: blobs) {
                        if (i != width - 1 && blob.contains(points[i + 1][j])) {
                            blob.addPoint(points[i][j], points, width, height);
                            added = true;
                            break;
                        }

                        if (i != 0 && blob.contains(points[i - 1][j])) {
                            blob.addPoint(points[i][j], points, width, height);
                            added = true;
                            break;
                        }

                        if (j != height - 1 && blob.contains(points[i][j + 1])) {
                            blob.addPoint(points[i][j], points, width, height);
                            added = true;
                            break;
                        }

                        if (j != 0 && blob.contains(points[i][j - 1])) {
                            blob.addPoint(points[i][j], points, width, height);
                            added = true;
                            break;
                        }
                    }

                    if (!added) {
                        Blob blob = new Blob();
                        blob.addPoint(points[i][j], points, width, height);
                        blobs.add(blob);
                    }
                }
            }
        }

        blobs.removeIf(blob -> blob.getPoints().size() < 7);

        return new Blobs(blobs, timestamp);
    }
}
