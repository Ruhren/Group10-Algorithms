import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] sizes = {1000, 2000, 3000};

        // trial test: run each sorting algorithm to let JVM optimise code before we measure results
        int[] trial = SortFunctions.createArr(5000, 1000000);
        QuickSort.sort(trial.clone());
        QuickInsertionSort.sort(trial.clone());
        QuickCountingSort.sort(trial.clone());

        for (int n : sizes) {
            int[] array = SortFunctions.createArr(n, 1000000);

            System.out.println("Results for size " + n + ":");

            // Classic QuickSort
            runTest("   Classic QuickSort: ", Arrays.copyOf(array, n), "classic");

            // Quick + Insertion Sort
            runTest("   Quick + Insertion Sort: ", Arrays.copyOf(array, n), "insertion");

            // Quick + Counting Sort
            runTest("   Quick + Counting Sort: ", Arrays.copyOf(array, n),  "counting");

            System.out.println();
        }
    }

    // to measure the execution time of each sorting algorithm
    private static void runTest(String name, int[] arr, String type) {
        long start = System.nanoTime();

        if (type.equals("classic")) {
            QuickSort.sort(arr);
        } else if (type.equals("insertion")) {
            QuickInsertionSort.sort(arr);
        } else if (type.equals("counting")) {
            QuickCountingSort.sort(arr);
        }

        long end = System.nanoTime();

        double duration = (end - start) / 1_000_000.0;
        System.out.println(name + duration + " ms");
    }
}