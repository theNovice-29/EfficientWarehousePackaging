import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LPFAlgorithm {
    public static void main(String[] args) {
        List<Integer> packages = new ArrayList<>();
        packages = Arrays.asList(85, 70, 65, 60, 45, 35, 30, 25, 20, 15, 10);



        long startTime = System.nanoTime();
        List<List<Integer>> containerAllocations = lpfPacking(packages);
        long endTime = System.nanoTime();
        double durationInMilli = (endTime - startTime) / 1_000_000.0;
        System.out.println("LPF Allocations: " + containerAllocations.toString());
        System.out.println("Execution time: " + durationInMilli + " milliseconds");
        System.out.println("Number of containers used: " + containerAllocations.size());
    }

    public static List<List<Integer>> lpfPacking(List<Integer> packages) {
        Collections.sort(packages, Collections.reverseOrder());
        List<List<Integer>> containers = new ArrayList<>();
        int CONTAINER_CAPACITY = 100;

        for (int packageVol : packages) {
            boolean placed = false;
            for (List<Integer> container : containers) {
                int containerVolumeUsed = container.stream().mapToInt(Integer::intValue).sum();
                if (containerVolumeUsed + packageVol <= CONTAINER_CAPACITY) {
                    container.add(packageVol);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                List<Integer> newContainer = new ArrayList<>();
                newContainer.add(packageVol);
                containers.add(newContainer);
            }
        }

        return containers;
    }
}
