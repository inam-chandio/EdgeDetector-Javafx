package edgedetector.detectors;

import edgedetector.grayscale.Grayscale;
import edgedetector.ui.ImageViewer;
import edgedetector.util.Threshold;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SobelEdgeDetector extends GaussianEdgeDetector {

   /*********************************************************************
    * Convolution kernels
    *********************************************************************/
   private final static double[][] X_kernel = {{-1, 0, 1},
           {-2, 0, 2},
           {-1, 0, 1}};

   private final static double[][] Y_kernel = {{1, 2, 1},
           {0, 0, 0},
           {-1, -2, -1}};

   /*********************************************************************
    * Implemented abstract methods
    *********************************************************************/

   /**
    * @Override
    * {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}
    */
   public double[][] getXkernel() {
      return SobelEdgeDetector.X_kernel;
   }

   /**
    * @Override
    * {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}}
    */
   public double[][] getYkernel() {
      return SobelEdgeDetector.Y_kernel;
   }

   /*********************************************************************
    * Constructor
    *********************************************************************/

   /**
    * All work is done in constructor.
    * @param filePath path to image
    */
   public SobelEdgeDetector(String filePath) {
      // read image and get pixels
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
    * @param image
    */
   public SobelEdgeDetector(int[][] image) {
      findEdges(image, false);
   }

   /**
    * All work is done in constructor.
    * <P> Gives option to use L1 or L2 norm.
    */
   public SobelEdgeDetector(int[][] image, boolean L1norm) {
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
      // Provide the correct file path for the image
      String img = "C:/Users/CES_Engineer/Downloads/TestData.jpg";  // Replace this with your actual file path

      // Read image and get pixels
      BufferedImage originalImage;
      try {
         originalImage = ImageIO.read(new File(img));
      } catch (IOException e) {
         System.out.println("Error: Unable to read the image file. Please check the file path.");
         return;
      }

      int[][] pixels = Grayscale.imgToGrayPixels(originalImage);

      // Run SobelEdgeDetector
      final long startTime = System.currentTimeMillis();
      SobelEdgeDetector sed = new SobelEdgeDetector(pixels);
      final long endTime = System.currentTimeMillis();

      // Print timing information
      final double elapsed = (double) (endTime - startTime) / 1000;
      System.out.println("Sobel Edge Detector took " + elapsed + " seconds.");
      System.out.println("Threshold = " + sed.threshold);

      // Display edges
      boolean[][] edges = sed.getEdges();
      BufferedImage edges_image = Threshold.applyThresholdReversed(edges);
      BufferedImage[] toShow = {originalImage, edges_image};
      String title = "Sobel Edge Detector by Jason Altschuler";
      ImageViewer.showImages(toShow, title, 2, 2);
   }}
