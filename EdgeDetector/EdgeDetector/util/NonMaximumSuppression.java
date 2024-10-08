/**************************************************************************
 * @author Jason Altschuler
 *
 * @tags edge detection, image analysis, computer vision, AI, machine learning
 *
 * PURPOSE: Applies non-maximum suppression, a key step in edge detection. 
 *
 * OVERVIEW: Edges in images are usually defined to be 1 pixel thick. Thus,
 * we cannot simply look for pixels where the change in intensity
 * is sufficiently large (greater than a threshold). We must also suppress
 * non-maximum pixels. Essentially, we calculate the edge direction
 * (the direction in which the gradient of pixel intensity is greatest). We round the
 * edge direction to the nearest 45 degrees. The pixel at (i, j) is suppressed if
 * either of the pixels one unit in the edge direction have greater gradient
 * magnitudes than (i, j).
 ************************************************************************/

package edgedetector.util;

public class NonMaximumSuppression {

   /**
    * Direction to check for non-maximum suppression.
    */
   public enum EdgeDirection {
      VERTICAL,
      HORIZONTAL,
      DIAG_LEFT_UP,
      DIAG_RIGHT_UP, DIAGONAL;

      // constants used for determining direction
      public static final double UP = Math.PI / 2.0;
      public static final double UP_TILT = Math.PI * 77.5 / 180.0;
      public static final double FLAT_TILT = Math.PI * 22.5 / 180.0;
      public static final double FLAT = 0;

      /**
       * Gets the direction for non-maximum suppression from G_x and G_y.
       * Handles the case when G_x == 0 and can't calculate arctan.
       * @param G_x the x component of the gradient
       * @param G_y the y component of the gradient
       * @return the rounded edge direction
       */
      public static EdgeDirection getDirection(double G_x, double G_y) {
         return (G_x != 0) ? getDirection(Math.atan(G_y / G_x)) : ((G_y == 0) ? EdgeDirection.HORIZONTAL : EdgeDirection.VERTICAL);
      }

      /**
       * Rounds the direction to the nearest 45 degrees.
       * @param radians the angle in radians
       * @return the rounded edge direction
       */
      public static EdgeDirection getDirection(double radians) {
         double radians_abs = Math.abs(radians);
         if (radians_abs >= UP_TILT && radians_abs <= UP)
            return EdgeDirection.VERTICAL;
         else if (radians_abs <= FLAT_TILT)
            return EdgeDirection.HORIZONTAL;
         else if (radians >= FLAT_TILT && radians <= UP_TILT)
            return EdgeDirection.DIAG_RIGHT_UP;
         else
            return EdgeDirection.DIAG_LEFT_UP;
      }
   }

   /**
    * See if the pixel at (i, j) is an edge. Requires the following criterion:
    * <P> Non-maximum suppression.
    * @param mag the gradient magnitudes of the image
    * @param angle the edge direction at pixel (i, j)
    * @param i the row index of the pixel
    * @param j the column index of the pixel
    * @return true if the pixel (i, j) is an edge, false otherwise
    */
   public static boolean nonMaximumSuppression(int[][] mag, EdgeDirection angle, int i, int j) {
      // calculate indices of 2 points to check for non-maximum suppression
      int[] indices = indicesMaxSuppresion(angle, i, j);

      // first point to check
      int i1 = indices[0];
      int j1 = indices[1];

      // second point to check
      int i2 = indices[2];
      int j2 = indices[3];

      // non-maximum suppression
      boolean suppress1 = checkInBounds(i1, j1, mag.length, mag[0].length) && mag[i1][j1] > mag[i][j];
      boolean suppress2 = checkInBounds(i2, j2, mag.length, mag[0].length) && mag[i2][j2] > mag[i][j];

      // only return true if (i, j) is not suppressed by either of its 2 neighbors
      return !(suppress1 || suppress2);
   }

   /**
    * Get coordinates of the two points needed to check for non-maximum suppression.
    * @param d the direction of the edge
    * @param i the row index of the pixel
    * @param j the column index of the pixel
    * @return {i1, j1, i2, j2} the coordinates of the two neighboring pixels
    */
   public static int[] indicesMaxSuppresion(EdgeDirection d, int i, int j) {
      // coordinates of two points to check for non-maximum suppression
      int[] indices = new int[4];

      switch (d) {
         case VERTICAL:
            indices[0] = i - 1;
            indices[1] = j;
            indices[2] = i + 1;
            indices[3] = j;
            break;
         case HORIZONTAL:
            indices[0] = i;
            indices[1] = j - 1;
            indices[2] = i;
            indices[3] = j + 1;
            break;
         case DIAG_LEFT_UP:
            indices[0] = i - 1;
            indices[1] = j - 1;
            indices[2] = i + 1;
            indices[3] = j + 1;
            break;
         default: // DIAG_RIGHT_UP
            indices[0] = i - 1;
            indices[1] = j + 1;
            indices[2] = i + 1;
            indices[3] = j - 1;
            break;
      }

      return indices;
   }

   /**
    * Checks if the pixel at (i, j) is within bounds of the image.
    * @param i the row index of the pixel
    * @param j the column index of the pixel
    * @param rows the number of rows in the image
    * @param columns the number of columns in the image
    * @return true if the pixel is within bounds, false otherwise
    */
   private static boolean checkInBounds(int i, int j, int rows, int columns) {
      return (i >= 0 && i < rows && j >= 0 && j < columns);
   }
}
