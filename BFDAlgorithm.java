import java.util.*;

public class BFDAlgorithm {
    public static void main(String[] args) {
        List<Integer> packages = new ArrayList<>();
        packages = Arrays.asList(85, 70, 65, 60, 45, 35, 30, 25, 20, 15, 10);




        long startTime = System.nanoTime();
        List<List<Integer>> containerAllocations = bfdPacking(packages, 100); // Assuming 200 is the container capacity
        long endTime = System.nanoTime();
        double durationInMilli = (endTime - startTime) / 1_000_000.0;

        System.out.println("BFD Allocations: " + containerAllocations.toString());
        System.out.println("Execution time: " + durationInMilli + " milliseconds");
        System.out.println("Number of containers used: " + containerAllocations.size());
    }

    public static List<List<Integer>> bfdPacking(List<Integer> packages, int containerCapacity) {
        // Sort packages in descending order to start with the largest package first
        packages.sort(Collections.reverseOrder());
        List<List<Integer>> containers = new ArrayList<>();

        for (int packageVol : packages) {
            List<Integer> bestFitContainer = null;
            int minLeftoverSpace = Integer.MAX_VALUE;

            // Search for the best fit container for the current package
            for (List<Integer> container : containers) {
                int containerVolumeUsed = container.stream().mapToInt(Integer::intValue).sum();
                int leftoverSpace = containerCapacity - (containerVolumeUsed + packageVol);
                if (leftoverSpace >= 0 && leftoverSpace < minLeftoverSpace) {
                    bestFitContainer = container;
                    minLeftoverSpace = leftoverSpace;
                }
            }

            // If no suitable container is found, create a new one
            if (bestFitContainer == null) {
                List<Integer> newContainer = new ArrayList<>();
                newContainer.add(packageVol);
                containers.add(newContainer);
            } else {
                // If a suitable container is found, place the package there
                bestFitContainer.add(packageVol);
            }
        }

        return containers;
    }
}
