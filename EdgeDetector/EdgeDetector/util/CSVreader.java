package edgedetector.util;

/*************************************************************************
 * @author Jason Altschuler
 *
 * PURPOSE: Read CSV files
 ************************************************************************/

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVreader {

   /**
    * Reads double[][] from a CSV file
    *
    * @param inFile  The path to the CSV file
    * @param rows    Expected number of rows in the CSV
    * @param columns Expected number of columns in the CSV
    * @return A 2D array of doubles with the CSV data
    */
   public static double[][] read(String inFile, int rows, int columns) {
      if (rows <= 0 || columns <= 0)
         throw new IllegalArgumentException("Invalid dimensions");

      BufferedReader bf = null;
      double[][] arr = new double[rows][columns];

      String line = "";
      int r = 0;

      try {
         bf = new BufferedReader(new FileReader(inFile));

         while ((line = bf.readLine()) != null) {
            String[] x = line.split(",");

            if (x.length != columns) {
               throw new IllegalArgumentException("File has invalid dimensions (columns)");
            }

            for (int c = 0; c < columns; c++) {
               arr[r][c] = Double.parseDouble(x[c]);
            }

            r++;
         }

      } catch (FileNotFoundException e) {
         System.err.println("Error: File not found - " + inFile);
         e.printStackTrace();
      } catch (IOException e) {
         System.err.println("Error: Unable to read file - " + inFile);
         e.printStackTrace();
      } catch (NumberFormatException e) {
         System.err.println("Error: Invalid number format in file - " + inFile);
         e.printStackTrace();
      } finally {
         if (bf != null) {
            try {
               bf.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }

      if (r != rows) {
         throw new IllegalArgumentException("File has invalid dimensions (rows). Expected: " + rows + ", but got: " + r);
      }

      return arr;
   }

   public static void main(String[] args) {
      String testFile;
      int rows;
      int columns;
      boolean print = false;

      // Check if args were provided for the test case
      if (args.length == 4) {
         testFile = args[0];
         rows = Integer.parseInt(args[1]);
         columns = Integer.parseInt(args[2]);
         print = Boolean.parseBoolean(args[3]);
      } else {
         // Use a full path to the file for testing
          testFile = "C:/Users/CES_Engineer/Downloads/TestData.csv";
         // Change this to your actual file path
         rows = 3000;  // Set the correct row count for your CSV file
         columns = 2;  // Set the correct column count for your CSV file
         print = false;
      }

      try {
         double[][] test = CSVreader.read(testFile, rows, columns);

         if (print) {
            for (int i = 0; i < test.length; i++) {
               for (int j = 0; j < test[0].length; j++) {
                  System.out.print(test[i][j] + " ");
               }
               System.out.println();
            }
         }

         System.out.println("Read " + testFile + " successfully!");

      } catch (IllegalArgumentException e) {
         System.err.println("Error: " + e.getMessage());
      }
   }
}
