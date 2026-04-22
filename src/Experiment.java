/*
 * This class performs all the experiments for the all the analysis of Counting Sort based hybrid algorithm against the
 * baseline algorithms from the paper
 *
 * Experiments involved:
 * 1 - replicates table 1 (Time taken to sort an array using Classic Counting Sort, unordered vs ordered input array)
 * 2 - replicates table 2 (Time take to sort array with Counting Sort with and without pre-processing)
 * 3 - replicates table 3 (Running times in ms for QuickSort, QuickSort with InsertionSort, and QuickSort with CountingSort)
*/

public class Experiment {

    private static final int RUNS = 10;

    // Helper function to check if array is sorted, important to check the correctness of the sorting
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    // Helper function to warm up JVM before running experiments,
    /*
    * JVM warmup is important, before actually timing the algorithms to help stabilize execution
    * and avoid measuring irrelevant processes like class loading or JIT compilation.
    */
    private static void warmup(int iterations, int n, int r) {
        System.out.println("Starting warmup...");

        for (int i = 0; i < iterations; i++) {
            // warm up for random array
            int[] randomArr = SortFunctions.createArr(n, r);
            int[] randomArr1 = randomArr.clone();
            int[] randomArr2 = randomArr.clone();
            int[] randomArr3 = randomArr.clone();

            QuickSort.sort(randomArr);
            QuickInsertionSort.sort(randomArr1);
            QuickCountingSort.sortWithTimers(randomArr2);
            CountingSort.sort(randomArr3);

            // warm up for sorted array
            int[] sortedArr = SortFunctions.createSortedArr(n);
            int[] sortedArr1 = sortedArr.clone();
            int[] sortedArr2 = sortedArr.clone();
            int[] sortedArr3 = sortedArr.clone();

            QuickSort.sort(sortedArr);
            QuickInsertionSort.sort(sortedArr1);
            QuickCountingSort.sortWithTimers(sortedArr2);
            CountingSort.sort(sortedArr3);
        }

        System.out.println("Finished!");
    }

    // Timer for Classic Counting Sort
    // Returns elapsed time in nanoseconds
    public static long TimerCountingSort(int[] array) {

        long start = System.nanoTime();
        CountingSort.sort(array);
        long end = System.nanoTime();

        if (!isSorted(array)) {
            throw new RuntimeException("Array is not sorted correctly.");
        }

        return end - start;
    }

    // Timer for Classic QuickSort
    // Returns elapsed time in nanoseconds
    public static long TimerQuickSort(int[] array){
        long start = System.nanoTime();
        QuickSort.sort(array);
        long end = System.nanoTime();

        if (!isSorted(array)) {
            throw new RuntimeException("Array is not sorted correctly.");
        }

        return  end - start;
    }

    // Timer for quickSortModified and insertionSort
    // Returns elapsed time in nanoseconds
     public static long TimerQuickInsertionSort(int[] array){
        long start = System.nanoTime();
        QuickInsertionSort.sort(array);
        long end = System.nanoTime();

         if (!isSorted(array)) {
             throw new RuntimeException("Array is not sorted correctly.");
         }

        return end - start;
    }

    // Timer for proposed hybrid algorithm
    /*  QuickCountingSort.sortWithTimers already measures internal components of the
     algorithm and returns a SortResults object. The total execution time is used
     here for comparison with the baseline algorithms.
    */
    public static long TimerQuickCountingSort(int[] array){

        QuickCountingSort.SortResults results = QuickCountingSort.sortWithTimers(array);

        if (!isSorted(array)) {
            throw new RuntimeException("Array is not sorted correctly.");
        }

        return (long) results.totalTime;
    }

    /*
     * Experiment 1 : Measuring performance comparison in ms Over Random(T1) and sorted Inputs(T2)
     *
     * For the input array n = r, n is the no. of inputs and r is the range
     * Timing is calculated, a Random independent array with n inputs and r range s.t. n = r will be input 1st(T1).
     * Later for T2, sorted input array in the form {0,1,2,....,n-1}, here to n = r will be input
     *
     * Note: The call order alternates between T1 and T2 across repetitions so that one input
     * type does not always benefit from being executed first. This helps reduce timing bias.
     */

    public static void experiment1() {
        System.out.println("Performance Comparison in ms Over Random (T1) and Sorted Inputs (T2)");

        int[] nValues = {1000000, 2000000, 3000000};

        for (int n : nValues) {
            System.out.println("n:" + n + "       r:" + n);

            long totalT1 = 0;
            long totalT2 = 0;

            for (int rep = 0; rep < RUNS; rep++) {
                int[] randomArr = SortFunctions.createArr(n, n);
                int[] sortedArr = SortFunctions.createSortedArr(n);

                // alternating the calling between the randomArr and sortedArr to avoid bias

                if (rep % 2 == 0) {
                    totalT1 += TimerCountingSort(randomArr);
                    totalT2 += TimerCountingSort(sortedArr);
                } else {
                    totalT2 += TimerCountingSort(sortedArr);
                    totalT1 += TimerCountingSort(randomArr);
                }
            }

            double avgT1 = (totalT1 / (double) RUNS) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) RUNS) / 1_000_000.0;

            System.out.printf("T1 : %.2f ms\n", avgT1);
            System.out.printf("T2 : %.2f ms\n", avgT2);
            System.out.println();
        }
    }

    /*
    * Experiment 2 replicates Table 2 of the paper by comparing counting sort with preprocessing(T1)
    * against counting sort without preprocessing (T2) when r ≫ n.
    * Note: T1 = time for preprocessing + time for sorting using counting sort after processing
    */

    public static void experiment2() {
        System.out.println("Running Times in ms for Counting Sort with and without Preprocessing");
        int r = 1000000;
        int[] nValues = {1000, 2000, 3000};

        for (int n : nValues){
            System.out.println("n:" + n + "       r:" + r);

            long totalPreprocessT1 = 0;
            long totalCountingT1 = 0;
            long totalT1 = 0;
            long totalT2 = 0;

            for (int rep = 0; rep < RUNS; rep++) {
                int[] randomArr = SortFunctions.createArr(n, r);
                int[] randomArr1 = randomArr.clone();

                // alternating the calling between arrays to avoid bias

                if (rep % 2 == 0) {
                    QuickCountingSort.SortResults results = QuickCountingSort.sortWithTimers(randomArr);
                    totalPreprocessT1 += (long)(results.preprocessingTime * 1_000_000);
                    totalCountingT1 += (long)(results.countingSortTime * 1_000_000);
                    totalT1 += (long)(results.totalTime * 1_000_000);

                    totalT2 += TimerCountingSort(randomArr1);
                } else {
                    QuickCountingSort.SortResults results = QuickCountingSort.sortWithTimers(randomArr1);
                    totalPreprocessT1 += (long)(results.preprocessingTime * 1_000_000);
                    totalCountingT1 += (long)(results.countingSortTime * 1_000_000);
                    totalT1 += (long)(results.totalTime * 1_000_000);

                    totalT2 += TimerCountingSort(randomArr);
                }

            }

            double avgPreprocessT1 = (totalPreprocessT1 / (double) RUNS) / 1_000_000.0;
            double avgCountingT1 = (totalCountingT1 / (double) RUNS) / 1_000_000.0;
            double avgT1 = (totalT1 / (double) RUNS) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) RUNS) / 1_000_000.0;

            System.out.printf("T1 : %.2f + %.2f = %.2f ms\n", avgPreprocessT1, avgCountingT1, avgT1);
            System.out.printf("T2 : %.2f ms\n", avgT2);
            System.out.println();
        }
    }

    /*
    *
    * Experiment 3 replicates Table 3 of the paper by comparing QuickSort (T1), QuickSort with InsertionSort (T2),
    * and proposed QuickSort with Counting Sort Hybrid (T3) when n=r.
    */

    public static void experiment3() {
        System.out.println("Running Times in ms for Quicksort, Quicksort with Insertion Sort, and Quicksort with Counting Sort");

        int[] nValues = {1000000, 2000000};

        for(int n : nValues) {
            System.out.println("n:" + n + "       r:" + n);

            long totalT1 = 0;
            long totalT2 = 0;
            long totalT3 = 0;

            for (int rep = 0; rep < RUNS; rep++) {
                int[] randomArr = SortFunctions.createArr(n, n);
                int[] randomArr1 = randomArr.clone();
                int[] randomArr2 = randomArr.clone();

                // alternating the calling between arrays to avoid bias

                if (rep % 3 == 0) {
                    totalT1 += TimerQuickSort(randomArr);               // classic quick sort
                    totalT2 += TimerQuickInsertionSort(randomArr1);     // modified quick sort with insertion sort
                    totalT3 += TimerQuickCountingSort(randomArr2);      // proposed algorithm
                } else if (rep % 3 == 1) {
                    totalT2 += TimerQuickInsertionSort(randomArr1);     // modified quick sort with insertion sort
                    totalT3 += TimerQuickCountingSort(randomArr2);      // proposed algorithm
                    totalT1 += TimerQuickSort(randomArr);               // classical quick sort

                } else {
                    totalT3 += TimerQuickCountingSort(randomArr2);      // proposed algorithm
                    totalT1 += TimerQuickSort(randomArr);               // classical quick sort
                    totalT2 += TimerQuickInsertionSort(randomArr1);     // modified quick sort with insertion sort
                }
            }

            double avgT1 = (totalT1 / (double) RUNS) / 1_000_000.0;
            double avgT2 = (totalT2 / (double) RUNS) / 1_000_000.0;

            // TimerQuickCountingSort returns the total time directly from SortResults,
            // so it is averaged without the nanoseconds-to-milliseconds conversion used above.
            double avgT3 = (totalT3 / (double) RUNS);

            System.out.printf("T1 : %.2f ms\n", avgT1);
            System.out.printf("T2 : %.2f ms\n", avgT2);
            System.out.printf("T3 : %.2f ms\n", avgT3);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        warmup(10, 5000000, 5000000); // JVM warmed up before experiments performed

        experiment1();
        experiment2();
        experiment3();
    }
}

