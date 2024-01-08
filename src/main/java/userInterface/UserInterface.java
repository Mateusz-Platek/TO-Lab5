package userInterface;

import userInterface.ConvectiveCellsAlgorithm;
import userInterface.Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class UserInterface {

    private JFrame window;
    private Display display;
    private File[] files;
    private ConvectiveCellsAlgorithm convectiveCellsAlgorithm;

    public UserInterface() throws IOException, ParseException {
        this.window = new JFrame("Radar");
        this.files = new File("src/main/resources").listFiles();
        this.convectiveCellsAlgorithm = new ConvectiveCellsAlgorithm();
        if (files != null) {
            this.convectiveCellsAlgorithm.loadImage(files[0]);
        }
        this.display = new Display(
                convectiveCellsAlgorithm.getEntities().getEntities(),
                convectiveCellsAlgorithm.getReflectivityMatrix().getWidth(),
                convectiveCellsAlgorithm.getReflectivityMatrix().getHeight(),
                ImageIO.read(files[0])
        );
    }

    public void init() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(display);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }

    public void run() throws IOException, ParseException {
        display.repaint();
        for (int i = 1; i < files.length; i++) {
            convectiveCellsAlgorithm.loadImage(files[i]);
            display.setImage(ImageIO.read(files[i]));
            display.repaint();
        }
    }
}
