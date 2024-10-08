package edgedetector.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewer {

    /**
     * Displays multiple images in a grid layout.
     *
     * @param images Array of images to display
     * @param title  Window title
     * @param i
     * @param i1
     */
    public static void showImages(BufferedImage[] images, String title, int i, int i1) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Calculate number of rows and columns for the grid layout
        int rows = (int) Math.ceil(Math.sqrt(images.length)); // Number of rows
        int cols = (int) Math.ceil((double) images.length / rows); // Number of columns

        frame.setLayout(new GridLayout(rows, cols));

        for (BufferedImage image : images) {
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);
        }

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Converts a boolean 2D array to a BufferedImage.
     *
     * @param edges 2D boolean array where true represents an edge.
     * @return BufferedImage representation of the edges.
     */
    public static BufferedImage createImageFromPixels(boolean[][] edges) {
        int rows = edges.length;
        int cols = edges[0].length;
        BufferedImage image = new BufferedImage(cols, rows, BufferedImage.TYPE_BYTE_BINARY);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Set pixel color based on whether it's an edge or not
                int color = edges[r][c] ? 0xFFFFFF : 0x000000; // White for edges, black for non-edges
                image.setRGB(c, r, color);
            }
        }
        return image;
    }
}
