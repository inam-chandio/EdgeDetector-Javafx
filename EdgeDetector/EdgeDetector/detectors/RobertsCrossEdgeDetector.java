package edgedetector.detectors;

import edgedetector.grayscale.Grayscale;
import edgedetector.ui.ImageViewer;
import edgedetector.util.Threshold;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RobertsCrossEdgeDetector extends GaussianEdgeDetector {

   /*********************************************************************
    * Convolution kernels
    *********************************************************************/
   private final static double[][] X_kernel = {{1, 0},
           {0, -1}};

   private final static double[][] Y_kernel = {{0, -1},
           {1, 0}};

   /*********************************************************************
    * Implemented abstract methods
    *********************************************************************/

   /**
    * @Override
    * {{1, 0}, {0, -1}}
    */
   public double[][] getXkernel() {
      return RobertsCrossEdgeDetector.X_kernel;
   }

   /**
    * @Override
    * {{0, -1}, {1, 0}}
    */
   public double[][] getYkernel() {
      return RobertsCrossEdgeDetector.Y_kernel;
   }

   /*********************************************************************
    * Constructor
    *********************************************************************/

   /**
    * All work is done in constructor.
    * @param filePath path to image
    */
   public RobertsCrossEdgeDetector(String filePath) {
      // Read image and get pixels
      BufferedImage originalImage;
      try {
         originalImage = ImageIO.read(new File(filePath));
         findEdges(Grayscale.imgToGrayPixels(originalImage), false);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * All work is done in constructor.
    * <P> Uses L2 norm by default.
    * @param image the image pixels array
    */
   public RobertsCrossEdgeDetector(int[][] image) {
      findEdges(image, false);
   }

   /**
    * All work is done in constructor.
    * <P> Gives option to use L1 or L2 norm.
    * @param image the image pixels array
    * @param L1norm if true, use L1 norm, otherwise use L2 norm
    */
   public RobertsCrossEdgeDetector(int[][] image, boolean L1norm) {
      findEdges(image, L1norm);
   }

   /*********************************************************************
    * Unit testing
    * @throws IOException
    *********************************************************************/

   /**
    * Example run.
    * <P> Displays detected edges next to original image.
    * @param args command line arguments
    * @throws IOException if image file cannot be read
    */
   public static void main(String[] args) throws IOException {
      // Directly provide the image file path
      String img = "C:/Users/CES_Engineer/Downloads/TestData.jpg";  // Update this to your actual image path

      // Read image and get pixels
      BufferedImage originalImage;
      try {
         originalImage = ImageIO.read(new File(img));
      } catch (IOException e) {
         System.out.println("Error: Unable to read the image file. Please check the file path.");
         return;
      }

      int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

      // Run RobertsCrossEdgeDetector
      final long startTime = System.currentTimeMillis();
      RobertsCrossEdgeDetector sed = new RobertsCrossEdgeDetector(pixels);
      final long endTime = System.currentTimeMillis();

      // Print timing information
      final double elapsed = (double) (endTime - startTime) / 1000;
      System.out.println("Roberts Edge Detector took " + elapsed + " seconds.");
      System.out.println("Threshold = " + sed.threshold);

      // Display edges
      boolean[][] edges = sed.getEdges();
      BufferedImage edges_image = Threshold.applyThresholdReversed(edges);
      BufferedImage[] toShow = {originalImage, edges_image};
      String title = "Roberts Edge Detector by Jason Altschuler";
      ImageViewer.showImages(toShow, title, 2, 2);
   }
}