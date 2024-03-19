import java.util.ArrayList;
import java.util.List;

public class BruteForceAlgorithm {

    private static final int CONTAINER_CAPACITY = 100;

    public static void main(String[] args) {
        List<Integer> packages = new ArrayList<>();

        packages.add(10);
        packages.add(20);
        packages.add(30);
        packages.add(45);
        packages.add(55);


        long startTime = System.nanoTime();


        List<List<Integer>> containerAllocations = bruteForcePacking(packages);


        long endTime = System.nanoTime();


        double durationInMilli = (endTime - startTime) / 1_000_000.0;


        System.out.println("Brute-Force Allocations: " + containerAllocations.toString());


        System.out.println("Execution time: " + durationInMilli + " milliseconds");
    }

    public static List<List<Integer>> bruteForcePacking(List<Integer> packages) {
        List<List<Integer>> bestSolution = new ArrayList<>();
        List<List<Integer>> currentSolution = new ArrayList<>();
        currentSolution.add(new ArrayList<>()); // Start with one empty container.
        return findBestAllocation(packages, 0, currentSolution, bestSolution);
    }

    private static List<List<Integer>> findBestAllocation(List<Integer> packages, int index,
                                                          List<List<Integer>> currentSolution, List<List<Integer>> bestSolution) {
        if (index == packages.size()) {
            if (bestSolution.isEmpty() || currentSolution.size() < bestSolution.size()) {
                bestSolution = new ArrayList<>(currentSolution.size());
                for (List<Integer> container : currentSolution) {
                    bestSolution.add(new ArrayList<>(container));
                }
            }
            return bestSolution;
        }

        int packageVol = packages.get(index);
        boolean placedInExistingContainer = false;

        for (int i = 0; i < currentSolution.size(); i++) {
            List<Integer> container = new ArrayList<>(currentSolution.get(i));
            if (container.stream().mapToInt(Integer::intValue).sum() + packageVol <= CONTAINER_CAPACITY) {
                container.add(packageVol);
                List<List<Integer>> newCurrentSolution = new ArrayList<>(currentSolution);
                newCurrentSolution.set(i, container);
                bestSolution = findBestAllocation(packages, index + 1, newCurrentSolution, bestSolution);
                placedInExistingContainer = true;
            }
        }

        if (!placedInExistingContainer) {
            List<Integer> newContainer = new ArrayList<>();
            newContainer.add(packageVol);
            List<List<Integer>> newCurrentSolution = new ArrayList<>(currentSolution);
            newCurrentSolution.add(newContainer);
            bestSolution = findBestAllocation(packages, index + 1, newCurrentSolution, bestSolution);
        }

        return bestSolution;
    }
}
