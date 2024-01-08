package userInterface;

import entities.Entity;
import measurements.Measurement;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Display extends JPanel {

    private ArrayList<Entity> entities;
    private Image image;

    public Display(ArrayList<Entity> entities, int width, int height, Image image) {
        setPreferredSize(new Dimension(width, height));
        this.entities = entities;
        this.image = image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, null);
        g.setColor(Color.YELLOW);

        for (Entity entity: entities) {
            Measurement previousMeasurement = null;

            for (Timestamp timestamp: entity.getPairs().keySet()) {
                Measurement measurement = entity.getPairs().get(timestamp);
                g.setColor(Color.YELLOW);
                if (previousMeasurement != null) {
                    g.drawLine((int) previousMeasurement.getMyX(), (int) previousMeasurement.getMyY(), (int) measurement.getMyX(), (int) measurement.getMyY());
                }
                previousMeasurement = measurement;
            }
        }
    }
}
