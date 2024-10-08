package edgedetector.util;

import edgedetector.detectors.GaussianEdgeDetector;
import edgedetector.detectors.PrewittEdgeDetector;
import edgedetector.detectors.RobertsCrossEdgeDetector;
import edgedetector.detectors.SobelEdgeDetector;
import edgedetector.grayscale.Grayscale;
import edgedetector.ui.ImageViewer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EdgeDetectorViewer {

   public static void main(String[] args) {
      try {
         // Provide the full absolute path to the image file here
         test("C:/Users/CES_Engineer/Downloads/TestData.jpg");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Displays results from Sobel, Prewitt, and Roberts Cross edge detectors.
    *
    * @param imageFile - The absolute or relative path of the image file.
    * @throws IOException
    */
   public static void test(String imageFile) throws IOException {
      // Check if the file exists
      File file = new File(imageFile);
      if (!file.exists()) {
         System.out.println("Error: File not found at path: " + imageFile);
         return;
      }

      // Read image and get pixels
      BufferedImage originalImage = ImageIO.read(file);
      int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

      // Run various Gaussian edge detectors
      GaussianEdgeDetector[] edgeDetectors = new GaussianEdgeDetector[3];
      edgeDetectors[0] = new SobelEdgeDetector(pixels);
      edgeDetectors[1] = new PrewittEdgeDetector(pixels);
      edgeDetectors[2] = new RobertsCrossEdgeDetector(pixels);

      // Get edges
      boolean[][] sobelEdges = edgeDetectors[0].getEdges();
      boolean[][] prewittEdges = edgeDetectors[1].getEdges();
      boolean[][] robertsCrossEdges = edgeDetectors[2].getEdges();

      // Make images out of edges
      BufferedImage sobelImage = Threshold.applyThresholdReversed(sobelEdges);
      BufferedImage prewittImage = Threshold.applyThresholdReversed(prewittEdges);
      BufferedImage robertsCrossImage = Threshold.applyThresholdReversed(robertsCrossEdges);

      // Display edges
      BufferedImage[] toShow = {originalImage, sobelImage, prewittImage, robertsCrossImage};
      String title = "Edge Detection by Jason Altschuler";
      ImageViewer.showImages(toShow, title, 2, 2);
   }
}
