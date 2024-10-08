package edgedetector;

import edgedetector.detectors.CannyEdgeDetector;

public class Main {

    public static void main(String[] args) {
        // Example input image (2D array of pixel intensity values)
        int[][] image = {
                {0, 0, 0, 255, 255, 0, 0, 0},
                {0, 255, 255, 255, 255, 255, 255, 0},
                {0, 0, 0, 255, 255, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {255, 255, 255, 0, 0, 255, 255, 255},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };

        // Create a CannyEdgeDetector using the builder pattern
        CannyEdgeDetector detector = new CannyEdgeDetector.Builder(image)
                .thresholds(50, 150) // Set low and high thresholds
                .L1norm(false) // Use L2 norm for edge detection
                .minEdgeSize(1) // Set minimum edge size
                .build();

        // Perform edge detection
        boolean[][] edges = detector.getEdges();

        // Output the edge detection result
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < edges[i].length; j++) {
                System.out.print(edges[i][j] ? "1 " : "0 ");
            }
            System.out.println();
        }
    }
}
