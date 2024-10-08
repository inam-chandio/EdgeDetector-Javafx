package edgedetector.detectors;

import java.util.Random;

public class KMeans {

    private int k; // number of clusters
    private int iterations; // max iterations
    private double[][] data; // data points to be clustered
    private double[][] centroids; // centroids of clusters
    private int[] labels; // labels for each data point
    private boolean pp; // k-means++ initialization
    private boolean useEpsilon;
    private double epsilon;

    private KMeans(Builder builder) {
        this.k = builder.k;
        this.iterations = builder.iterations;
        this.data = builder.data;
        this.pp = builder.pp;
        this.useEpsilon = builder.useEpsilon;
        this.epsilon = builder.epsilon;

        runKMeans();
    }

    // Builder class for KMeans
    public static class Builder {
        private int k;
        private double[][] data;
        private int iterations = 100; // default max iterations
        private boolean pp = false;
        private boolean useEpsilon = false;
        private double epsilon = 0.0001;

        public Builder(int k, double[][] data) {
            this.k = k;
            this.data = data;
        }

        public Builder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder pp(boolean pp) {
            this.pp = pp;
            return this;
        }

        public Builder useEpsilon(boolean useEpsilon) {
            this.useEpsilon = useEpsilon;
            return this;
        }

        public Builder epsilon(double epsilon) {
            this.epsilon = epsilon;
            return this;
        }

        public KMeans build() {
            return new KMeans(this);
        }
    }

    private void runKMeans() {
        // Step 1: Initialize centroids
        initializeCentroids();

        boolean converged = false;
        labels = new int[data.length];

        for (int iter = 0; iter < iterations && !converged; iter++) {
            // Step 2: Assign labels based on nearest centroid
            assignLabels();

            // Step 3: Update centroids
            double[][] newCentroids = calculateNewCentroids();

            // Step 4: Check convergence
            converged = checkConvergence(newCentroids);

            centroids = newCentroids;
        }
    }

    private void initializeCentroids() {
        centroids = new double[k][data[0].length];
        Random rand = new Random();

        if (pp) {
            // K-means++ initialization
            centroids[0] = data[rand.nextInt(data.length)];
            for (int i = 1; i < k; i++) {
                double[] distances = new double[data.length];
                for (int j = 0; j < data.length; j++) {
                    distances[j] = minDistanceToCentroid(data[j], centroids, i);
                }
                centroids[i] = data[selectRandomWeightedIndex(distances)];
            }
        } else {
            // Random initialization
            for (int i = 0; i < k; i++) {
                centroids[i] = data[rand.nextInt(data.length)];
            }
        }
    }

    private int selectRandomWeightedIndex(double[] distances) {
        double sum = 0;
        for (double distance : distances) {
            sum += distance;
        }

        Random rand = new Random();
        double r = rand.nextDouble() * sum;
        double cumulativeSum = 0;

        for (int i = 0; i < distances.length; i++) {
            cumulativeSum += distances[i];
            if (cumulativeSum >= r) {
                return i;
            }
        }

        return distances.length - 1;
    }

    private double minDistanceToCentroid(double[] point, double[][] centroids, int numCentroids) {
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < numCentroids; i++) {
            double dist = euclideanDistance(point, centroids[i]);
            if (dist < minDist) {
                minDist = dist;
            }
        }
        return minDist;
    }

    private void assignLabels() {
        for (int i = 0; i < data.length; i++) {
            labels[i] = findNearestCentroid(data[i]);
        }
    }

    private int findNearestCentroid(double[] point) {
        int nearest = 0;
        double minDist = euclideanDistance(point, centroids[0]);

        for (int i = 1; i < centroids.length; i++) {
            double dist = euclideanDistance(point, centroids[i]);
            if (dist < minDist) {
                minDist = dist;
                nearest = i;
            }
        }

        return nearest;
    }

    private double[][] calculateNewCentroids() {
        double[][] newCentroids = new double[k][data[0].length];
        int[] counts = new int[k];

        for (int i = 0; i < data.length; i++) {
            int label = labels[i];
            for (int j = 0; j < data[0].length; j++) {
                newCentroids[label][j] += data[i][j];
            }
            counts[label]++;
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (counts[i] > 0) {
                    newCentroids[i][j] /= counts[i];
                }
            }
        }

        return newCentroids;
    }

    private boolean checkConvergence(double[][] newCentroids) {
        for (int i = 0; i < centroids.length; i++) {
            if (useEpsilon) {
                if (euclideanDistance(centroids[i], newCentroids[i]) > epsilon) {
                    return false;
                }
            } else {
                if (!centroidsEqual(centroids[i], newCentroids[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean centroidsEqual(double[] centroid1, double[] centroid2) {
        for (int i = 0; i < centroid1.length; i++) {
            if (centroid1[i] != centroid2[i]) {
                return false;
            }
        }
        return true;
    }

    private double euclideanDistance(double[] point1, double[] point2) {
        double sum = 0;
        for (int i = 0; i < point1.length; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public double[][] getCentroids() {
        return centroids;
    }

    public int[] getLabels() {
        return labels;
    }
}
