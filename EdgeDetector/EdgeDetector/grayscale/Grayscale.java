package edgedetector.grayscale;



import java.awt.image.BufferedImage;

public class Grayscale {
    public static int[][] imgToGrayPixels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] grayPixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb) & 0xff;
                int gray = (r + g + b) / 3; // Simple average to convert to grayscale
                grayPixels[y][x] = gray;
            }
        }
        return grayPixels;
    }
}
