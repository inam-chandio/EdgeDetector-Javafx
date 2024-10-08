package edgedetector.detectors; // Adjust to your chosen package name

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReader {
    public static void main(String[] args) {
        // Check if an image path is provided as an argument
        if (args.length != 1) {
            System.out.println("Please provide the image path as an argument.");
            return;
        }

        String imagePath = args[0];
        BufferedImage image = null;

        try {
            // Read the image from the specified path
            image = ImageIO.read(new File(imagePath));
            System.out.println("Image successfully read: " + imagePath);
        } catch (IOException e) {
            System.out.println("Error reading the image: " + e.getMessage());
        }

        // Further processing of the image can be done here
    }
}

