package edgedetector.util;

/**
 * Utility class for calculating distance norms.
 * This class provides methods for calculating the L1 and L2 norms.
 */
public class Hypotenuse {

   /**
    * Calculates the L1 norm (Manhattan distance) between two values.
    *
    * @param x The first value.
    * @param y The second value.
    * @return The L1 norm as the sum of the absolute values of x and y.
    */
   public static double L1(int x, int y) {
      return Math.abs(x) + Math.abs(y);
   }

   /**
    * Calculates the L2 norm (Euclidean distance) between two values.
    *
    * @param x The first value.
    * @param y The second value.
    * @return The L2 norm as the square root of the sum of squares of x and y.
    */
   public static double L2(int x, int y) {
      return Math.sqrt(x * x + y * y);
   }
}
